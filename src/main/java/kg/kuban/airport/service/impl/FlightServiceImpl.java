package kg.kuban.airport.service.impl;

import kg.kuban.airport.dto.FlightRequestDto;
import kg.kuban.airport.entity.Airplane;
import kg.kuban.airport.entity.Airport;
import kg.kuban.airport.entity.Flight;
import kg.kuban.airport.entity.Position;
import kg.kuban.airport.repository.AirplaneRepository;
import kg.kuban.airport.repository.AirportRepository;
import kg.kuban.airport.service.FlightService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FlightServiceImpl implements FlightService {

    private AirplaneRepository airplaneRepository;
    private AirportRepository airportRepository;


    @Override
    public Flight registerFlight(FlightRequestDto flightRequestDto) {

        Airplane existingAirplane = this.airplaneRepository.findByBoardNumber(flightRequestDto.getAirplane().getBoardNumber());
        Airport existingAirportDestination = this.airportRepository.findByTitle(flightRequestDto.getDestination().getTitle());

        Flight flight = new Flight();

        flight.setFlightNumber(flightRequestDto.getNumber());
        flight.setAirplane(existingAirplane);
        flight.setAvailable(false);
        flight.setDestination(existingAirportDestination);
        flight.setDateRegister(LocalDateTime.now());


        return flight;
    }
}
