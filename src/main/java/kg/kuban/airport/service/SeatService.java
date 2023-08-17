package kg.kuban.airport.service;

import kg.kuban.airport.dto.SeatResponseDto;
import kg.kuban.airport.entity.Seat;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SeatService {
    Seat bookingSeat(Long seatId);

    Seat cancelSeatReservation(Long seatId);

    List<Seat> generateSeats(Integer numberOfSeats);

    List<SeatResponseDto> getAllSeats(Long airplaneId, Boolean isOccupied);

    Seat getSeatById(Long seatId);

    Integer getNumberFreeSeatsByAirplane(Long airplaneId);
}
