package kg.kuban.airport.controller.v1;

import kg.kuban.airport.dto.EmployeeReportRequestDto;
import kg.kuban.airport.dto.EmployeeReportResponseDto;
import kg.kuban.airport.dto.FlightResponseDto;
import kg.kuban.airport.dto.PositionRequestDto;
import kg.kuban.airport.entity.AppUser;
import kg.kuban.airport.mapper.EmployeeReportMapper;
import kg.kuban.airport.mapper.FlightMapper;
import kg.kuban.airport.repository.FlightRepository;
import kg.kuban.airport.service.EmployeeService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private FlightRepository flightRepository;
    private EmployeeService employeeService;
    /*
        Главный диспетчер:
    1.	Просмотр доступных рейсов.
    2.	Подтверждение нового рейса.
    3.	Просмотр всех зарегистрированных самолетов.
    4.	Подтверждение регистрации нового самолета.
    5.	Подтверждение отправки рейса.
    6.	Подтверждение принятия рейса.
    */

    @GetMapping("/available-flights")
    public List<FlightResponseDto> getAvailableFlights() {
        return FlightMapper.mapFlightEntityListToDto(this.flightRepository.findAvailableFlights());
    }

    @GetMapping(value = "/report")
    @PreAuthorize("hasAnyRole('CHIEF')")
    public EmployeeReportResponseDto getEmployeeReport(
            @RequestParam(name = "startDate", required = false)
            @DateTimeFormat(pattern = "dd.MM.yyyy")
            LocalDate startDate,
            @RequestParam(name = "endDate", required = false)
            @DateTimeFormat(pattern = "dd.MM.yyyy")
            LocalDate endDate,
            @RequestParam(name = "fullName", required = false) String fullName,
            @RequestParam(name = "position", required = false) PositionRequestDto position,
            @RequestParam(name = "isEnabled", required = false) Boolean isEnabled
    ) {
        List<AppUser> employeeAll = new ArrayList<>();
        List<AppUser> employeeEnabled = new ArrayList<>();
        List<AppUser> employeeDismissed = new ArrayList<>();
        if (Objects.isNull(isEnabled)) {
            employeeAll = this.employeeService.getEmployeeReport(
                                    startDate,
                                    endDate,
                                    fullName,
                                    position,
                                    isEnabled);
        }
        if (Objects.nonNull(isEnabled) && (isEnabled == true)) {
            employeeEnabled = this.employeeService.getEmployeeReport(
                                    startDate,
                                    endDate,
                                    fullName,
                                    position,
                                    isEnabled);
        }
        if (Objects.nonNull(isEnabled) && (isEnabled == false)) {
            employeeDismissed = this.employeeService.getEmployeeReport(
                                    startDate,
                                    endDate,
                                    fullName,
                                    position,
                                    isEnabled);
        }

        Integer countAll = employeeAll.size();
        Integer countEnabledUsers = employeeEnabled.size();
        Integer countDismissedUsers = employeeDismissed.size();

        return EmployeeReportMapper.mapReportFilterToDto(   startDate,
                                                            endDate,
                                                            fullName,
                                                            position,
                                                            countAll,
                                                            countEnabledUsers,
                                                            countDismissedUsers,
                                                            employeeAll,
                                                            employeeEnabled,
                                                            employeeDismissed
        );
    }


}
