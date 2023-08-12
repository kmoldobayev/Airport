package kg.kuban.airport.mapper;

import kg.kuban.airport.dto.FlightResponseDto;
import kg.kuban.airport.entity.Flight;

import java.util.ArrayList;
import java.util.List;

public class FlightMapper {
    public static FlightResponseDto mapFlightEntityToDto(Flight flight) {
        FlightResponseDto flightResponseDto = new FlightResponseDto();
        flightResponseDto.setId(flight.getId());
        flightResponseDto.setAirplane(AirplaneMapper.mapAirplaneEntityToDto(flight.getAirplane()));
        flightResponseDto.setDateRegister(flight.getDateRegister());
        flightResponseDto.setSource(AirportMapper.mapAirportEntityToDto(flight.getSource()));
        flightResponseDto.setDestination(AirportMapper.mapAirportEntityToDto(flight.getDestination()));
        return flightResponseDto;
    }

    public static List<FlightResponseDto> mapGroupServiceEntityListToDto(List<Flight> flightList) {
        List<FlightResponseDto> result = new ArrayList<>();

        for ( Flight flight : flightList) {
            FlightResponseDto flightResponseDto = new FlightResponseDto();
            flightResponseDto.setId(flight.getId());
            flightResponseDto.setAirplane(AirplaneMapper.mapAirplaneEntityToDto(flight.getAirplane()));
            flightResponseDto.setDateRegister(flight.getDateRegister());
            flightResponseDto.setSource(AirportMapper.mapAirportEntityToDto(flight.getSource()));
            flightResponseDto.setDestination(AirportMapper.mapAirportEntityToDto(flight.getDestination()));
            result.add(flightResponseDto);
        }

        return result;
    }
}
