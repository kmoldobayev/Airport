package kg.kuban.airport.repository;

import kg.kuban.airport.entity.Airplane;
import kg.kuban.airport.entity.AirplanePart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirplanePartRepository extends JpaRepository<AirplanePart, Long>, QuerydslPredicateExecutor<AirplanePart> {
    List<AirplanePart> getPartsByIdIn(List<Long> partsIdList);

    List<AirplanePart> getPartsByIdInAndAirplanesContains(
            List<Long> partsIdList,
            Airplane airplane
    );
}
