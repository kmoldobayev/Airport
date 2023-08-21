package kg.kuban.airport.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.kuban.airport.dto.AirplaneRequestDto;
import kg.kuban.airport.dto.AirplanePartCheckupRequestDto;
import kg.kuban.airport.exception.*;
import kg.kuban.airport.mapper.AirplaneMapper;
import kg.kuban.airport.mapper.PartCheckupMapper;
import kg.kuban.airport.response.SuccessResponse;
import kg.kuban.airport.service.AirplaneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
                    @Parameter(name = "AirplaneRequestDto", description = "DTO самолета")
            }
    )
    @PostMapping("/register")
    @PreAuthorize("hasAnyRole('DISPATCHER')")
    public ResponseEntity<?> registerNewAirplane(@RequestBody AirplaneRequestDto airplaneRequestDto)
            throws PartNotFoundException,
            IncompatiblePartException
    {
        logger.info("registerNewAirplane");
        return ResponseEntity.ok(AirplaneMapper.mapAirplaneEntityToDto(this.airplaneService.registerNewAirplane(airplaneRequestDto)));
    }

    @Operation(
            summary = "Метод удаления самолета Диспетчером",
            description = "Удаление нового самолета выполняется работником банка с ролью Диспетчер",
            parameters = {
                    @Parameter(name = "airplaneId", description = "ID самолета")
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
                    @Parameter(name = "AirplaneRequestDto", description = "DTO самолета")
            }
    )
    @PostMapping("/assignCheckup/{id}")
    @PreAuthorize(value = "hasAnyRole('CHIEF_ENGINEER')")
    public ResponseEntity<?> assignAirplaneToInspection( @PathVariable Long airplaneId, @RequestParam Long userId)
            throws AirplaneNotFoundException, StatusChangeException, EngineerIsBusyException {
        return ResponseEntity.ok(AirplaneMapper.mapAirplaneEntityToDto(this.airplaneService.assignAirplaneCheckup(airplaneId, userId)));
    }

    @PutMapping(value = "/confirmServiceability/{id}")
    @PreAuthorize(value = "hasRole('CHIEF_ENGINEER')")
    public ResponseEntity<?> confirmAircraftServiceability(@PathVariable Long airplaneId)
            throws AirplaneNotFoundException,
            PartCheckupNotFoundException,
            StatusChangeException
    {
        return ResponseEntity.ok(this.airplaneService.confirmAirplaneServiceAbility(airplaneId));
    }

    @PreAuthorize(value = "hasRole('ENGINEER')")
    @PostMapping(value = "/checkup/{id}")
    public ResponseEntity<?> checkupAirplane(
            @PathVariable Long airplaneId,
            @RequestBody List<AirplanePartCheckupRequestDto> partInspectionsRequestDtoList
    )
            throws AirplaneNotFoundException,
            StatusChangeException,
            WrongEngineerException,
            AirplaneIsNotOnServiceException,
            PartNotFoundException,
            IllegalAirplaneException,
            IncompatiblePartException
    {
        return ResponseEntity.ok(PartCheckupMapper.mapToPartCheckupResponseDtoList(this.airplaneService.checkupAirplane(airplaneId, partInspectionsRequestDtoList)));
    }
}
