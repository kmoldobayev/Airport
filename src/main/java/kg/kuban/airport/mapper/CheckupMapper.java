package kg.kuban.airport.mapper;

import kg.kuban.airport.dto.AirplanePartCheckupRequestDto;
import kg.kuban.airport.dto.AirplanePartCheckupResponseDto;
import kg.kuban.airport.dto.PartStatesResponseDto;
import kg.kuban.airport.entity.AirplanePartCheckup;
import kg.kuban.airport.enums.AirplanePartStatus;

import java.util.List;
import java.util.stream.Collectors;

public class CheckupMapper {
    public static AirplanePartCheckup mapPartInspectionsRequestDtoToEntity(AirplanePartCheckupRequestDto source) {
        return new AirplanePartCheckup().setStatus(source.getPartStatus());
    }

    public static AirplanePartCheckupResponseDto mapToPartInspectionsResponseDto(AirplanePartCheckup source) {
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

    public static List<AirplanePartCheckupResponseDto> mapToPartInspectionsResponseDtoList(
            List<AirplanePartCheckup> sourceList
    ) {
        return sourceList
                .stream()
                .map(CheckupMapper::mapToPartInspectionsResponseDto)
                .collect(Collectors.toList());
    }

    public static PartStatesResponseDto mapToPartStatesResponseDto(List<AirplanePartStatus> partStates) {
        return new PartStatesResponseDto().setPartStates(partStates);
    }
}
