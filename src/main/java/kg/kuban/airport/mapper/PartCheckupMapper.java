package kg.kuban.airport.mapper;

import kg.kuban.airport.dto.AirplanePartCheckupRequestDto;
import kg.kuban.airport.dto.AirplanePartCheckupResponseDto;
import kg.kuban.airport.dto.AirplanePartStatusResponseDto;
import kg.kuban.airport.entity.AirplanePartCheckup;
import kg.kuban.airport.enums.AirplanePartStatus;

import java.util.List;
import java.util.stream.Collectors;

public class PartCheckupMapper {
    public static AirplanePartCheckup mapAirplanePartCheckupRequestDtoToEntity(AirplanePartCheckupRequestDto source) {
        return new AirplanePartCheckup().setStatus(source.getPartState());
    }

    public static AirplanePartCheckupResponseDto mapToPartCheckupResponseDto(AirplanePartCheckup source) {
        return new AirplanePartCheckupResponseDto()
                .setId(source.getId())
                .setPartId(source.getPart().getId())
                .setPartTitle(source.getPart().getTitle())
                .setPartState(source.getStatus())
                .setPartType(source.getPart().getPartType())
                .setAirplaneId(source.getAirplane().getId())
                .setAirplaneTitle(source.getAirplane().getBoardNumber())
                .setCheckupCode(source.getCheckupCode())
                .setDateRegister(source.getDateRegister());
    }

    public static List<AirplanePartCheckupResponseDto> mapToPartCheckupResponseDtoList(
            List<AirplanePartCheckup> sourceList
    ) {
        return sourceList
                .stream()
                .map(PartCheckupMapper::mapToPartCheckupResponseDto)
                .collect(Collectors.toList());
    }

    public static AirplanePartStatusResponseDto mapToPartStatesResponseDto(List<AirplanePartStatus> partStatusList) {
        return new AirplanePartStatusResponseDto().setPartStatusList(partStatusList);
    }
}
