package kg.kuban.airport.mapper;

import kg.kuban.airport.dto.AppRoleResponseDto;
import kg.kuban.airport.dto.AppUserResponseDto;
import kg.kuban.airport.entity.AppRole;
import kg.kuban.airport.entity.AppUser;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

public class AppRoleMapper {
    public static AppRoleResponseDto mapAppUserEntityToDto(AppRole appRole) {
        MapstructProviderMapper mapper = Mappers.getMapper(MapstructProviderMapper.class);
        AppRoleResponseDto result = mapper.mapAppRoleToDto(appRole);
        return result;
    }

    public static List<AppRoleResponseDto> mapAppRoleEntityListToDto(List<AppRole> appRoleList) {
        //MapstructProviderMapper mapper = Mappers.getMapper(MapstructProviderMapper.class);
        //List<AppUserResponseDto> result = mapper.mapListAppUserToListDto(appUserList);

        List<AppRoleResponseDto> result = new ArrayList<>();

        for ( AppRole entity : appRoleList) {
            AppRoleResponseDto appRoleResponseDto = new AppRoleResponseDto();
            appRoleResponseDto.setId(entity.getId());
            appRoleResponseDto.setTitle(entity.getTitle());

            appRoleResponseDto.setPosition(PositionMapper.mapPositionEntityToDto(entity.getPosition()));


            result.add(appRoleResponseDto);
        }

        return result;
    }
}
