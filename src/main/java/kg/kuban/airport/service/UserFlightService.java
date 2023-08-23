package kg.kuban.airport.service;

import kg.kuban.airport.dto.UserFlightRequestDto;
import kg.kuban.airport.entity.UserFlight;
import kg.kuban.airport.enums.UserFlightStatus;
import kg.kuban.airport.exception.*;

import java.util.List;

public interface UserFlightService {
    List<UserFlight> registerEmployeesForFlight(List<UserFlightRequestDto> requestDtoList)
            throws FlightNotFoundException,
            IllegalFlightException,
            NotRegisteredFlightException,
            AppUserNotFoundException,
            NotEnoughRolesForCrewRegistrationException,
            InvalidUserRoleException;

    UserFlight bookingCustomerForFlight(UserFlightRequestDto requestDto)
            throws FlightNotFoundException,
            NotRegisteredFlightException,
            IllegalFlightException,
            AirplaneSeatNotFoundException,
            SeatBookingException;

    UserFlight cancelCustomerBooking(Long bookingId) throws
            UserFlightNotFoundException,
            TicketCancelingException,
            AirplaneSeatNotFoundException,
            SeatBookingException,
            FlightNotFoundException;

    UserFlight checkCustomer(Long customerBookingId) throws UserFlightNotFoundException, StatusChangeException;

    UserFlight distributeCustomersFood(Long customerBookingId) throws UserFlightNotFoundException, StatusChangeException;

    UserFlight conductCustomersBriefing(Long customerBookingId) throws UserFlightNotFoundException, StatusChangeException;

    UserFlight confirmReadinessForFlight() throws UserFlightNotFoundException, StatusChangeException;

    List<UserFlight> getAllUserRegistrations(
            Long flightId,
            UserFlightStatus status,
            Long userId,
            Boolean isCustomer
    ) throws UserFlightNotFoundException;

    List<UserFlight> getAllCustomerBookings(Long flightId, UserFlightStatus status) throws UserFlightNotFoundException;

    List<UserFlight> getAllEmployeesBookings(Long flightId, UserFlightStatus status) throws UserFlightNotFoundException;

    List<UserFlight> getAllCustomerBookingsForCurrentFlight(UserFlightStatus status) throws UserFlightNotFoundException;

    List<UserFlight> getCustomersFlightBookingHistory(UserFlightStatus status) throws UserFlightNotFoundException;

    UserFlight getCurrentFlight() throws UserFlightNotFoundException;

    UserFlight getCustomerFlightBookingByCustomerIdAndUserFlightId(Long registrationId, Long userId) throws UserFlightNotFoundException;

    UserFlight getCustomerFlightBookingById(Long registrationId) throws UserFlightNotFoundException;

    UserFlight getUserFlightRegistrationByUserId(Long userId) throws UserFlightNotFoundException;

    boolean checkIfAllPassengersOfFlightHaveStatus(Long flightId, UserFlightStatus status);

    boolean checkIfAllCrewMembersIsReadyForFlight(Long flightId);

}
