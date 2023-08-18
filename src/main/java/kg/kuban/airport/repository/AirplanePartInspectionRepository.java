package kg.kuban.airport.repository;

import kg.kuban.airport.entity.AirplanePartInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirplanePartInspectionRepository extends JpaRepository<AirplanePartInspection, Long>, QuerydslPredicateExecutor<AirplanePartInspection> {
    @Query(value = "SELECT MAX(inspection_code) FROM part_inspections", nativeQuery = true)
    Long getCurrentMaxInspectionCode();

    @Query(
            value = "with airplane_inspections as (select * from part_inspections where airplane_id = :airplaneId)\n" +
                    "select * " +
                    "from airplane_inspections" +
                    " where inspection_code = (select max(inspection_code) from airplane_inspections);",
            nativeQuery = true
    )
    List<AirplanePartInspection> getLastAirplaneInspectionByAirplaneId(
            @Param(value = "airplaneId") Long airplaneId
    );
}
