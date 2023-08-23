package kg.kuban.airport.mapper;

import kg.kuban.airport.dto.AircompanyResponseDto;
import kg.kuban.airport.dto.AirplaneResponseDto;
import kg.kuban.airport.dto.AirplaneSeatResponseDto;
import kg.kuban.airport.dto.AirplaneTypesResponseDto;
import kg.kuban.airport.entity.Aircompany;
import kg.kuban.airport.entity.Airplane;
import kg.kuban.airport.entity.Seat;
import kg.kuban.airport.enums.AirplaneType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AirplaneMapper {
    public static AirplaneResponseDto mapAirplaneEntityToDto(Airplane airplane) {
        AirplaneResponseDto airplaneResponseDto = new AirplaneResponseDto();
        airplaneResponseDto.setId(airplane.getId());
        airplaneResponseDto.setBoardNumber(airplane.getBoardNumber());
        airplaneResponseDto.setMarka(airplane.getMarka());
        airplaneResponseDto.setNumberSeats(airplane.getNumberSeats());
        airplaneResponseDto.setDateRegister(airplane.getDateRegister());


        return airplaneResponseDto;
    }

    public static List<AirplaneResponseDto> mapAirplaneEntityListToDto(List<Airplane> airplaneList) {
        List<AirplaneResponseDto> result = new ArrayList<>();

        for ( Airplane airplane : airplaneList) {
            AirplaneResponseDto airplaneResponseDto = new AirplaneResponseDto();
            airplaneResponseDto.setId(airplane.getId());
            airplaneResponseDto.setBoardNumber(airplane.getBoardNumber());
            airplaneResponseDto.setDateRegister(airplane.getDateRegister());
            result.add(airplaneResponseDto);
        }

        return result;
    }

    public static AirplaneSeatResponseDto mapToAirplaneSeatResponseDto(Seat source) {
        return new AirplaneSeatResponseDto()
                .setId(source.getId())
                .setSeatNumber(source.getSeatNumber())
                .setOccupied(source.getOccupied());
    }

    public static List<AirplaneSeatResponseDto> mapToAirplaneSeatResponseDtoList(List<Seat> sourceList) {
        return sourceList
                .stream()
                .map(AirplaneMapper::mapToAirplaneSeatResponseDto)
                .collect(Collectors.toList());
    }

    public static AirplaneTypesResponseDto mapToAirplaneTypesResponseDto(List<AirplaneType> airplaneTypes) {
        return new AirplaneTypesResponseDto().setAirplaneTypes(airplaneTypes);
    }
}
