package kg.kuban.airport.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.kuban.airport.dto.AirplaneRequestDto;
import kg.kuban.airport.dto.AirplanePartCheckupRequestDto;
import kg.kuban.airport.dto.AirplaneResponseDto;
import kg.kuban.airport.entity.Airplane;
import kg.kuban.airport.enums.AirplaneStatus;
import kg.kuban.airport.enums.AirplaneType;
import kg.kuban.airport.exception.*;
import kg.kuban.airport.mapper.AirplaneMapper;
import kg.kuban.airport.mapper.AirplanePartCheckupMapper;
import kg.kuban.airport.response.SuccessResponse;
import kg.kuban.airport.service.AirplaneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/airplanes")
@Tag(
        name = "Контроллер для регистрации Самолета",
        description = "Описывает точки доступа по регистрации самолета и обслуживании работниками аэропорта"
)
public class AirplaneController {
    private final AirplaneService airplaneService;
    private final Logger logger = LoggerFactory.getLogger(AppUserController.class);

    @Autowired
    public AirplaneController(AirplaneService airplaneService) {
        this.airplaneService = airplaneService;
    }

    @Operation(
            summary = "Метод регистрации самолета Диспетчером",
            description = "1.Диспетчер регистрирует новый самолет и отправляет на обработку Главному инженеру",
            parameters = {
                    @Parameter(name = "AirplaneRequestDto",
                                description = "DTO самолета",
                                schema = @Schema(type = "AirplaneRequestDto"),
                                required = true)
            }
    )
    @PostMapping(value = "/register")
    @PreAuthorize("hasAnyRole('DISPATCHER')")
    public ResponseEntity<?> registerNewAirplane(@RequestBody AirplaneRequestDto airplaneRequestDto)
            throws AirplanePartNotFoundException, IncompatiblePartException
    {
        //logger.info("registerNewAirplane");
        return ResponseEntity.ok(AirplaneMapper.mapAirplaneEntityToDto(this.airplaneService.registerNewAirplane(airplaneRequestDto)));
    }
    // --------------------2--------------
    @Operation(
            summary = "Метод выдачи самолета на техосмотр",
            description = "2.Главный инженер выдает самолет на технический осмотр Инженеру",
            parameters = {
                    @Parameter(name = "airplaneId",
                            description = "ID самолета",
                            schema = @Schema(type = "Long"),
                            required = true),
                    @Parameter(name = "userId",
                                description = "ID инженера",
                                schema = @Schema(type = "Long"),
                                required = true)
            }
    )
    @PostMapping("/assignCheckup/{id}")
    @PreAuthorize(value = "hasAnyRole('CHIEF_ENGINEER')")
    public ResponseEntity<?> assignAirplaneToCheckup( @PathVariable(value = "id") Long airplaneId, @RequestParam Long userId)
            throws AirplaneNotFoundException, StatusChangeException, EngineerIsBusyException {
        return ResponseEntity.ok(AirplaneMapper.mapAirplaneEntityToDto(this.airplaneService.assignAirplaneCheckup(airplaneId, userId)));
    }
    // --------------3------------------------
    @Operation(
            summary = "Метод составление технического осмотра самолета.",
            description = "3.Инженер составляет технический осмотр ",
            parameters = {
                    @Parameter(name = "airplaneId",
                                description = "ID самолета",
                                schema = @Schema(type = "Long"),
                                required = true),
                    @Parameter(name = "List<AirplanePartCheckupRequestDto>",
                                description = "Список DTO запроса списка частей самолета ",
                                schema = @Schema(type = "List<AirplanePartCheckupRequestDto>"),
                                required = true)
            }
    )
    @PreAuthorize(value = "hasRole('ENGINEER')")
    @PostMapping(value = "/checkup/{id}")
    public ResponseEntity<?> checkupAirplane(
            @PathVariable(value = "id") Long airplaneId,
            @RequestBody List<AirplanePartCheckupRequestDto> partInspectionsRequestDtoList
    )
            throws AirplaneNotFoundException,
            StatusChangeException,
            WrongEngineerException,
            AirplaneIsNotOnServiceException,
            AirplanePartNotFoundException,
            IllegalAirplaneException,
            IncompatiblePartException
    {
        return ResponseEntity.ok(AirplanePartCheckupMapper.mapToPartCheckupResponseDtoList(this.airplaneService.checkupAirplane(airplaneId, partInspectionsRequestDtoList)));
    }

    @Operation(
            summary = "Метод подтверждения исправности самолета.",
            description = "4.Главный инженер принимает решение по техническому осмотру: если все нормально, то подтверждает технический осмотр и отправляет назад Диспетчеру",
            parameters = {
                    @Parameter(name = "airplaneId",
                                description = "ID самолета",
                                schema = @Schema(type = "Long"),
                                required = true
                    )
            }
    )
    @PutMapping(value = "/confirmServiceability/{id}")
    @PreAuthorize(value = "hasRole('CHIEF_ENGINEER')")
    public ResponseEntity<?> confirmAirplaneServiceability(@PathVariable(value = "id") Long airplaneId)
            throws AirplaneNotFoundException,
            AirplanePartCheckupNotFoundException,
            StatusChangeException
    {
        return ResponseEntity.ok(AirplaneMapper.mapAirplaneEntityToDto(this.airplaneService.confirmAirplaneServiceAbility(airplaneId)));
    }

    @Operation(
            summary = "Метод назначения ремонта самолета.",
            description = "4.1.Главный инженер принимает решение по техническому осмотру, если не ОК, то назначает Инженеру ремонт самолета",
            parameters = {
                    @Parameter(name = "airplaneId",
                                description = "ID самолета",
                                schema = @Schema(type = "Long"),
                                required = true),
                    @Parameter(name = "userId",
                                description = "ID инженера",
                                schema = @Schema(type = "Long"),
                                required = true),
            }
    )
    @PreAuthorize(value = "hasRole('СHIEF_ENGINEER')")
    @PutMapping(value = "/assignRepairs/{id}")
    public ResponseEntity<?> assignAirplaneToRepairs(
            @PathVariable(value = "id") Long airplaneId,
            @RequestParam Long userId
    )
            throws EngineerIsBusyException,
            AirplaneNotFoundException,
            AirplanePartCheckupNotFoundException,
            StatusChangeException,
            AppUserNotFoundException
    {
        return ResponseEntity.ok(AirplaneMapper.mapAirplaneEntityToDto(this.airplaneService.assignAirplaneRepairs(airplaneId, userId)));
    }

    @Operation(
            summary = "Отправка самолета на подтверждение регистрации",
            description = "5.Диспетчер отправляет самолет на подтверждение регистрации Главному диспетчеру.",
            parameters = {
                    @Parameter(name = "airplaneId",
                                description = "ID самолета",
                                schema = @Schema(type = "Long"),
                                required = true)
            }
    )
    @PreAuthorize(value = "hasRole('DISPATCHER')")
    @PutMapping(value = "/sendToConfirmation/{id}")
    public ResponseEntity<?> sendAirplaneToRegistrationConfirmation(@PathVariable(value = "id") Long airplaneId)
            throws AirplaneNotFoundException, StatusChangeException
    {
        return ResponseEntity.ok(AirplaneMapper.mapAirplaneEntityToDto(this.airplaneService.sendAirplaneToRegistrationConfirmation(airplaneId)));
    }

    @Operation(
            summary = "Подтверждение отправки рейса",
            description = "6.Главный диспетчер подтверждает регистрацию и самолет регистрируется в системе как доступный.",
            parameters = {
                    @Parameter(name = "airplaneId",
                            description = "ID самолета",
                            schema = @Schema(type = "Long"),
                            required = true)
            }
    )
    @PreAuthorize(value = "hasRole('СHIEF_DISPATCHER')")
    @PutMapping(value = "/confirmRegistration/{id}")
    public ResponseEntity<?> confirmAirplaneRegistration(@PathVariable(value = "id") Long airplaneId)
            throws AirplaneNotFoundException, StatusChangeException
    {
        return ResponseEntity.ok(AirplaneMapper.mapAirplaneEntityToDto(this.airplaneService.confirmAirplaneRegistration(airplaneId)));
    }

    @Operation(
            summary = "Главный инженер назначает Инженера для заправки самолета..",
            description = "Главный инженер назначает Инженера для заправки самолета.",
            parameters = {
                    @Parameter(name = "airplaneId",
                                description = "ID самолета",
                                schema = @Schema(type = "Long"),
                                required = true),
                    @Parameter(name = "engineerId",
                                description = "ID инженера",
                                schema = @Schema(type = "Long"),
                                required = true)
            }
    )
    @PreAuthorize(value = "hasRole('CHIEF_ENGINEER')")
    @PutMapping(value = "/assignRefueling/{id}")
    public ResponseEntity<?> assignAirplaneRefueling(
            @PathVariable(value = "id") Long airplaneId,
            @RequestParam Long engineerId
    )
            throws AirplaneNotFoundException,
            EngineerIsBusyException,
            StatusChangeException,
            AppUserNotFoundException,
            FlightNotFoundException
    {
        return ResponseEntity.ok(AirplaneMapper.mapAirplaneEntityToDto(this.airplaneService.assignAirplaneRefueling(airplaneId, engineerId)));
    }

    @Operation(
            summary = "Заправка самолета.",
            description = "Заправка самолета выполняется работником банка с ролью Инженер",
            parameters = {
                    @Parameter(name = "airplaneId",
                            description = "ID самолета",
                            schema = @Schema(type = "Long"),
                            required = true)
            }
    )
    @PreAuthorize(value = "hasRole('ENGINEER')")
    @PutMapping(value = "/refuelAirplane/{id}")
    public ResponseEntity<?> refuelAirplane(@PathVariable(value = "id") Long airplaneId)
            throws AirplaneNotFoundException,
            StatusChangeException,
            EngineerIsBusyException
    {
        return ResponseEntity.ok(AirplaneMapper.mapAirplaneEntityToDto(this.airplaneService.refuelAirplane(airplaneId)));
    }

    @Operation(
            summary = "Просмотр всех самолетов",
            description = "Просмотр всех самолетов выполняется работниками банка с ролью (Управляющий директор, Главный диспетчер, Диспетчер, Главный инженер, Инженер",
            parameters = {
                    @Parameter(name = "airplaneType",
                            description = "Марка самолета",
                            schema = @Schema(type = "AirplaneType"),
                            required = false),
                    @Parameter(name = "airplaneStatus",
                            description = "Статус самолета",
                            schema = @Schema(type = "AirplaneStatus"),
                            required = false),
                    @Parameter(name = "registeredAfter",
                            description = "Дата начала регистрации",
                            schema = @Schema(type = "LocalDateTime"),
                            required = false),
                    @Parameter(name = "registeredBefore",
                            description = "Дата конца регистрации",
                            schema = @Schema(type = "LocalDateTime"),
                            required = false)
            }
    )
    @PreAuthorize(value = "hasAnyRole('CHIEF', 'CHIEF_DISPATCHER', 'DISPATCHER', 'CHIEF_ENGINEER', 'ENGINEER')")
    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllAirplanes(

            @RequestParam(name = "dateRegisterBeg", required = true)
            @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
            LocalDateTime dateRegisterBeg,
            @RequestParam(name = "dateRegisterEnd", required = true)
            @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
            LocalDateTime dateRegisterEnd,
            @RequestParam(required = false) AirplaneType airplaneType,
            @RequestParam(required = false) AirplaneStatus airplaneStatus
    )
            throws AirplaneNotFoundException, IncorrectFiltersException
    {
        return ResponseEntity.ok(AirplaneMapper.mapAirplaneEntityListToDto(this.airplaneService.getAllAirplanes(dateRegisterBeg, dateRegisterEnd, airplaneType, airplaneStatus))); //List<Airplane>
    }

    @Operation(
            summary = "Просмотр новых самолетов",
            description = "Просмотр новых самолетов выполняется работниками банка с ролью (Управляющий директор, Инженер",
            parameters = {
                    @Parameter(name = "airplaneType",
                            description = "Марка самолета",
                            schema = @Schema(type = "AirplaneType"),
                            required = false),
                    @Parameter(name = "registeredAfter",
                            description = "Дата начала регистрации",
                            schema = @Schema(type = "LocalDateTime"),
                            required = false),
                    @Parameter(name = "registeredBefore",
                            description = "Дата конца регистрации",
                            schema = @Schema(type = "LocalDateTime"),
                            required = false)
            }
    )
    @PreAuthorize(value = "hasAnyRole('CHEIF', 'ENGINEER')")
    @GetMapping(value = "/new")
    public ResponseEntity<?> getNewAirplanes(

            @RequestParam(required = true)
            @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
            LocalDateTime dateRegisterBeg,
            @RequestParam(required = true)
            @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
            LocalDateTime dateRegisterEnd,
            @RequestParam(required = false) AirplaneType airplaneType
    )
            throws AirplaneNotFoundException, IncorrectFiltersException
    {
        return ResponseEntity.ok(AirplaneMapper.mapAirplaneEntityListToDto(this.airplaneService.getNewAirplanes(dateRegisterBeg, dateRegisterEnd, airplaneType))); //List<AirplaneResponseDto>
    }

    @Operation(
            summary = "Просмотр самолетов, отправленных на ремонт",
            description = "Просмотр самолетов, отправленных на ремонт выполняется работниками банка с ролью (Управляющий директор, Инженер)",
            parameters = {
                    @Parameter(name = "airplaneType",
                            description = "Марка самолета",
                            schema = @Schema(type = "AirplaneType"),
                            required = false),
                    @Parameter(name = "registeredAfter",
                            description = "Дата начала регистрации",
                            schema = @Schema(type = "LocalDateTime"),
                            required = false),
                    @Parameter(name = "registeredBefore",
                            description = "Дата конца регистрации",
                            schema = @Schema(type = "LocalDateTime"),
                            required = false)
            }
    )
    @PreAuthorize(value = "hasAnyRole('CHIEF', 'ENGINEER')")
    @GetMapping(value = "/forRepairs")
    public ResponseEntity<?> getAirplanesForRepairs(
            @RequestParam(required = false) AirplaneType airplaneType,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
            LocalDateTime registeredAfter,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
            LocalDateTime registeredBefore
    )
            throws AirplaneNotFoundException, IncorrectFiltersException
    {
        return ResponseEntity.ok(AirplaneMapper.mapAirplaneEntityListToDto(this.airplaneService.getAirplanesForRepairs(airplaneType, registeredBefore, registeredAfter)));
    }

    @Operation(
            summary = "Просмотр самолетов, отправленных на заправку",
            description = "Просмотр самолетов, отправленных на заправку выполняется работниками банка с ролью (Управляющий директор, Инженер)",
            parameters = {
                    @Parameter(name = "airplaneType",
                            description = "Марка самолета",
                            schema = @Schema(type = "AirplaneType"),
                            required = false),
                    @Parameter(name = "registeredAfter",
                            description = "Дата начала регистрации",
                            schema = @Schema(type = "LocalDateTime"),
                            required = false),
                    @Parameter(name = "registeredBefore",
                            description = "Дата конца регистрации",
                            schema = @Schema(type = "LocalDateTime"),
                            required = false)
            }
    )
    @PreAuthorize(value = "hasAnyRole('CHIEF', 'ENGINEER')")
    @GetMapping(value = "/forRefueling")
    public ResponseEntity<?> getAirplaneForRefueling(
            @RequestParam(required = false) AirplaneType airplaneType,
            @RequestParam(required = true)
            @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
            LocalDateTime registeredBefore,
            @RequestParam(required = true)
            @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
            LocalDateTime registeredAfter
    )
            throws AirplaneNotFoundException, IncorrectFiltersException
    {
        return ResponseEntity.ok(AirplaneMapper.mapAirplaneEntityListToDto(this.airplaneService.getAirplanesForRefueling(airplaneType, registeredBefore, registeredAfter)));
    }

    @Operation(
            summary = "Просмотр всех марок самолетов",
            description = "Просмотр всех марок самолетов (Управляющий директор, Главный диспетчер, Диспетчер, Главный инженер, Инженер)"
    )
    @PreAuthorize(value = "hasAnyRole('DISPATCHER', 'CHIEF', 'ENGINEER', 'CHIEF_ENGINEER', 'CHIEF_DISPATCHER')")
    @GetMapping(value = "/airplaneTypes")
    public ResponseEntity<?> getAirplaneTypes() {
        return ResponseEntity.ok(this.airplaneService.getAllAirplaneTypes());
    }

    @Operation(
            summary = "Метод удаления самолета Диспетчером",
            description = "Удаление нового самолета выполняется работником банка с ролью Диспетчер",
            parameters = {
                    @Parameter(name = "airplaneId",
                            description = "ID самолета",
                            schema = @Schema(type = "Long"),
                            required = true)
            }
    )
    @DeleteMapping(value = "/deleteAirplane/{id}")
    @PreAuthorize("hasAnyRole('DISPATCHER')")
    public ResponseEntity<?> deleteNewAirplane(@PathVariable(value = "id") Long airplaneId)
            throws IllegalArgumentException, AirplaneNotFoundException, AirplaneSeatNotFoundException
    {
        boolean isDeleted = this.airplaneService.deleteNewAirplane(airplaneId);
        if (isDeleted) {
            return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "Самолет успешно удален!."));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
