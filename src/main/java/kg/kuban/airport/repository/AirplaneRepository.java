package kg.kuban.airport.repository;

import kg.kuban.airport.entity.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirplaneRepository extends JpaRepository<Airplane, Long>, QuerydslPredicateExecutor<Airplane> {
    Airplane findByBoardNumber(String boardNumber);
    Optional<Airplane> getAirplaneById(Long airplaneId);
}
