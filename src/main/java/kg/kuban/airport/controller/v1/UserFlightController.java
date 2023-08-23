package kg.kuban.airport.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping(value = "/flights/bookings")
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
                    @Parameter(name = "List<UserFlightRequestDto>", description = "Список UserFlight")
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
                    @Parameter(name = "UserFlightRequestDto", description = "DTO клиента")
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

    @PreAuthorize(value = "hasRole('CUSTOMER')")
    @PutMapping(value = "/Customers/cancelBooking")
    public ResponseEntity<?> cancelCustomerBookingForFlight(
            @RequestParam Long bookingId
    )
            throws AirplaneSeatNotFoundException,
            SeatBookingException,
            TicketCancelingException,
            UserFlightNotFoundException,
            FlightNotFoundException
    {
        return ResponseEntity.ok(FlightMapper.mapToUserFlightRegistrationResponseDto(userFlightService.cancelCustomerBooking(bookingId)));
    }

    @PreAuthorize(value = "hasRole('STEWARD')")
    @PutMapping(value = "/customers/briefCustomer")
    public UserFlightRegistrationResponseDto conductCustomerBriefing(
            @RequestParam Long registrationId
    )
            throws UserFlightNotFoundException,
            StatusChangeException, FlightNotFoundException
    {
        UserFlightRegistrationResponseDto responseDto = UserFlightMapper.mapToUserFlightRegistrationResponseDto(this.userFlightService.conductCustomersBriefing(registrationId));
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

    @PreAuthorize(value = "hasRole('STEWARD')")
    @PutMapping(value = "/customers/distributeCustomersFood")
    public ResponseEntity<?> distributeCustomersFood(@RequestParam Long registrationId)
            throws UserFlightNotFoundException,
            StatusChangeException, FlightNotFoundException {
        UserFlightRegistrationResponseDto responseDto = UserFlightMapper.mapToUserFlightRegistrationResponseDto(this.userFlightService.distributeCustomersFood(registrationId));
        if (this.userFlightService.checkIfAllPassengersOfFlightHaveStatus(
                        responseDto.getFlightId(),
                        UserFlightStatus.CUSTOMER_FOOD_DISTRIBUTED)) {
            this.flightService.informThatAllCustomersAreBriefed(responseDto.getFlightId());
        }
        return ResponseEntity.ok(responseDto);  //UserFlightRegistrationResponseDto
    }

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

    @PreAuthorize(value = "hasAnyRole('CHIEF')")
    @GetMapping(value = "/customers/all")
    public ResponseEntity<?> getCustomersRegistrations(
            @RequestParam Long flightId,
            @RequestParam(required = false) UserFlightStatus customerStatus
    )
            throws UserFlightNotFoundException
    {
        return ResponseEntity.ok(this.userFlightService.getAllCustomerBookings(flightId, customerStatus));  //List<UserFlightRegistrationResponseDto>
    }

    @PreAuthorize(value = "hasAnyRole('CHIEF')")
    @GetMapping(value = "/employees/all")
    public ResponseEntity<?> getEmployeesRegistrationsForFlights(
            @RequestParam(required = false) Long flightId,
            @RequestParam(required = false) UserFlightStatus status
    )
            throws UserFlightNotFoundException
    {
        return ResponseEntity.ok(this.userFlightService.getAllEmployeesBookings(flightId, status));  //List<UserFlightRegistrationResponseDto>
    }

    @PreAuthorize(value = "hasAnyRole('CHIEF_STEWARD', 'STEWARD')")
    @GetMapping(value = "/customers/currentFlight")
    public ResponseEntity<?> getCustomerRegistrationsForCurrentFlight(
            @RequestParam(required = false) UserFlightStatus customerStatus
    ) throws UserFlightNotFoundException
    {
        return ResponseEntity.ok(this.userFlightService.getAllCustomerBookingsForCurrentFlight(customerStatus)); //List<UserFlightRegistrationResponseDto>
    }

    @PreAuthorize(value = "hasRole('CUSTOMER')")
    @GetMapping(value = "/customers/customerCurrentFlight")
    public ResponseEntity<?> getCustomersCurrentFlight()
            throws UserFlightNotFoundException {
        return ResponseEntity.ok(this.userFlightService.getCurrentFlight());  //UserFlightRegistrationResponseDto
    }

    @PreAuthorize(value = "hasRole('CUSTOMER')")
    @GetMapping(value = "/customers/customerFlightHistory")
    public ResponseEntity<?> getCustomersFlightHistory(
            @RequestParam(required = false) UserFlightStatus status
    ) throws UserFlightNotFoundException
    {
        return ResponseEntity.ok(this.userFlightService.getCustomersFlightBookingHistory(status));  //List<UserFlightRegistrationResponseDto>
    }
}
