package kg.kuban.airport.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.kuban.airport.dto.UserFlightRegistrationResponseDto;
import kg.kuban.airport.dto.UserFlightRequestDto;
import kg.kuban.airport.entity.UserFlight;
import kg.kuban.airport.enums.UserFlightStatus;
import kg.kuban.airport.exception.*;
import kg.kuban.airport.mapper.FlightMapper;
import kg.kuban.airport.mapper.UserFlightMapper;
import kg.kuban.airport.service.FlightService;
import kg.kuban.airport.service.UserFlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/flights/users")
@Tag(
        name = "Контроллер регистрации клиента на рейс + регистрации экипажа на рейс",
        description = "Описывает точки доступа по регистрации на рейс ролями Клиент, Диспетчер"
)
public class UserFlightController {
    private final FlightService flightService;
    private final UserFlightService userFlightService;

    @Autowired
    public UserFlightController(
            FlightService flightService,
            UserFlightService userFlightService
    ) {
        this.flightService = flightService;
        this.userFlightService = userFlightService;
    }

    @Operation(
            summary = "Регистрация экипажа на рейс",
            description = "Регистрация экипажа на рейс выполняется с ролью Диспетчер",
            parameters = {
                    @Parameter(name = "List<UserFlightRequestDto>",
                                description = "Список UserFlight",
                                schema = @Schema(type = "List<UserFlightRequestDto>"))
            }
    )
    @PreAuthorize(value = "hasRole('DISPATCHER')")
    @PostMapping(value = "/crew/register")
    public ResponseEntity<?> registerCrewMembersForFlight(@RequestBody List<UserFlightRequestDto> requestDtoList)
            throws InvalidUserRoleException,
            IllegalFlightException,
            NotEnoughRolesForCrewRegistrationException,
            AppUserNotFoundException,
            FlightNotFoundException,
            NotRegisteredFlightException,
            NotEnoughRolesForCrewRegistrationException,
            InvalidUserRoleException

    {
        List<UserFlight> responseDtoList = this.userFlightService.registerEmployeesForFlight(requestDtoList);
        return ResponseEntity.ok(UserFlightMapper.mapUserFlightEntityListToDto(responseDtoList));
    }

    @Operation(
            summary = "Регистрация на рейс",
            description = "Регистрация на рейс выполняется с ролью Клиент",
            parameters = {
                    @Parameter(name = "UserFlightRequestDto",
                                description = "DTO клиента",
                                schema = @Schema(type = "UserFlightRequestDto"))
            }
    )
    @PreAuthorize(value = "hasRole('CUSTOMER')")
    @PostMapping(value = "/customers/booking")
    public ResponseEntity<?> bookingCustomerForFlight(
            @RequestBody UserFlightRequestDto requestDto
    )
            throws IllegalFlightException,
            AirplaneSeatNotFoundException,
            SeatBookingException,
            NotRegisteredFlightException,
            FlightNotFoundException,
            SeatBookingException
    {
        return ResponseEntity.ok(FlightMapper.mapToUserFlightRegistrationResponseDto(userFlightService.bookingCustomerForFlight(requestDto)));
    }

    @Operation(
            summary = "Отмена регистрации на рейс",
            description = "Отмена регистрации на рейс выполняется с ролью Клиент",
            parameters = {
                    @Parameter(name = "bookingId", description = "ID регистрации на рейс", schema = @Schema(type = "Long"))
            }
    )
    @PreAuthorize(value = "hasRole('CUSTOMER')")
    @PutMapping(value = "/customers/cancelBooking")
    public ResponseEntity<?> cancelCustomerBookingForFlight(@RequestParam Long bookingId)
            throws AirplaneSeatNotFoundException,
            SeatBookingException,
            TicketCancelingException,
            UserFlightNotFoundException,
            FlightNotFoundException
    {
        return ResponseEntity.ok(FlightMapper.mapToUserFlightRegistrationResponseDto(userFlightService.cancelCustomerBooking(bookingId)));
    }

    @Operation(
            summary = "Проведение инструктажа клиенту",
            description = "Проведение инструктажа клиенту выполняется с ролью Стюард",
            parameters = {
                    @Parameter(name = "bookingId", description = "ID регистрации на рейс", schema = @Schema(type = "Long"))
            }
    )
    @PreAuthorize(value = "hasRole('STEWARD')")
    @PutMapping(value = "/customers/briefCustomer")
    public UserFlightRegistrationResponseDto conductCustomerBriefing(
            @RequestParam Long bookingId
    )
            throws UserFlightNotFoundException,
            StatusChangeException, FlightNotFoundException
    {
        UserFlightRegistrationResponseDto responseDto = UserFlightMapper.mapToUserFlightRegistrationResponseDto(this.userFlightService.conductCustomersBriefing(bookingId));
        if(
                this.userFlightService.checkIfAllPassengersOfFlightHaveStatus(
                        responseDto.getFlightId(),
                        UserFlightStatus.CUSTOMER_BRIEFED
                )
        ) {
            this.flightService.informThatAllCustomersAreBriefed(responseDto.getFlightId());
        }
        return responseDto;
    }

    @Operation(
            summary = "Проверка клиента на готовность к рейсу",
            description = "Проверка клиента на готовность к рейсу выполняется с ролью Стюард",
            parameters = {
                    @Parameter(name = "bookingId", description = "ID регистрации на рейс", schema = @Schema(type = "Long"))
            }
    )
    @PreAuthorize(value = "hasRole('STEWARD')")
    @PutMapping(value = "/customers/checkCustomer")
    public ResponseEntity<?> checkCustomer(@RequestParam Long bookingId)
            throws UserFlightNotFoundException,
            StatusChangeException,
            FlightNotFoundException
    {
        UserFlightRegistrationResponseDto responseDto = UserFlightMapper.mapToUserFlightRegistrationResponseDto(this.userFlightService.checkCustomer(bookingId));
        if (
                this.userFlightService.checkIfAllPassengersOfFlightHaveStatus(
                        responseDto.getFlightId(),
                        UserFlightStatus.CUSTOMER_CHECKED
                )
        ) {
            this.flightService.informThatAllCustomersAreBriefed(responseDto.getFlightId());
        }
        return ResponseEntity.ok(responseDto);
    }

    @Operation(
            summary = "Раздача еды",
            description = "Раздача еды выполняется с ролью Стюард",
            parameters = {
                    @Parameter(name = "bookingId", description = "ID регистрации на рейс", schema = @Schema(type = "Long"))
            }
    )
    @PreAuthorize(value = "hasRole('STEWARD')")
    @PutMapping(value = "/customers/distributeCustomersFood")
    public ResponseEntity<?> distributeCustomersFood(@RequestParam Long bookingId)
            throws UserFlightNotFoundException,
            StatusChangeException, FlightNotFoundException {
        UserFlightRegistrationResponseDto responseDto = UserFlightMapper.mapToUserFlightRegistrationResponseDto(this.userFlightService.distributeCustomersFood(bookingId));
        if (this.userFlightService.checkIfAllPassengersOfFlightHaveStatus(
                        responseDto.getFlightId(),
                        UserFlightStatus.CUSTOMER_FOOD_DISTRIBUTED)) {
            this.flightService.informThatAllCustomersAreBriefed(responseDto.getFlightId());
        }
        return ResponseEntity.ok(responseDto);  //UserFlightRegistrationResponseDto
    }

    @Operation(
            summary = "Подтверждение готовности к рейсу",
            description = "Подтверждение готовности к рейсу выполняется с ролью Стюард, Главный Стюард, Пилот"
    )
    @PreAuthorize(value = "hasAnyRole('STEWARD', 'CHIEF_STEWARD', 'PILOT')")
    @PutMapping(value = "/crewMembers/confirmReadiness")
    public ResponseEntity<?> confirmReadiness()
            throws UserFlightNotFoundException,
            StatusChangeException,
            FlightNotFoundException
    {
        UserFlightRegistrationResponseDto responseDto = UserFlightMapper.mapToUserFlightRegistrationResponseDto(this.userFlightService.confirmReadinessForFlight());
        if(this.userFlightService.checkIfAllCrewMembersIsReadyForFlight(responseDto.getFlightId())) {
            this.flightService.informThatAllCrewMembersIsReadyForFlight(responseDto.getFlightId());
        }
        return ResponseEntity.ok(responseDto);
    }

    @Operation(
            summary = "Просмотр всех регистрации клиентов",
            description = "Просмотр всех регистрации клиентов выполняется с ролью Управляющий директор",
            parameters = {
                    @Parameter(name = "flightId", description = "ID рейса", schema = @Schema(type = "Long"), required = true),
                    @Parameter(name = "customerStatus", description = "Статус рейса по клиенту", schema = @Schema(type = "UserFlightStatus"), required = false)
            }
    )
    @PreAuthorize(value = "hasAnyRole('CHIEF')")
    @GetMapping(value = "/customers/all")
    public ResponseEntity<?> getCustomersBookings(
            @RequestParam(required = false) Long flightId,
            @RequestParam(required = false) UserFlightStatus customerStatus
    )
            throws UserFlightNotFoundException
    {
        return ResponseEntity.ok(this.userFlightService.getAllCustomerBookings(flightId, customerStatus));  //List<UserFlightRegistrationResponseDto>
    }

    @Operation(
            summary = "Просмотр всех регистрации работников аэропорта",
            description = "Просмотр всех регистрации клиентов выполняется с ролью Управляющий директор",
            parameters = {
                    @Parameter(name = "flightId", description = "ID рейса", schema = @Schema(type = "Long"), required = false),
                    @Parameter(name = "status", description = "Статус рейса", schema = @Schema(type = "UserFlightStatus"), required = false)
            }
    )
    @PreAuthorize(value = "hasAnyRole('CHIEF')")
    @GetMapping(value = "/employees/all")
    public ResponseEntity<?> getEmployeesBookingsForFlights(
            @RequestParam(required = false) Long flightId,
            @RequestParam(required = false) UserFlightStatus status
    )
            throws UserFlightNotFoundException
    {
        return ResponseEntity.ok(UserFlightMapper.mapUserFlightEntityListToDto(this.userFlightService.getAllEmployeesBookings(flightId, status)));  //List<UserFlightRegistrationResponseDto>
    }

    @Operation(
            summary = "Просмотр регистрации клиентов по текущему рейсу",
            description = "Просмотр регистрации клиентов по текущему рейсу выполняется с ролью Главный стюард, Стюард",
            parameters = {
                     @Parameter(name = "customerStatus", description = "Статус рейса", schema = @Schema(type = "UserFlightStatus"), required = false)
            }
    )
    @PreAuthorize(value = "hasAnyRole('CHIEF_STEWARD', 'STEWARD')")
    @GetMapping(value = "/customers/currentFlight")
    public ResponseEntity<?> getCustomerBookingsForCurrentFlight(
            @RequestParam(required = false) UserFlightStatus customerStatus
    ) throws UserFlightNotFoundException
    {
        return ResponseEntity.ok(UserFlightMapper.mapUserFlightEntityListToDto(this.userFlightService.getAllCustomerBookingsForCurrentFlight(customerStatus))); //List<UserFlightRegistrationResponseDto>
    }

    @Operation(
            summary = "Просмотр текущего рейса",
            description = "Просмотр текущего рейса выполняется с ролью Клиент"
    )
    @PreAuthorize(value = "hasRole('CUSTOMER')")
    @GetMapping(value = "/customers/customerCurrentFlight")
    public ResponseEntity<?> getCustomersCurrentFlight() throws UserFlightNotFoundException {
        return ResponseEntity.ok(UserFlightMapper.mapToUserFlightRegistrationResponseDto(this.userFlightService.getCurrentFlight()));  //UserFlightRegistrationResponseDto
    }

    @Operation(
            summary = "Просмотр своих прошлых рейсов",
            description = "Просмотр своих прошлых рейсов выполняется с ролью Клиент",
            parameters = {
                    @Parameter(name = "status", description = "Статус рейса", schema = @Schema(type = "UserFlightStatus"), required = false)
            }
    )
    @PreAuthorize(value = "hasRole('CUSTOMER')")
    @GetMapping(value = "/customers/customerFlightHistory")
    public ResponseEntity<?> getCustomersFlightHistory(
            @RequestParam(required = false) UserFlightStatus status) throws UserFlightNotFoundException
    {
        return ResponseEntity.ok(UserFlightMapper.mapUserFlightEntityListToDto(this.userFlightService.getCustomersFlightBookingHistory(status)));  //List<UserFlightRegistrationResponseDto>
    }
}
