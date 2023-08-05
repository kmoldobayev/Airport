package kg.kuban.airport.repository;

import kg.kuban.airport.entity.TechMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechMaintenanceRepository extends JpaRepository<TechMaintenance, Long> {
}
