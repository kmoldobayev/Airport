package kg.kuban.airport.service.impl;

import kg.kuban.airport.dto.SeatResponseDto;
import kg.kuban.airport.entity.Seat;
import kg.kuban.airport.service.SeatService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {
    @Override
    @Transactional
    public Seat bookingSeat(Long seatId) {
        return null;
    }

    @Override
    @Transactional
    public Seat cancelSeatReservation(Long seatId) {
        return null;
    }

    @Override
    @Transactional
    public List<Seat> generateSeats(Integer numberOfSeats) {
        return null;
    }

    @Override
    @Transactional
    public List<SeatResponseDto> getAllSeats(Long airplaneId, Boolean isOccupied) {
        return null;
    }

    @Override
    @Transactional
    public Seat getSeatById(Long seatId) {
        return null;
    }

    @Override
    public Integer getNumberFreeSeatsByAirplane(Long airplaneId) {
        return null;
    }
}
