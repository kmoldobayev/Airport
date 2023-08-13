package kg.kuban.airport.controller.v1;

import kg.kuban.airport.dto.EmployeeReportRequestDto;
import kg.kuban.airport.dto.EmployeeReportResponseDto;
import kg.kuban.airport.dto.FlightResponseDto;
import kg.kuban.airport.dto.PositionRequestDto;
import kg.kuban.airport.mapper.EmployeeReportMapper;
import kg.kuban.airport.mapper.FlightMapper;
import kg.kuban.airport.repository.FlightRepository;
import kg.kuban.airport.service.EmployeeService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/employee")
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
    public EmployeeReportResponseDto getEmployeeReport(
            @RequestParam(name = "startDate", required = false)
            @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
            LocalDateTime startDate,
            @RequestParam(name = "endDate", required = false)
            @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
            LocalDateTime endDate,
            @RequestParam(name = "fullName", required = false) String fullName,
            @RequestParam(name = "position", required = false) PositionRequestDto position
    ) {
//        List<EmployeeReportResponseDto> employeeAll =
//                EmployeeReportMapper.mapReportFilterToDto(this.employeeService.servicePayments.getPaymentHistoryReport(startDate, endDate, serviceId, providerId, credentials, null));
//        List<EmployeeReportResponseDto> employeeEnabled =
//                PaymentHistoryMapper.mapPaymentHistoryEntityListToDto(this.servicePayments.getPaymentHistoryReport(startDate, endDate, serviceId, providerId, credentials, PaymentStatus.ACCEPTED));
//        List<EmployeeReportResponseDto> employeeDismissed =
//                PaymentHistoryMapper.mapPaymentHistoryEntityListToDto(this.servicePayments.getPaymentHistoryReport(startDate, endDate, serviceId, providerId, credentials, PaymentStatus.DECLINED));
//
//
//        Integer countAll = paymentHistoryEntities.size();
//        Integer countEnabledUsers = paymentHistoryAcceptedEntities.size();
//        Integer countDissmissedUsers = paymentHistoryDeclinedEntities.size();
//
//        return PaymentHistoryReportMapper.mapPaymentHistoryFiltersToDto(startDate,
//                endDate,
//                serviceId,
//                providerId,
//                credentials,
//                countAccepted,
//                countDeclined,
//                countAll,
//                sumAccepted,
//                sumDeclined,
//                sumAll,
//                paymentHistoryEntities
        return null;
    }
}
