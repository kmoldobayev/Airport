package kg.kuban.airport.repository;

import kg.kuban.airport.entity.UserFlight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFlightRepository extends JpaRepository<UserFlight, Long> {
}
