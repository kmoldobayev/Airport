package kg.kuban.airport.controller.v1;

import kg.kuban.airport.dto.UserFlightRegistrationResponseDto;
import kg.kuban.airport.dto.UserFlightRequestDto;
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

@RestController
@RequestMapping(value = "/flights/registrations")
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

//    @PreAuthorize(value = "hasRole('DISPATCHER')")
//    @PostMapping(value = "/crew/register")
//    public ResponseEntity<?> registerCrewMembersForFlight(
//            @RequestBody List<UserFlightRequestDto> requestDtoList
//    )
//            throws InvalidUserRoleException,
//            IllegalFlightException,
//            NotEnoughRolesForCrewRegistrationException,
//            AppUserNotFoundException,
//            FlightNotFoundException,
//            NotRegisteredFlightException,
//            NotEnoughRolesForCrewRegistrationException,
//            InvalidUserRoleException
//
//    {
//        List<UserFlight> responseDtoList = this.userFlightService.registerEmployeesForFlight(requestDtoList);
//        return ResponseEntity.ok(FlightMapper.mapToUserFlightRegistrationResponseDto(responseDtoList));
//    }

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

//    @PreAuthorize(value = "hasRole('STEWARD')")
//    @PutMapping(value = "/Customers/check-Customer")
//    public ResponseEntity<?> checkCustomer(
//            @RequestParam Long registrationId
//    )
//            throws UserFlightNotFoundException,
//            StatusChangeException,
//            FlightNotFoundException
//    {
//        UserFlightRegistrationResponseDto responseDto = UserFlightMapper.mapToUserFlightRegistrationResponseDto(this.userFlightService.checkCustomer(registrationId));
//        if (
//                this.userFlightService.checkIfAllPassengersOfFlightHaveStatus(
//                        responseDto.getFlightId(),
//                        UserFlightStatus.CUSTOMER_CHECKED
//                )
//        ) {
//            this.flightService.informThatAllClientsAreBriefed(responseDto.getFlightId());
//        }
//        return ResponseEntity.ok(UserFlightMapper.mapToUserFlightRegistrationResponseDto(responseDto)); //UserFlightRegistrationResponseDto
//    }

    @PreAuthorize(value = "hasRole('STEWARD')")
    @PutMapping(value = "/customers/distributeCustomersFood")
    public UserFlightRegistrationResponseDto distributeCustomersFood(
            @RequestParam Long registrationId
    )
            throws UserFlightNotFoundException,
            StatusChangeException, FlightNotFoundException {
        UserFlightRegistrationResponseDto responseDto = UserFlightMapper.mapToUserFlightRegistrationResponseDto(this.userFlightService.distributeCustomersFood(registrationId));
        if(
                this.userFlightService.checkIfAllPassengersOfFlightHaveStatus(
                        responseDto.getFlightId(),
                        UserFlightStatus.CUSTOMER_FOOD_DISTRIBUTED
                )
        ) {
            this.flightService.informThatAllCustomersAreBriefed(responseDto.getFlightId());
        }
        return responseDto;
    }

    @PreAuthorize(value = "hasAnyRole('STEWARD', 'CHIEF_STEWARD', 'PILOT')")
    @PutMapping(value = "/crew-members/confirm-readiness")
    public UserFlightRegistrationResponseDto confirmReadiness()
            throws UserFlightNotFoundException,
            StatusChangeException,
            FlightNotFoundException
    {
        UserFlightRegistrationResponseDto responseDto = UserFlightMapper.mapToUserFlightRegistrationResponseDto(this.userFlightService.confirmReadinessForFlight());
        if(this.userFlightService.checkIfAllCrewMembersIsReadyForFlight(responseDto.getFlightId())) {
            this.flightService.informThatAllCrewMembersIsReadyForFlight(responseDto.getFlightId());
        }
        return responseDto;
    }

//    @PreAuthorize(value = "hasAnyRole('CHIEF')")
//    @GetMapping(value = "/Customers/all")
//    public List<UserFlightRegistrationResponseDto> getCustomersRegistrations(
//            @RequestParam Long flightId,
//            @RequestParam(required = false) UserFlightStatus Customerstatus
//    )
//            throws UserFlightNotFoundException
//    {
//        return this.userFlightService.getAllCustomerRegistrations(flightId, Customerstatus);
//    }

//    @GetMapping(value = "/employees/all")
//    public List<UserFlightRegistrationResponseDto> getEmployeesRegistrationsForFlights(
//            @RequestParam(required = false) Long flightId,
//            @RequestParam(required = false) UserFlightStatus status
//    )
//            throws UserFlightNotFoundException
//    {
//        return this.userFlightService.getAllEmployeesRegistrations(flightId, status);
//    }

//    @PreAuthorize(value = "hasAnyRole('CHIEF_STEWARD', 'STEWARD')")
//    @GetMapping(value = "/Customers/current-flight")
//    public List<UserFlightRegistrationResponseDto> getCustomerRegistrationsForCurrentFlight(
//            @RequestParam(required = false) UserFlightStatus Customerstatus
//    )
//            throws UserFlightNotFoundException
//    {
//        return this.userFlightService.getAllCustomerRegistrationsForCurrentFLight(Customerstatus);
//    }

//    @PreAuthorize(value = "hasRole('CUSTOMER')")
//    @GetMapping(value = "/Customers/Customer-current-flight")
//    public UserFlightRegistrationResponseDto getCustomersCurrentFlight()
//            throws UserFlightNotFoundException {
//        return this.userFlightService.getCurrentFlight();
//    }

//    @PreAuthorize(value = "hasRole('CUSTOMER')")
//    @GetMapping(value = "/Customers/Customer-flight-history")
//    public List<UserFlightRegistrationResponseDto> getCustomersFlightHistory(
//            @RequestParam(required = false) UserFlightStatus status
//    )
//            throws UserFlightNotFoundException
//    {
//        return this.userFlightService.getCustomersFlightRegistrationHistory(status);
//    }
}
