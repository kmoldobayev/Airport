package kg.kuban.airport.repository;

import kg.kuban.airport.dto.FlightResponseDto;
import kg.kuban.airport.entity.Flight;
import kg.kuban.airport.entity.UserFlight;
import kg.kuban.airport.enums.FlightStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long>, QuerydslPredicateExecutor<Flight> {

    @Query(value = "SELECT t FROM public.flights t WHERE t.is_available = true;", nativeQuery = true)
    List<Flight> findAvailableFlights();

    List<Flight> getFlightsByStatus(FlightStatus status);

    Optional<Flight> getFlightById(Long flightId);
}
