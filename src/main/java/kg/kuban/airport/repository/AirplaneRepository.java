package kg.kuban.airport.repository;

import kg.kuban.airport.entity.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirplaneRepository extends JpaRepository<Airplane, Long> {
    Airplane findByBoardNumber(String boardNumber);
}
