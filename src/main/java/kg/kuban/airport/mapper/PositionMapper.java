package kg.kuban.airport.mapper;

import kg.kuban.airport.dto.AppUserResponseDto;
import kg.kuban.airport.dto.PositionResponseDto;
import kg.kuban.airport.entity.AppUser;
import kg.kuban.airport.entity.Position;
import org.mapstruct.factory.Mappers;

import java.util.List;

public class PositionMapper {
    public static PositionResponseDto mapPositionEntityToDto(Position position) {
        MapstructProviderMapper mapper = Mappers.getMapper(MapstructProviderMapper.class);
        PositionResponseDto result = mapper.mapPositionEntityToDto(position);
        return result;
    }

    public static Position mapPositionDtoToEntity(PositionResponseDto positionDto) {
        MapstructProviderMapper mapper = Mappers.getMapper(MapstructProviderMapper.class);
        Position result = mapper.mapPositionDtoToEntity(positionDto);
        return result;
    }

    public static List<PositionResponseDto> mapAppUserEntityListToDto(List<Position> positions) {
        MapstructProviderMapper mapper = Mappers.getMapper(MapstructProviderMapper.class);
        List<PositionResponseDto> result = mapper.mapListPositionToListDto(positions);

        return result;
    }
}
