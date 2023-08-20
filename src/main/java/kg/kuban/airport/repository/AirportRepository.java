package kg.kuban.airport.repository;

import kg.kuban.airport.entity.Airplane;
import kg.kuban.airport.entity.Airport;
import kg.kuban.airport.entity.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long>, QuerydslPredicateExecutor<Airport> {
    Airport findByTitle(String title);
}
