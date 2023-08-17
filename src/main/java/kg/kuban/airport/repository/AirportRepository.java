package kg.kuban.airport.repository;

import kg.kuban.airport.entity.Airport;
import kg.kuban.airport.entity.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {
    Airport findByTitle(String title);
}
