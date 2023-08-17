package kg.kuban.airport.repository;

import kg.kuban.airport.entity.AirplanePart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartRepository extends JpaRepository<AirplanePart, Long> {
}
