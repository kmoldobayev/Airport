package kg.kuban.airport.mapper;

import kg.kuban.airport.dto.FlightResponseDto;
import kg.kuban.airport.dto.UserFlightRegistrationResponseDto;
import kg.kuban.airport.entity.Flight;
import kg.kuban.airport.entity.UserFlight;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserFlightMapper {
    public static UserFlightRegistrationResponseDto mapToUserFlightRegistrationResponseDto(
            UserFlight source
    ) {
        UserFlightRegistrationResponseDto responseDto =
                new UserFlightRegistrationResponseDto()
                        .setId(source.getId())
                        .setFlightId(source.getFlight().getId())
                        .setFlightDestination(AirportMapper.mapAirportEntityToDto(source.getFlight().getDestination()))
                        .setEmployeeId(source.getId())
                        .setEmployeeFullName(source.getAppUser().getFullName())
                        .setEmployeePositionTitle(
                                source.getAppUser().getPosition().getTitle()
                        )
                        .setRegisteredAt(source.getDateRegister())
                        .setUserStatus(source.getStatus());

        if (Objects.nonNull(source.getSeat())) {
            responseDto
                    .setSeatNumber(source.getSeat().getSeatNumber());
        }

        return responseDto;
    }

    public static List<UserFlightRegistrationResponseDto> mapUserFlightEntityListToDto(List<UserFlight> userFlightList) {
        List<UserFlightRegistrationResponseDto> result = new ArrayList<>();

        for ( UserFlight userFlight : userFlightList) {
            UserFlightRegistrationResponseDto userFlightRegistrationResponseDto = new UserFlightRegistrationResponseDto();
            userFlightRegistrationResponseDto.setId(userFlight.getId());
            userFlightRegistrationResponseDto.setFlightId(userFlight.getFlight().getId());
            userFlightRegistrationResponseDto.setSeatNumber(userFlight.getSeat().getSeatNumber());
            //userFlightRegistrationResponseDto.setFlightDestination(userFlight..getDestination()));
            userFlightRegistrationResponseDto.setUserStatus(userFlight.getStatus());
            userFlightRegistrationResponseDto.setRegisteredAt(userFlight.getDateRegister());
            result.add(userFlightRegistrationResponseDto);
        }

        return result;
    }
}
