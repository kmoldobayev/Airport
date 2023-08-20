package kg.kuban.airport.service;

import kg.kuban.airport.dto.UserFlightRequestDto;
import kg.kuban.airport.entity.UserFlight;
import kg.kuban.airport.enums.UserFlightsStatus;
import kg.kuban.airport.exception.*;

import java.util.List;

public interface UserFlightsService {
    List<UserFlight> registerEmployeesForFlight(List<UserFlightRequestDto> requestDtoList)
            throws FlightNotFoundException,
            NotRegisteredFlightException,
            AppUserNotFoundException,
            NotEnoughRolesForCrewRegistrationException,
            InvalidUserRoleException;

    UserFlight registerCustomerForFlight(UserFlightRequestDto requestDto)
            throws FlightNotFoundException,
            NotRegisteredFlightException,
            AirplaneSeatNotFoundException,
            SeatBookingException;

    UserFlight cancelClientRegistration(Long registrationId) throws
            UserFlightNotFoundException,
            TicketCancelingException,
            AirplaneSeatNotFoundException,
            SeatBookingException,
            FlightNotFoundException;

    UserFlight checkClient(Long clientRegistrationId) throws UserFlightNotFoundException, StatusChangeException;

    UserFlight distributeClientsFood(Long clientRegistrationId) throws UserFlightNotFoundException, StatusChangeException;

    UserFlight conductClientsBriefing(Long clientRegistrationId) throws UserFlightNotFoundException, StatusChangeException;

    UserFlight confirmReadinessForFlight() throws UserFlightNotFoundException, StatusChangeException;

    List<UserFlight> getAllUserRegistrations(
            Long flightId,
            UserFlightsStatus status,
            Long userId,
            Boolean isClient
    ) throws UserFlightNotFoundException;

    List<UserFlight> getAllClientRegistrations(Long flightId, UserFlightsStatus status) throws UserFlightNotFoundException;

    List<UserFlight> getAllEmployeesRegistrations(Long flightId, UserFlightsStatus status) throws UserFlightNotFoundException;

    List<UserFlight> getAllCustomerRegistrationsForCurrentFLight(UserFlightsStatus status) throws UserFlightNotFoundException;

    List<UserFlight> getCustomersFlightRegistrationHistory(UserFlightsStatus status) throws UserFlightNotFoundException;

    UserFlight getCurrentFlight() throws UserFlightNotFoundException;

    UserFlight getCustomerFlightRegistrationByCustomerIdAndUserFlightId(Long registrationId, Long userId) throws UserFlightNotFoundException;

    UserFlight getCustomerFlightRegistrationById(Long registrationId) throws UserFlightNotFoundException;

    UserFlight getUserFlightRegistrationByUserId(Long userId) throws UserFlightNotFoundException;

    boolean checkIfAllPassengersOfFlightHaveStatus(Long flightId, UserFlightsStatus status);

    boolean checkIfAllCrewMembersIsReadyForFlight(Long flightId);
}
