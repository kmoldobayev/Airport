package kg.kuban.airport.repository;

import kg.kuban.airport.entity.TechInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechInspectionRepository extends JpaRepository<TechInspection, Long> {
}
