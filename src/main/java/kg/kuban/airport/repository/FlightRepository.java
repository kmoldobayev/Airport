package kg.kuban.airport.repository;

import kg.kuban.airport.dto.FlightResponseDto;
import kg.kuban.airport.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query(value = "SELECT t FROM public.flights t WHERE t.is_available = true;", nativeQuery = true)
    List<Flight> findAvailableFlights();
}
