package kg.kuban.airport.mapper;

import kg.kuban.airport.dto.AirplanePartRequestDto;
import kg.kuban.airport.dto.AirplanePartResponseDto;
import kg.kuban.airport.dto.AirplanePartTypesResponseDto;
import kg.kuban.airport.entity.AirplanePart;
import kg.kuban.airport.enums.AirplanePartType;

import java.util.List;
import java.util.stream.Collectors;

public class AirplanePartMapper {
    public static AirplanePart mapAirplanePartRequestDtoToEntity(AirplanePartRequestDto source) {
        return new AirplanePart()
                .setTitle(source.getTitle())
                .setAirplaneType(source.getAirplaneType())
                .setPartType(source.getAirplanePartType());
    }

    public static AirplanePartResponseDto mapToAirplanePartResponseDto(AirplanePart source) {
        return new AirplanePartResponseDto()
                .setId(source.getId())
                .setTitle(source.getTitle())
                .setAirplaneType(source.getAirplaneType())
                .setAirplanePartType(source.getPartType())
                .setDateRegister(source.getDateRegister());
    }

    public static List<AirplanePartResponseDto> mapToAirplanePartResponseDtoList(List<AirplanePart> sourceList) {
        return sourceList
                .stream()
                .map(AirplanePartMapper::mapToAirplanePartResponseDto)
                .collect(Collectors.toList());
    }

    public static AirplanePartTypesResponseDto mapToAirplanePartTypesResponseDto(List<AirplanePartType> airplanePartTypes) {
        return new AirplanePartTypesResponseDto().setPartTypeList(airplanePartTypes);
    }
}
