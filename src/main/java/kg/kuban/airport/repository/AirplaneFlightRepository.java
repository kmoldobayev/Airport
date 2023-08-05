package kg.kuban.airport.repository;

import kg.kuban.airport.entity.AirplaneFlight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirplaneFlightRepository extends JpaRepository<AirplaneFlight, Long> {
}
