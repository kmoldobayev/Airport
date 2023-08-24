package kg.kuban.airport.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.kuban.airport.dto.EmployeeReportRequestDto;
import kg.kuban.airport.dto.EmployeeReportResponseDto;
import kg.kuban.airport.dto.FlightResponseDto;
import kg.kuban.airport.dto.PositionRequestDto;
import kg.kuban.airport.entity.AppUser;
import kg.kuban.airport.mapper.EmployeeReportMapper;
import kg.kuban.airport.mapper.FlightMapper;
import kg.kuban.airport.repository.FlightRepository;
import kg.kuban.airport.service.AppUserService;
import kg.kuban.airport.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
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
@Tag(
        name = "Контроллер для работников аэропорта",
        description = "Описывает точки доступа по работникам аэропорта"
)
public class EmployeeController {

    private final AppUserService appUserService;
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(AppUserService appUserService, EmployeeService employeeService) {
        this.appUserService = appUserService;
        this.employeeService = employeeService;
    }

    @Operation(
            summary = "Формирование отчета по работникам аэрропорта",
            description = "отчет по работникам аэрропорта по дате регистрации за заданный период и другие фильтры",
            parameters = {
                    @Parameter(name = "startDate", description = "Дата начала периода"),
                    @Parameter(name = "endDate", description = "Дата начала периода"),
                    @Parameter(name = "fullName", description = "Полное имя"),
                    @Parameter(name = "position", description = "DTO должности"),
                    @Parameter(name = "isEnabled", description = "Активные")
            }
    )
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

//    @PreAuthorize(value = "hasAnyRole('MANAGER', 'DISPATCHER')")
//    @GetMapping(value = "/crew-members/free")
//    public List<ApplicationUserResponseDto> getAllFreeCrewMembers()
//            throws ApplicationUserNotFoundException
//    {
//        return this.applicationUserService.getAllFreeCrewMembers();
//    }
//
//    @PreAuthorize(value = "hasAnyRole('MANAGER', 'CHIEF_ENGINEER')")
//    @GetMapping(value = "/engineers/free")
//    public List<ApplicationUserResponseDto> getAllFreeEngineers()
//            throws ApplicationUserNotFoundException
//    {
//        return this.applicationUserService.getAllFreeEngineers();
//    }
//
//    @PreAuthorize(value = "hasAnyRole('MANAGER', 'ADMIN')")
//    @GetMapping(value = "/positions")
//    public List<UserPositionResponseDto> getAllEmployeePositions() {
//        return this.userPositionsService.getAllEmployeePositions();
//    }

}
