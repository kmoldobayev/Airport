package kg.kuban.airport.mapper;

import kg.kuban.airport.dto.AircompanyResponseDto;
import kg.kuban.airport.dto.AirportResponseDto;
import kg.kuban.airport.dto.AppUserResponseDto;
import kg.kuban.airport.entity.Aircompany;
import kg.kuban.airport.entity.Airport;
import kg.kuban.airport.entity.AppUser;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

public class AppUserMapper {
    public static AppUserResponseDto mapAppUserEntityToDto(AppUser appUser) {
        MapstructProviderMapper mapper = Mappers.getMapper(MapstructProviderMapper.class);
        AppUserResponseDto result = mapper.mapAppUserToDto(appUser);
        return result;
    }

    public static List<AppUserResponseDto> mapAppUserEntityListToDto(List<AppUser> appUserList) {
        //MapstructProviderMapper mapper = Mappers.getMapper(MapstructProviderMapper.class);
        //List<AppUserResponseDto> result = mapper.mapListAppUserToListDto(appUserList);

        List<AppUserResponseDto> result = new ArrayList<>();

        for ( AppUser entity : appUserList) {
            AppUserResponseDto appUserResponseDto = new AppUserResponseDto();
            appUserResponseDto.setId(entity.getId());
            appUserResponseDto.setPosition(PositionMapper.mapPositionEntityToDto(entity.getPosition()));
            appUserResponseDto.setUserLogin(entity.getUserLogin());
            appUserResponseDto.setDateLastLogin(entity.getDateLastLogin());


            result.add(appUserResponseDto);
        }

        return result;
    }
}
