package kg.kuban.airport.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.kuban.airport.dto.AirplaneRequestDto;
import kg.kuban.airport.exception.AirplaneNotFoundException;
import kg.kuban.airport.exception.EngineerException;
import kg.kuban.airport.exception.PartInspectionNotFoundException;
import kg.kuban.airport.exception.StatusChangeException;
import kg.kuban.airport.mapper.AirplaneMapper;
import kg.kuban.airport.service.AirplaneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> registerNewAirplane(@RequestBody AirplaneRequestDto airplaneRequestDto) {
        return ResponseEntity.ok(AirplaneMapper.mapAirplaneEntityToDto(this.airplaneService.registerNewAirplane(airplaneRequestDto)));
    }

    @Operation(
            summary = "Метод выдачи самолета на техосмотр",
            description = "Выдача самолета на техосмотр выполняется работником банка с ролью Главный инженер",
            parameters = {
                    @Parameter(name = "AirplaneRequestDto", description = "DTO самолета")
            }
    )
    @PostMapping("/assignInspection{id}")
    @PreAuthorize(value = "hasAnyRole('CHIEF_ENGINEER')")
    public ResponseEntity<?> assignAirplaneToInspection( @PathVariable Long airplaneId, @RequestParam Long userId) throws AirplaneNotFoundException, StatusChangeException, EngineerException {
        return ResponseEntity.ok(AirplaneMapper.mapAirplaneEntityToDto(this.airplaneService.assignInspection(airplaneId, userId)));
    }

    @PutMapping(value = "/confirmServiceability{id}")
    @PreAuthorize(value = "hasRole('CHIEF_ENGINEER')")
    public ResponseEntity<?> confirmAircraftServiceability(
            @PathVariable Long airplaneId
    )
            throws AirplaneNotFoundException,
            PartInspectionNotFoundException,
            StatusChangeException
    {
        return ResponseEntity.ok(this.airplaneService.confirmAirplaneServiceAbility(airplaneId));
    }
}
