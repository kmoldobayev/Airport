package kg.kuban.airport.repository;

import kg.kuban.airport.entity.Aircompany;
import kg.kuban.airport.entity.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AircompanyRepository extends JpaRepository<Aircompany, Long>, QuerydslPredicateExecutor<Aircompany> {
    Optional<Aircompany> findAircompanyByTitle(String title);
}
