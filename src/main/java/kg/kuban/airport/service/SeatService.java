package kg.kuban.airport.service;

import kg.kuban.airport.entity.Seat;
import kg.kuban.airport.exception.AirplaneSeatNotFoundException;
import kg.kuban.airport.exception.SeatBookingException;

import java.util.List;

public interface SeatService {
    Seat bookingSeat(Long seatId) throws AirplaneSeatNotFoundException, SeatBookingException;

    Seat cancelBookingSeat(Long seatId) throws AirplaneSeatNotFoundException, SeatBookingException;

    List<Seat> generateSeats(Integer numberOfSeats);

    List<Seat> getAllSeats(Long airplaneId, Boolean isOccupied) throws AirplaneSeatNotFoundException;

    Seat getSeatById(Long seatId) throws AirplaneSeatNotFoundException;

    Integer getNumberFreeSeatsByAirplane(Long airplaneId);
}
