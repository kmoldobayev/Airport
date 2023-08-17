package kg.kuban.airport.service;

import kg.kuban.airport.dto.FlightRequestDto;
import kg.kuban.airport.entity.Flight;

public interface FlightService {
    Flight registerFlight(FlightRequestDto flightRequestDto);
}
