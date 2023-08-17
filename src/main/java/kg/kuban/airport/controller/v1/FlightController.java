package kg.kuban.airport.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.kuban.airport.dto.FlightRequestDto;
import kg.kuban.airport.dto.FlightResponseDto;
import kg.kuban.airport.mapper.AirplaneMapper;
import kg.kuban.airport.service.AppRoleService;
import kg.kuban.airport.service.AppUserService;
import kg.kuban.airport.service.FlightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/flights")
@Tag(
        name = "Контроллер для рейсов самолетов",
        description = "Описывает точки доступа по рейсам аэропорта"
)
public class FlightController {

    private final FlightService flightService;
    private final Logger logger = LoggerFactory.getLogger(AppUserController.class);

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @Operation(
            summary = "Регистрация нового самолета",
            description = "Пользовотель с ролью Диспетчер выполняет Регистрацию нового самолета",
            parameters = {
                    @Parameter(name = "FlightRequestDto", description = "Данные самолета")
            }
    )
    @PostMapping("/register")
    @PreAuthorize("hasAnyRole('DISPATCHER')")
    public ResponseEntity<?> registerNewFlight(@RequestBody FlightRequestDto flightRequestDto) {
        return ResponseEntity.ok(this.flightService.registerFlight(flightRequestDto));
    }
}
