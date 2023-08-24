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
            description = "Регистрация нового самолета выполняется работником банка с ролью Диспетчер",
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
            throws AirplanePartNotFoundException,
            IncompatiblePartException
    {
        logger.info("registerNewAirplane");
        return ResponseEntity.ok(AirplaneMapper.mapAirplaneEntityToDto(this.airplaneService.registerNewAirplane(airplaneRequestDto)));
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

    @Operation(
            summary = "Метод выдачи самолета на техосмотр",
            description = "Выдача самолета на техосмотр выполняется работником банка с ролью Главный инженер",
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

    @Operation(
            summary = "Метод составление технического осмотра самолета.",
            description = "Составление технического осмотра самолета выполняется работником банка с ролью Инженер",
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
            description = "Подтверждение исправности самолета выполняется работником банка с ролью Главный Инженер",
            parameters = {
                    @Parameter(name = "airplaneId", description = "ID самолета")
            }
    )
    @PutMapping(value = "/confirmServiceability/{id}")
    @PreAuthorize(value = "hasRole('CHIEF_ENGINEER')")
    public ResponseEntity<?> confirmAirplaneServiceability(@PathVariable Long airplaneId)
            throws AirplaneNotFoundException,
            AirplanePartCheckupNotFoundException,
            StatusChangeException
    {
        return ResponseEntity.ok(this.airplaneService.confirmAirplaneServiceAbility(airplaneId));
    }

    @Operation(
            summary = "Метод назначения ремонта самолета.",
            description = "Назначение ремонта самолета выполняется работником банка с ролью Главный Инженер",
            parameters = {
                    @Parameter(name = "airplaneId", description = "ID самолета"),
                    @Parameter(name = "userId", description = "ID инженера")
            }
    )
    @PreAuthorize(value = "hasRole('CHIEF_ENGINEER')")
    @PutMapping(value = "/assignRepairs/{id}/")
    public ResponseEntity<?> assignAirplaneToRepairs(
            @PathVariable Long airplaneId,
            @RequestParam Long userId
    )
            throws EngineerIsBusyException,
            AirplaneNotFoundException,
            AirplanePartCheckupNotFoundException,
            StatusChangeException,
            AppUserNotFoundException
    {
        return ResponseEntity.ok(this.airplaneService.assignAirplaneRepairs(airplaneId, userId));
    }

    @Operation(
            summary = "Метод назначения ремонта самолета.",
            description = "Назначение ремонта самолета выполняется работником банка с ролью Главный Инженер",
            parameters = {
                    @Parameter(name = "airplaneId", description = "ID самолета")
            }
    )
    @PreAuthorize(value = "hasRole('DISPATCHER')")
    @PutMapping(value = "/sendToConfirmation/{id}")
    public ResponseEntity<?> sendAirplaneToRegistrationConfirmation(@PathVariable Long airplaneId)
            throws AirplaneNotFoundException, StatusChangeException
    {
        return ResponseEntity.ok(this.airplaneService.sendAirplaneToRegistrationConfirmation(airplaneId));
    }

    @PreAuthorize(value = "hasRole('CHIEF_DISPATCHER')")
    @PutMapping(value = "/confirmRegistration/{id}")
    public ResponseEntity<?> confirmAirplaneRegistration(
            @PathVariable Long airplaneId
    )
            throws AirplaneNotFoundException,
            StatusChangeException
    {
        return ResponseEntity.ok(this.airplaneService.confirmAirplaneRegistration(airplaneId));
    }

    @Operation(
            summary = "Метод Подтверждение технического осмотра самолета.",
            description = "Подтверждение технического осмотра самолета работником банка с ролью Главный Инженер",
            parameters = {
                    @Parameter(name = "airplaneId", description = "ID самолета"),
                    @Parameter(name = "engineerId", description = "ID инженера")
            }
    )
    @PreAuthorize(value = "hasRole('CHIEF_ENGINEER')")
    @PutMapping(value = "/assign_refueling/{id}")
    public ResponseEntity<?> assignAirplaneRefueling(
            @PathVariable Long airplaneId,
            @RequestParam Long engineerId
    )
            throws AirplaneNotFoundException,
            EngineerIsBusyException,
            StatusChangeException,
            AppUserNotFoundException
    {
        return ResponseEntity.ok(this.airplaneService.assignAirplaneRefueling(airplaneId, engineerId));
    }

    @PreAuthorize(value = "hasRole('ENGINEER')")
    @PutMapping(value = "/refuelAirplane/{id}")
    public ResponseEntity<?> refuelAirplane(
            @PathVariable Long airplaneId
    )
            throws AirplaneNotFoundException,
            StatusChangeException,
            EngineerIsBusyException
    {
        return ResponseEntity.ok(this.airplaneService.refuelAirplane(airplaneId));
    }

    @PreAuthorize(value = "hasAnyRole('CHIEF', 'CHIEF_DISPATCHER', 'DISPATCHER', 'CHIEF_ENGINEER', 'ENGINEER')")
    @GetMapping(value = "/all")
    public List<Airplane> getAllAirplanes(
            @RequestParam(required = false) AirplaneType AirplaneType,
            @RequestParam(required = false) AirplaneStatus AirplaneStatus,
            @RequestParam(required = false) LocalDateTime registeredAfter,
            @RequestParam(required = false) LocalDateTime registeredBefore
    )
            throws AirplaneNotFoundException,
            IncorrectFiltersException
    {
        return this.airplaneService.getAllAirplanes(AirplaneType, AirplaneStatus, registeredBefore, registeredAfter);
    }

    @PreAuthorize(value = "hasAnyRole('CHEIF', 'ENGINEER')")
    @GetMapping(value = "/new")
    public ResponseEntity<?> getNewAirplanes(
            @RequestParam(required = false) AirplaneType AirplaneType,
            @RequestParam(required = false) LocalDateTime registeredAfter,
            @RequestParam(required = false) LocalDateTime registeredBefore
    )
            throws AirplaneNotFoundException, IncorrectFiltersException
    {
        return ResponseEntity.ok(AirplaneMapper.mapAirplaneEntityListToDto(this.airplaneService.getNewAirplanes(AirplaneType, registeredBefore, registeredAfter))); //List<AirplaneResponseDto>
    }

    @PreAuthorize(value = "hasAnyRole('CHIEF', 'ENGINEER')")
    @GetMapping(value = "/forRepairs")
    public ResponseEntity<?> getAirplanesForRepairs(
            @RequestParam(required = false) AirplaneType AirplaneType,
            @RequestParam(required = false) LocalDateTime registeredAfter,
            @RequestParam(required = false) LocalDateTime registeredBefore
    )
            throws AirplaneNotFoundException,
            IncorrectFiltersException
    {
        return ResponseEntity.ok(AirplaneMapper.mapAirplaneEntityListToDto(this.airplaneService.getAirplanesForRepairs(AirplaneType, registeredBefore, registeredAfter)));
    }

    @PreAuthorize(value = "hasAnyRole('CHIEF', 'ENGINEER')")
    @GetMapping(value = "/forRefueling")
    public ResponseEntity<?> getAirplaneForRefueling(
            @RequestParam(required = false) AirplaneType AirplaneType,
            @RequestParam(required = false) LocalDateTime registeredBefore,
            @RequestParam(required = false) LocalDateTime registeredAfter
    )
            throws AirplaneNotFoundException,
            IncorrectFiltersException
    {
        return ResponseEntity.ok(AirplaneMapper.mapAirplaneEntityListToDto(this.airplaneService.getAirplanesForRefueling(AirplaneType, registeredBefore, registeredAfter)));
    }

    @PreAuthorize(value = "hasAnyRole('DISPATCHER', 'CHIEF', 'ENGINEER', 'CHIEF_ENGINEER', 'CHIEF_DISPATCHER')")
    @GetMapping(value = "/airplaneTypes")
    public ResponseEntity<?> getAirplaneTypes() {
        return ResponseEntity.ok(this.airplaneService.getAllAirplaneTypes());
    }
}
