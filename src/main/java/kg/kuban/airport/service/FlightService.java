package kg.kuban.airport.service;

import kg.kuban.airport.dto.AirportRequestDto;
import kg.kuban.airport.dto.FlightRequestDto;
import kg.kuban.airport.dto.FlightResponseDto;
import kg.kuban.airport.entity.Flight;
import kg.kuban.airport.enums.FlightStatus;
import kg.kuban.airport.exception.*;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightService {
    Flight registerNewFlight(FlightRequestDto flightRequestDto) throws AirplaneNotFoundException, UnavailableAirplaneException;

    Flight updateNumberOfRemainingTickets(Long flightId) throws FlightNotFoundException;

    void informThatAllCrewMembersIsReadyForFlight(Long flightId) throws FlightNotFoundException, StatusChangeException;

    void informThatAllCustomersAreChecked(Long flightId) throws FlightNotFoundException, StatusChangeException;

    void informThatAllCustomersAreBriefed(Long flightId) throws FlightNotFoundException, StatusChangeException;

    void informThatAllCustomersFoodIsDistributed(Long flightId) throws FlightNotFoundException, StatusChangeException;

    Flight confirmFlightRegistration(Long flightId) throws FlightNotFoundException, StatusChangeException;

    Flight initiateFlightDeparturePreparations(Long flightId) throws FlightNotFoundException, StatusChangeException;

    Flight initiateCrewPreparation(Long flightId) throws FlightNotFoundException, StatusChangeException;

    Flight confirmAirplaneRefueling(Long flightId) throws FlightNotFoundException, StatusChangeException, AirplaneNotReadyException;

    Flight assignBriefing(Long flightId) throws FlightNotFoundException, StatusChangeException;

    Flight confirmCustomerReadiness(Long flightId) throws FlightNotFoundException, StatusChangeException;

    Flight initiateDeparture(Long flightId) throws FlightNotFoundException, StatusChangeException;

    Flight confirmDeparture(Long flightId) throws FlightNotFoundException, StatusChangeException;

    Flight startFlight(Long flightId) throws FlightNotFoundException, StatusChangeException;

    Flight assignFoodDistribution(Long flightId) throws FlightNotFoundException, StatusChangeException;

    Flight requestLanding(Long flightId) throws FlightNotFoundException, StatusChangeException;

    Flight assignLanding(Long flightId)
            throws FlightNotFoundException,
            StatusChangeException;

    Flight confirmLanding(Long flightId)
            throws FlightNotFoundException,
            StatusChangeException;

    Flight endFlight(Long flightId)
            throws FlightNotFoundException,
            StatusChangeException;

    List<Flight> getAvailableFlights(
            LocalDateTime dateRegisterBeg,
            LocalDateTime dateRegisterEnd,
            FlightStatus flightStatus
    ) throws IncorrectFiltersException, FlightNotFoundException;

    List<Flight> getFlightsForTicketBooking(
            LocalDateTime dateCreateBeg,
            LocalDateTime dateCreateEnd,
            AirportRequestDto flightDestination
    ) throws FlightNotFoundException, IncorrectFiltersException;

    Flight getFlightEntityByFlightId(Long flightId) throws FlightNotFoundException;
}
