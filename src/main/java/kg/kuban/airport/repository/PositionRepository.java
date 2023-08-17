package kg.kuban.airport.repository;

import kg.kuban.airport.entity.AppRole;
import kg.kuban.airport.entity.Flight;
import kg.kuban.airport.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    Position findByTitle(String title);
}
