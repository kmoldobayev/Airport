package kg.kuban.airport.mapper;

import kg.kuban.airport.dto.AircompanyResponseDto;
import kg.kuban.airport.dto.AirplaneResponseDto;
import kg.kuban.airport.entity.Aircompany;
import kg.kuban.airport.entity.Airplane;

import java.util.ArrayList;
import java.util.List;

public class AirplaneMapper {
    public static AirplaneResponseDto mapAirplaneEntityToDto(Airplane airplane) {
        AirplaneResponseDto airplaneResponseDto = new AirplaneResponseDto();
        airplaneResponseDto.setId(airplane.getId());
        airplaneResponseDto.setBoardNumber(airplane.getBoardNumber());
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
}
