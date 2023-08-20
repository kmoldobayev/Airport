package kg.kuban.airport.service.impl;

import com.querydsl.core.BooleanBuilder;
import kg.kuban.airport.entity.QSeat;
import kg.kuban.airport.entity.Seat;

import kg.kuban.airport.exception.AirplaneSeatNotFoundException;
import kg.kuban.airport.exception.SeatBookingException;
import kg.kuban.airport.repository.SeatRepository;
import kg.kuban.airport.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;

    @Autowired
    public SeatServiceImpl(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Override
    @Transactional
    public Seat bookingSeat(Long seatId) throws AirplaneSeatNotFoundException, SeatBookingException
    {

        Seat airplaneSeat = this.getSeatById(seatId);
        if (airplaneSeat.getOccupied()) {
            throw new SeatBookingException(
                    String.format("Ошибка! Место с ID [%d] уже забронировано!", seatId)
            );
        }
        airplaneSeat.setOccupied(Boolean.TRUE);

        return this.seatRepository.save(airplaneSeat);
    }

    @Override
    @Transactional
    public Seat cancelBookingSeat(Long seatId) throws AirplaneSeatNotFoundException, SeatBookingException
    {

        Seat airplaneSeat = this.getSeatById(seatId);
        if (!airplaneSeat.getOccupied()) {
            throw new SeatBookingException(
                    String.format("Ошибка! Место с ID [%d] свободно!", seatId)
            );
        }
        airplaneSeat.setOccupied(Boolean.FALSE);

        return this.seatRepository.save(airplaneSeat);
    }

    @Override
    @Transactional
    public List<Seat> generateSeats(Integer numberOfSeats) {
        if (Objects.isNull(numberOfSeats)) {
            throw new IllegalArgumentException("Количество мест не может быть null!");
        }
        if (numberOfSeats < 1) {
            throw new IllegalArgumentException("Количество мест не может быть меньше 1!");
        }

        List<Seat> airplaneSeats = new ArrayList<>();
        for (int i = 1; i <= numberOfSeats; i++) {
            airplaneSeats.add(
                    new Seat()
                            .setSeatNumber(i)
                            .setOccupied(Boolean.FALSE)
            );
        }
        return airplaneSeats;
    }

    @Override
    @Transactional
    public List<Seat> getAllSeats(Long airplaneId, Boolean isOccupied) throws AirplaneSeatNotFoundException {

        if (Objects.isNull(airplaneId)) {
            throw new IllegalArgumentException("ID самолета для поиска мест для бронирования не может быть null!");
        }
        if (airplaneId < 1L) {
            throw new IllegalArgumentException("ID самолета для поиска мест для бронирования не может быть меньше 1!");
        }

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QSeat root = QSeat.seat;

        booleanBuilder.and(root.airplane.id.eq(airplaneId));
        if (Objects.nonNull(isOccupied)) {
            booleanBuilder.and(root.isOccupied.eq(isOccupied));
        }

        Iterable<Seat> airplaneSeatIterable = this.seatRepository.findAll(booleanBuilder.getValue());
        List<Seat> airplaneSeatList =
                StreamSupport
                        .stream(airplaneSeatIterable.spliterator(), false)
                        .collect(Collectors.toList());

        if (airplaneSeatList.isEmpty()) {
            throw new AirplaneSeatNotFoundException("Мест для бронирования по заданным параметрам не найдено");
        }
        return airplaneSeatList;
    }

    @Override
    @Transactional
    public Seat getSeatById(Long seatId) throws AirplaneSeatNotFoundException
    {

        if (Objects.isNull(seatId)) {
            throw new IllegalArgumentException("ID места в самолете не может быть null!");
        }
        if (seatId < 1L) {
            throw new IllegalArgumentException("ID места в самолете не может быть меньше 1!");
        }

        Optional<Seat> aircraftSeatsEntityOptional =
                this.seatRepository.getSeatById(seatId);
        if(aircraftSeatsEntityOptional.isEmpty()) {
            throw new AirplaneSeatNotFoundException(
                    String.format("Места в самолете с ID[%d] не найдено!", seatId)
            );
        }
        return aircraftSeatsEntityOptional.get();
    }

    @Override
    public Integer getNumberFreeSeatsByAirplane(Long airplaneId) {

        if (Objects.isNull(airplaneId)) {
            throw new IllegalArgumentException("ID самолета не может быть null!");
        }
        if (airplaneId < 1L) {
            throw new IllegalArgumentException("ID самолета не может быть меньше 1!");
        }

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QSeat root = QSeat.seat;

        booleanBuilder.and(root.airplane.id.eq(airplaneId));
        booleanBuilder.and(root.isOccupied.eq(Boolean.FALSE));

        Iterable<Seat> aircraftSeatsEntityIterable =
                this.seatRepository.findAll(booleanBuilder.getValue());
        List<Seat> airplaneSeats =
                StreamSupport
                        .stream(aircraftSeatsEntityIterable.spliterator(), false)
                        .collect(Collectors.toList());

        return airplaneSeats.size();
    }
}
