package kg.kuban.airport.repository;

import kg.kuban.airport.entity.Airport;
import kg.kuban.airport.entity.UserFlight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserFlightRepository extends JpaRepository<UserFlight, Long>, QuerydslPredicateExecutor<UserFlight> {

    Optional<UserFlight> getUserFlightById(Long userFlightId);

    Optional<UserFlight> getUserFlightByAppUserId(Long userId);
}
