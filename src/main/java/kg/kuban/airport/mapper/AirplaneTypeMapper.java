package kg.kuban.airport.mapper;

import kg.kuban.airport.dto.AirplaneTypesResponseDto;
import kg.kuban.airport.dto.AppRoleResponseDto;
import kg.kuban.airport.entity.AppRole;
import kg.kuban.airport.enums.AirplaneType;
import org.mapstruct.factory.Mappers;

import java.util.List;

public class AirplaneTypeMapper {
    public static AirplaneTypesResponseDto mapAirplaneTypeToDto(AirplaneType airplaneType) {
        MapstructProviderMapper mapper = Mappers.getMapper(MapstructProviderMapper.class);
        AirplaneTypesResponseDto result = mapper.mapAirplaneTypeToDto(airplaneType);
        return result;
    }

    public static List<AirplaneTypesResponseDto> mapAirplaneTypeListToDto(List<AirplaneType> airplaneTypes) {
        MapstructProviderMapper mapper = Mappers.getMapper(MapstructProviderMapper.class);
        List<AirplaneTypesResponseDto> result = mapper.mapListAirplaneTypeToListDto(airplaneTypes);

        return result;
    }
}
