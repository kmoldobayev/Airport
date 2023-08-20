package kg.kuban.airport.repository;

import kg.kuban.airport.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long>, QuerydslPredicateExecutor<Seat> {
    Optional<Seat> getSeatById(Long seatId);

    void deleteSeatsByAirplane_Id(Long airplaneId);
}
