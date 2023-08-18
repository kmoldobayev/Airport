package kg.kuban.airport.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.kuban.airport.dto.*;
import kg.kuban.airport.entity.Flight;
import kg.kuban.airport.exception.*;
import kg.kuban.airport.mapper.*;
import kg.kuban.airport.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/customers")
@Tag(
        name = "Контроллер для клиентов аэропорта",
        description = "Описывает точки доступа по клиентам аэропорта"
)
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(
            summary = "Метод регистрации клиента аэропорта",
            description = "Регистрация нового клиента выполняется с ролью Клиент",
            parameters = {
                    @Parameter(name = "CustomerRequestDto", description = "DTO клиента")
            }
    )
    @PostMapping(value = "/register")
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    public ResponseEntity<?> registerCustomer(
            @RequestBody CustomerRequestDto user
    ) throws InvalidCredentialsException {
        return  ResponseEntity.ok(CustomerMapper.mapAppUserEntityToDto(this.customerService.createCustomer(user)));
    }

//    @Operation(
//            summary = "Метод просмотра доступных рейсов",
//            description = "Регистрация нового клиента выполняется с ролью Клиент",
//            parameters = {
//                    @Parameter(name = "CustomerRequestDto", description = "DTO клиента")
//            }
//    )
//    @GetMapping(value = "/customer{id}/pastFlights")
//    @PreAuthorize("hasAnyRole('CUSTOMER')")
//    public ResponseEntity<?> getMyPastFlights(@PathVariable(value = "id") Long customerId) throws InvalidCredentialsException {
//        return ResponseEntity.ok(UserFlightMapper.mapFlightEntityListToDto(this.customerService.getMyPastFlights(customerId)));
//    }

//    @Operation(
//            summary = "Метод просмотра доступных рейсов",
//            description = "Просмотр доступных рейсов выполняется с ролью Клиент",
//            parameters = {
//                    @Parameter(name = "customerId", description = "ID клиента")
//            }
//    )
//    @ApiResponse(description = "DTO рейсов")
//    @GetMapping(value = "/availableFlights")
//    @PreAuthorize("hasAnyRole('CUSTOMER')")
//    public ResponseEntity<?> getAvailableFlights(@PathVariable(value = "id") Long customerId) throws InvalidCredentialsException {
//        return  ResponseEntity.ok(FlightMapper.mapFlightEntityListToDto(this.customerService.getAvailableFlights(customerId)));
//    }

//    @Operation(
//            summary = "Метод просмотр текущего рейса",
//            description = "Просмотр текущего рейса с ролью Клиент",
//            parameters = {
//                    @Parameter(name = "customerId", description = "ID клиента")
//            }
//    )
//    @GetMapping(value = "/customer{id}/currentFlight")
//    @PreAuthorize("hasAnyRole('CUSTOMER')")
//    public ResponseEntity<?> getCurrentFlight(@PathVariable(value = "id") Long customerId) throws InvalidCredentialsException {
//        return  ResponseEntity.ok(UserFlightMapper.mapToUserFlightRegistrationResponseDto(this.customerService.getCurrentFlight(customerId)));
//    }

    @Operation(
            summary = "Формирование отчета по поиску клиентов по дате регистрации и статусу аккаунта. Роль : CHIEF",
            description = "Поиск клиентов по дате регистрации (date_register) и" +
                    " статусу аккаунта (isDeleted -- удален или нет)"
    )
    @PreAuthorize(value = "hasAnyRole('CHIEF')")
    @GetMapping(value = "/report")
    public ResponseEntity<?> getAllClients(
            @RequestParam(required = false) LocalDate registeredBefore,
            @RequestParam(required = false) LocalDate registeredAfter,
            @RequestParam(required = false) Boolean isDeleted
    )
            throws AppUserNotFoundException
    {
        return ResponseEntity.ok(AppUserMapper.mapAppUserEntityListToDto(this.customerService.getAllClients(registeredBefore, registeredAfter, isDeleted)));
    }

    @PreAuthorize(value = "hasRole('CUSTOMER')")
    @PostMapping(value = "/registerReview")
    public ResponseEntity<?> registerNewReview(@RequestBody CustomerReviewRequestDto requestDto)
            throws InvalidReviewException,
            UserFlightNotFoundException,
            FlightNotFoundException
    {
        return ResponseEntity.ok(CustomerReviewMapper.mapCustomerReviewToDto(this.customerService.registerNewReview(requestDto)));
    }

    @PreAuthorize(value = "hasAnyRole('MANAGER', 'PILOT')")
    @GetMapping(value = "/allReviews")
    public List<CustomerReviewResponseDto> getAllReviews(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam(required = false) LocalDateTime dateBegin,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam(required = false) LocalDateTime dateEnd,
            @RequestParam(required = false) Long flightId
    )
            throws CustomerReviewNotFoundException,
            IncorrectFiltersException
    {
        return this.customerService.getAllCustomersReviews(dateBegin, dateEnd, flightId);
    }

}
