package kg.kuban.airport.repository;

import kg.kuban.airport.entity.PartInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartInspectionRepository extends JpaRepository<PartInspection, Long> {
}
