package kg.kuban.airport.mapper;

import kg.kuban.airport.dto.*;
import kg.kuban.airport.entity.*;
import org.mapstruct.*;
import org.mapstruct.Mapper;

import java.util.List;

//@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@Mapper
public interface MapstructProviderMapper {

    /**
     * Метод предназначенный для маппинга Flight в FlightResponseDto
     * @param flight Сущность Flight для маппинга в DTO
     * @return новый эксземпляр FlightResponseDto полученный из Flight
     */

    //@Mapping(target = "ss", source = "provider.some")
    FlightResponseDto mapFlightToDto(Flight flight);

    /**
     * Метод предназначенный для маппинга {@link = Aircompany} в {@link = AircompanyResponseDto}
     * @param aircompany Сущность Aircompany для маппинга в DTO
     * @return новый эксземпляр AircompanyResponseDto полученный из Aircompany
     */
    AircompanyResponseDto mapAircompanyToDto(Aircompany aircompany);

    AppUserResponseDto mapAppUserToDto(AppUser appUser);
    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    List<AppUserResponseDto> mapListAppUserToListDto(List<AppUser> appUser);

    AppRoleResponseDto mapAppRoleToDto(AppRole appRole);
    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    List<AppRoleResponseDto> mapListAppRoleToListDto(List<AppRole> appRoles);

    PositionResponseDto mapPositionToDto(Position position);
    Position mapPositionDtoToEntity(PositionRequestDto positionDto);
    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    List<PositionResponseDto> mapListPositionToListDto(List<Position> positions);

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    List<FlightResponseDto> mapListFlightToListDto(List<Flight> flights);

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    List<AircompanyResponseDto> mapListAircompanyToListDto(List<Aircompany> aircompanies);
}
