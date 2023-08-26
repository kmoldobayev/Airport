package kg.kuban.airport.mapper;

import kg.kuban.airport.dto.FlightRequestDto;
import kg.kuban.airport.dto.FlightResponseDto;
import kg.kuban.airport.dto.UserFlightRegistrationResponseDto;
import kg.kuban.airport.entity.Flight;
import kg.kuban.airport.entity.UserFlight;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FlightMapper {

    public static Flight mapFlightRequestDtoToEntity(FlightRequestDto source) {
        return new Flight()
                .setDestination(AirportMapper.mapAirportDtoToEntity(source.getDestination()))
                .setFlightNumber(source.getFlightNumber());
    }

    public static FlightResponseDto mapFlightEntityToDto(Flight flight) {
        FlightResponseDto flightResponseDto = new FlightResponseDto();
        flightResponseDto.setId(flight.getId());
        flightResponseDto.setFlightNumber(flight.getFlightNumber());
        flightResponseDto.setDateRegister(flight.getDateRegister());
        flightResponseDto.setAirplane(AirplaneMapper.mapAirplaneEntityToDto(flight.getAirplane()));
        flightResponseDto.setDateRegister(flight.getDateRegister());
        flightResponseDto.setDestination(AirportMapper.mapAirportEntityToDto(flight.getDestination()));
        return flightResponseDto;
    }

    public static List<FlightResponseDto> mapFlightEntityListToDto(List<Flight> flightList) {
        List<FlightResponseDto> result = new ArrayList<>();

        for ( Flight flight : flightList) {
            FlightResponseDto flightResponseDto = new FlightResponseDto();
            flightResponseDto.setId(flight.getId());
            flightResponseDto.setFlightNumber(flight.getFlightNumber());
            flightResponseDto.setDateRegister(flight.getDateRegister());
            flightResponseDto.setAirplane(AirplaneMapper.mapAirplaneEntityToDto(flight.getAirplane()));
            flightResponseDto.setDateRegister(flight.getDateRegister());
            flightResponseDto.setDestination(AirportMapper.mapAirportEntityToDto(flight.getDestination()));
            result.add(flightResponseDto);
        }

        return result;
    }

    public static UserFlightRegistrationResponseDto mapToUserFlightRegistrationResponseDto(UserFlight source) {
        UserFlightRegistrationResponseDto responseDto =
                new UserFlightRegistrationResponseDto()
                        .setId(source.getId())
                        .setFlightId(source.getFlight().getId())
                        .setFlightDestination(AirportMapper.mapAirportEntityToDto(source.getFlight().getDestination()))
                        .setEmployeeId(source.getId())
                        .setEmployeeFullName(source.getAppUser().getFullName())
                        .setEmployeePositionTitle(
                                source.getAppUser() != null && source.getAppUser().getPosition() != null ? source.getAppUser().getPosition().getTitle() : null
                        )
                        .setRegisteredAt(source.getDateRegister())
                        .setUserStatus(source.getStatus());

        if (Objects.nonNull(source.getSeat())) {
            responseDto
                    .setSeatNumber(source.getSeat().getSeatNumber());
        }

        return responseDto;
    }
}
