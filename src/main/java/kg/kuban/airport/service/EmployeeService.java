package kg.kuban.airport.service;

import kg.kuban.airport.dto.PositionRequestDto;
import kg.kuban.airport.entity.AppUser;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeService {
    List<AppUser> getEmployeeReport(LocalDate startDate,
                                    LocalDate endDate,
                                    String fullName,
                                    PositionRequestDto position,
                                    Boolean isEnabled);
}
