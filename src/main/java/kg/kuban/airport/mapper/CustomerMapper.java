package kg.kuban.airport.mapper;

import kg.kuban.airport.dto.AppUserResponseDto;
import kg.kuban.airport.dto.CustomerResponseDto;
import kg.kuban.airport.entity.AppUser;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

public class CustomerMapper {
    public static CustomerResponseDto mapAppUserEntityToDto(AppUser appUser) {
        MapstructProviderMapper mapper = Mappers.getMapper(MapstructProviderMapper.class);
        CustomerResponseDto result = mapper.mapCustomerToDto(appUser);
        return result;
    }

    public static List<CustomerResponseDto> mapAppUserEntityListToDto(List<AppUser> appUserList) {
        List<CustomerResponseDto> result = new ArrayList<>();

        for ( AppUser entity : appUserList) {
            CustomerResponseDto customerResponseDto = new CustomerResponseDto();
            customerResponseDto.setId(entity.getId());
            customerResponseDto.setFullName(entity.getFullName());
            customerResponseDto.setUserLogin(entity.getUserLogin());

            result.add(customerResponseDto);
        }

        return result;
    }
}
