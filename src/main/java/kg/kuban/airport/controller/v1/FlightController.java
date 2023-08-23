package kg.kuban.airport.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.kuban.airport.dto.AirportRequestDto;
import kg.kuban.airport.dto.FlightRequestDto;
import kg.kuban.airport.dto.FlightResponseDto;
import kg.kuban.airport.entity.Flight;
import kg.kuban.airport.enums.FlightStatus;
import kg.kuban.airport.exception.*;
import kg.kuban.airport.mapper.AirplaneMapper;
import kg.kuban.airport.mapper.FlightMapper;
import kg.kuban.airport.service.AppRoleService;
import kg.kuban.airport.service.AppUserService;
import kg.kuban.airport.service.FlightService;
import kg.kuban.airport.service.SeatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/flights")
@Tag(
        name = "Контроллер для рейсов самолетов",
        description = "Описывает точки доступа по рейсам аэропорта"
)
public class FlightController {

    private final FlightService flightService;
    private final SeatService seatService;

    private final Logger logger = LoggerFactory.getLogger(AppUserController.class);

    @Autowired
    public FlightController(FlightService flightService, SeatService seatService) {
        this.flightService = flightService;
        this.seatService = seatService;
    }

    @Operation(
            summary = "Создание нового рейса",
            description = "Пользовотель с ролью Диспетчер выполняет создание нового рейса",
            parameters = {
                    @Parameter(name = "flightRequestDto", description = "Данные рейса")
            }
    )
    @PostMapping("/register")
    @PreAuthorize("hasAnyRole('DISPATCHER')")
    public ResponseEntity<?> registerNewFlight(@RequestBody FlightRequestDto flightRequestDto)
            throws AirplaneNotFoundException, UnavailableAirplaneException
    {
        return ResponseEntity.ok(this.flightService.registerNewFlight(flightRequestDto));
    }


    @Operation(
            summary = "Инициирование отправки рейса ",
            description = "Пользовотель с ролью Диспетчер выполняет Инициирование отправки рейса",
            parameters = {
                    @Parameter(name = "flightId", description = "ID самолета")
            }
    )
    @PreAuthorize(value = "hasRole('DISPATCHER')")
    @PutMapping(value = "/initFlightDeparture")
    public ResponseEntity<?> initiateFlightDeparture(@RequestParam Long flightId)
            throws StatusChangeException, FlightNotFoundException
    {
        return ResponseEntity.ok(FlightMapper.mapFlightEntityToDto(this.flightService.initiateFlightDeparturePreparations(flightId)));
    }

    @Operation(
            summary = "Назначение Инженера для заправки самолета",
            description = "Главный инженер назначает Инженера для заправки самолета.",
            parameters = {
                    @Parameter(name = "flightId", description = "ID самолета")
            }
    )
    @PreAuthorize(value = "hasRole('CHIEF_ENGINEER')")
    @PutMapping(value = "/confirmRefueling")
    public ResponseEntity<?> confirmRefueling(@RequestParam Long flightId)
            throws AirplaneNotReadyException,
            StatusChangeException,
            FlightNotFoundException
    {
        return ResponseEntity.ok(FlightMapper.mapFlightEntityToDto(this.flightService.confirmAirplaneRefueling(flightId)));
    }

    @Operation(
            summary = "Подтверждение нового рейса.",
            description = "Пользователь с ролью Главный Диспетчер подтверждает Регистрацию нового рейса",
            parameters = {
                    @Parameter(name = "flightId", description = "ID рейса")
            }
    )
    @PreAuthorize(value = "hasRole('CHIEF_DISPATCHER')")
    @PutMapping(value = "/confirmFlightRegistration")
    public ResponseEntity<?> confirmFlightRegistration(@RequestParam Long flightId)
            throws StatusChangeException,
            FlightNotFoundException
    {
        return ResponseEntity.ok(FlightMapper.mapFlightEntityToDto(this.flightService.confirmFlightRegistration(flightId)));
    }

    @Operation(
            summary = "Диспетчер оповещает Пилота, Главного стюарда и Стюардов о начале рейса.",
            description = "Пользователь с ролью Диспетчер иницирует экипаж по рейсу",
            parameters = {
                    @Parameter(name = "flightId", description = "ID рейса")
            }
    )
    @PreAuthorize(value = "hasRole('DISPATCHER')")
    @PutMapping(value = "/initСrewOnFlight")
    public ResponseEntity<?> initCrewOnFlight(@RequestParam Long flightId)
            throws StatusChangeException, FlightNotFoundException
    {
        return ResponseEntity.ok(FlightMapper.mapFlightEntityToDto(this.flightService.initiateCrewPreparation(flightId)));
    }

    @Operation(
            summary = "Запрос на посадку рейса.",
            description = "Пользователь с ролью Пилот запрашивает посадку по рейсу",
            parameters = {
                    @Parameter(name = "flightId", description = "ID рейса")
            }
    )
    @PreAuthorize(value = "hasRole('PILOT')")
    @PutMapping(value = "/requestLanding")
    public ResponseEntity<?> requestLanding(@RequestParam Long flightId)
            throws StatusChangeException, FlightNotFoundException
    {
        return ResponseEntity.ok(FlightMapper.mapFlightEntityToDto(this.flightService.requestLanding(flightId)));
    }

    @Operation(
            summary = "Принятие рейса по посадке.",
            description = "Пользователь с ролью Диспетчер принимает посадку по рейсу",
            parameters = {
                    @Parameter(name = "flightId", description = "ID рейса")
            }
    )
    @PreAuthorize(value = "hasRole('DISPATCHER')")
    @PutMapping(value = "/assignLanding")
    public ResponseEntity<?> assignLanding(@RequestParam Long flightId)
            throws StatusChangeException, FlightNotFoundException
    {
        return ResponseEntity.ok(FlightMapper.mapFlightEntityToDto(this.flightService.assignLanding(flightId)));
    }

    @Operation(
            summary = "Подтверждение принятия рейса по посадке.",
            description = "Пользователь с ролью Главный Диспетчер подтверждает принятие по рейсу",
            parameters = {
                    @Parameter(name = "flightId", description = "ID рейса")
            }
    )
    @PreAuthorize(value = "hasRole('CHIEF_DISPATCHER')")
    @PutMapping(value = "/confirm-landing")
    public ResponseEntity<?> confirmLanding(@RequestParam Long flightId)
            throws StatusChangeException, FlightNotFoundException
    {
        return ResponseEntity.ok(FlightMapper.mapFlightEntityToDto(this.flightService.confirmLanding(flightId)));
    }

    @Operation(
            summary = "Окончание рейса.",
            description = "Пользователь с ролью Пилот выполняет окончание рейса",
            parameters = {
                    @Parameter(name = "flightId", description = "ID рейса")
            }
    )
    @PreAuthorize(value = "hasRole('PILOT')")
    @PutMapping(value = "/endFlight")
    public ResponseEntity<?> endFlight(@RequestParam Long flightId)
            throws StatusChangeException, FlightNotFoundException
    {
        return ResponseEntity.ok(FlightMapper.mapFlightEntityToDto(this.flightService.endFlight(flightId)));
    }

    @Operation(
            summary = "Просмотр доступных рейсов",
            description = "Пользователь с ролью Пилот выполняет окончание рейса",
            parameters = {
                    @Parameter(name = "flightId", description = "ID рейса")
            }
    )
    @PreAuthorize(value = "hasAnyRole('CHIEF', 'CHIEF_DISPATCHER', 'DISPATCHER', 'PILOT', 'CUSTOMER')")
    @GetMapping(value = "/availableFlights")
    public ResponseEntity<?> getAvailableFlights(
            @RequestParam(required = false) LocalDateTime dateRegisterBeg,
            @RequestParam(required = false) LocalDateTime dateRegisterEnd,
            @RequestParam(required = false) FlightStatus flightStatus
    )
            throws IncorrectFiltersException, FlightNotFoundException
    {
        return ResponseEntity.ok(FlightMapper.mapFlightEntityListToDto(this.flightService.getAvailableFlights(dateRegisterBeg, dateRegisterEnd, flightStatus)));
    }

    @Operation(
            summary = "Просмотр рейсов для резервирования билетов ",
            description = "Пользователь с ролью Клиент или Управляющий директор выполняет Просмотр рейсов для резервирования билетов",
            parameters = {
                    @Parameter(name = "flightId", description = "ID рейса")
            }
    )
    @PreAuthorize(value = "hasAnyRole('CHIEF', 'CUSTOMER')")
    @GetMapping(value = "/sellingTickets")
    public ResponseEntity<?> getFlightsForTicketBooking(
            @RequestParam(required = false) LocalDateTime dateCreatedBeg,
            @RequestParam(required = false) LocalDateTime dateCreatedEnd,
            @RequestParam(required = false) AirportRequestDto flightDestination
    )
            throws IncorrectFiltersException, FlightNotFoundException
    {
        return ResponseEntity.ok(FlightMapper.mapFlightEntityListToDto(this.flightService.getFlightsForTicketBooking(dateCreatedBeg, dateCreatedEnd, flightDestination)));
    }

    @Operation(
            summary = "Просмотр мест для резервирования",
            description = "Пользователь с ролью Клиент выполняет Просмотр мест для резервирования",
            parameters = {
                    @Parameter(name = "flightId", description = "ID рейса"),
                    @Parameter(name = "isOccupied", description = "признак занятости")
            }
    )
    @PreAuthorize(value = "hasRole('CUSTOMER')")
    @GetMapping(value = "/airplaneSeats/{id}")
    public ResponseEntity<?> getAllAirplaneSeats(
            @PathVariable Long flightId,
            @RequestParam Boolean isOccupied
    )
            throws FlightNotFoundException, AirplaneSeatNotFoundException
    {
        Flight flight = this.flightService.getFlightEntityByFlightId(flightId);
        return ResponseEntity.ok(AirplaneMapper.mapToAirplaneSeatResponseDtoList(this.seatService.getAllSeats(flight.getAirplane().getId(), isOccupied)));
    }
}
