package kg.kuban.airport.service;

import kg.kuban.airport.dto.AirportRequestDto;
import kg.kuban.airport.dto.FlightRequestDto;
import kg.kuban.airport.dto.FlightResponseDto;
import kg.kuban.airport.entity.Flight;
import kg.kuban.airport.enums.FlightStatus;
import kg.kuban.airport.exception.AirplaneNotReadyException;
import kg.kuban.airport.exception.FlightNotFoundException;
import kg.kuban.airport.exception.IncorrectFiltersException;
import kg.kuban.airport.exception.StatusChangeException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightService {
    Flight registerNewFlight(FlightRequestDto flightRequestDto);

    Flight updateNumberOfRemainingTickets(Long flightId) throws FlightNotFoundException;

    void informThatAllCrewMembersIsReadyForFlight(Long flightId) throws FlightNotFoundException, StatusChangeException;

    void informThatAllClientsAreChecked(Long flightId) throws FlightNotFoundException, StatusChangeException;

    void informThatAllClientsAreBriefed(Long flightId) throws FlightNotFoundException, StatusChangeException;

    void informThatAllClientsFoodIsDistributed(Long flightId) throws FlightNotFoundException, StatusChangeException;

    Flight confirmFlightRegistration(Long flightId) throws FlightNotFoundException, StatusChangeException;

    Flight initiateFlightDeparturePreparations(Long flightId) throws FlightNotFoundException, StatusChangeException;

    Flight initiateCrewPreparation(Long flightId) throws FlightNotFoundException, StatusChangeException;

    Flight confirmAircraftRefueling(Long flightId) throws FlightNotFoundException, StatusChangeException, AirplaneNotReadyException;

    Flight assignBriefing(Long flightId) throws FlightNotFoundException, StatusChangeException;

    Flight confirmClientReadiness(Long flightId) throws FlightNotFoundException, StatusChangeException;

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

    List<Flight> getAllFLights(
            LocalDateTime registeredAfter,
            LocalDateTime registeredBefore,
            FlightStatus flightStatus
    ) throws IncorrectFiltersException, FlightNotFoundException;

    List<FlightResponseDto> getFlightsForTicketReservation(
            LocalDateTime createdAfter,
            LocalDateTime createdBefore,
            AirportRequestDto flightDestination
    ) throws FlightNotFoundException, IncorrectFiltersException;

    Flight getFlightEntityByFlightId(Long flightId) throws FlightNotFoundException;
}
