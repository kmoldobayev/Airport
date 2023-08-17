package kg.kuban.airport.mapper;

import kg.kuban.airport.dto.CustomerResponseDto;
import kg.kuban.airport.dto.EmployeeReportResponseDto;
import kg.kuban.airport.dto.PositionRequestDto;
import kg.kuban.airport.entity.AppUser;
import kg.kuban.airport.entity.Position;
import kg.kuban.airport.repository.PositionRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmployeeReportMapper {

    private static PositionRepository positionRepository;

    public static EmployeeReportResponseDto mapReportFilterToDto(
                                                                    LocalDate startDate,
                                                                    LocalDate endDate,
                                                                    String fullName,
                                                                    PositionRequestDto position,
                                                                    Integer countEnabledUsers,
                                                                    Integer countDismissedUsers,
                                                                    Integer countAll,
                                                                    List<AppUser> appUserAll,
                                                                    List<AppUser> appUserEnabled,
                                                                    List<AppUser> appUserDismissed
                                                                    ) {

        Position existingPosition = positionRepository.findByTitle(position.getTitle());

        EmployeeReportResponseDto employeeReportResponseDto = new EmployeeReportResponseDto();
        employeeReportResponseDto.setFullName(fullName);
        employeeReportResponseDto.setPosition(PositionMapper.mapPositionEntityToDto(existingPosition));
        employeeReportResponseDto.setCountAll(countAll);
        employeeReportResponseDto.setCountEnabledUsers(countEnabledUsers);
        employeeReportResponseDto.setCountDismissedUsers(countDismissedUsers);
        employeeReportResponseDto.setAppUserAll(appUserAll);
        employeeReportResponseDto.setAppUserEnabled(appUserEnabled);
        employeeReportResponseDto.setAppUserDismissed(appUserDismissed);


        return employeeReportResponseDto;
    }


}
