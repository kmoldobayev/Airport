package kg.kuban.airport.mapper;

import kg.kuban.airport.dto.PartInspectionsRequestDto;
import kg.kuban.airport.dto.PartInspectionsResponseDto;
import kg.kuban.airport.dto.PartStatesResponseDto;
import kg.kuban.airport.entity.PartInspection;
import kg.kuban.airport.enums.AirplanePartStatus;

import java.util.List;
import java.util.stream.Collectors;

public class InspectionMapper {
    public static PartInspection mapPartInspectionsRequestDtoToEntity(PartInspectionsRequestDto source) {
        return new PartInspection().setStatus(source.getPartState());
    }

    public static PartInspectionsResponseDto mapToPartInspectionsResponseDto(PartInspection source) {
        return new PartInspectionsResponseDto()
                .setId(source.getId())
                .setPartId(source.getPart().getId())
                .setPartTitle(source.getPart().getTitle())
                .setPartState(source.getStatus())
                .setPartType(source.getPart().getPartType())
                .setAirplaneId(source.getAirplane().getId())
                .setAirplaneTitle(source.getAirplane().getBoardNumber())
                .setInspectionCode(source.getInspectionCode())
                .setDateRegister(source.getDateRegister());
    }

    public static List<PartInspectionsResponseDto> mapToPartInspectionsResponseDtoList(
            List<PartInspection> sourceList
    ) {
        return sourceList
                .stream()
                .map(InspectionMapper::mapToPartInspectionsResponseDto)
                .collect(Collectors.toList());
    }

    public static PartStatesResponseDto mapToPartStatesResponseDto(List<AirplanePartStatus> partStates) {
        return new PartStatesResponseDto().setPartStates(partStates);
    }
}
