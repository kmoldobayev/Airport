package kg.kuban.airport.repository;

import kg.kuban.airport.entity.Aircompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AircompanyRepository extends JpaRepository<Aircompany, Long> {

}
