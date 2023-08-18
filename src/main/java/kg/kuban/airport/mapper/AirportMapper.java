package kg.kuban.airport.mapper;

import kg.kuban.airport.dto.AirportRequestDto;
import kg.kuban.airport.dto.AirportResponseDto;
import kg.kuban.airport.dto.FlightResponseDto;
import kg.kuban.airport.entity.Airport;
import kg.kuban.airport.entity.Flight;

import java.util.ArrayList;
import java.util.List;

public class AirportMapper {
    public static AirportResponseDto mapAirportEntityToDto(Airport airport) {
        AirportResponseDto airportResponseDto = new AirportResponseDto();
        airportResponseDto.setId(airport.getId());
        airportResponseDto.setCity(airport.getCity());
        airportResponseDto.setTitle(airport.getCity());
        return airportResponseDto;
    }

    public static Airport mapAirportDtoToEntity(AirportRequestDto airportRequestDto) {
        Airport airport = new Airport();
        airport.setId(airport.getId());
        airport.setCity(airport.getCity());
        airport.setTitle(airport.getCity());
        return airport;
    }

    public static List<AirportResponseDto> mapAirportEntityListToDto(List<Airport> airportList) {
        List<AirportResponseDto> result = new ArrayList<>();

        for ( Airport airport : airportList) {
            AirportResponseDto airportResponseDto = new AirportResponseDto();
            airportResponseDto.setId(airport.getId());
            airportResponseDto.setId(airport.getId());
            airportResponseDto.setCity(airport.getCity());
            airportResponseDto.setTitle(airport.getCity());
            result.add(airportResponseDto);
        }

        return result;
    }
}
