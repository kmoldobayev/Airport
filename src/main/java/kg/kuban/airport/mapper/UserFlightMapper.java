package kg.kuban.airport.mapper;

import kg.kuban.airport.dto.UserFlightRegistrationResponseDto;
import kg.kuban.airport.entity.UserFlight;

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
}
