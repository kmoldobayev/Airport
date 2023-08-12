package kg.kuban.airport.repository;

import kg.kuban.airport.entity.UserFlight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFlightRepository extends JpaRepository<UserFlight, Long> {
}
