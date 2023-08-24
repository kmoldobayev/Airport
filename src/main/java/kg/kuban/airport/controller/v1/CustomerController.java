package kg.kuban.airport.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Operation(
            summary = "Формирование отчета по поиску клиентов по дате регистрации и статусу аккаунта. Роль : CHIEF",
            description = "Поиск клиентов по дате регистрации (date_register) и" +
                    " статусу аккаунта (isDeleted -- удален или нет)"
    )
    @PreAuthorize(value = "hasAnyRole('CHIEF')")
    @GetMapping(value = "/report")
    public ResponseEntity<?> getAllCustomers(
            @RequestParam(required = false) LocalDate registeredBefore,
            @RequestParam(required = false) LocalDate registeredAfter,
            @RequestParam(required = false) Boolean isDeleted
    )
            throws AppUserNotFoundException
    {
        return ResponseEntity.ok(AppUserMapper.mapAppUserEntityListToDto(this.customerService.getAllCustomers(registeredBefore, registeredAfter, isDeleted)));
    }

    @Operation(
            summary = "Регистрация нового отзыва клиента по рейсу",
            description = "Пользователь с ролью Клиент выполняет Регистрацию нового отзыва",
            parameters = {
                    @Parameter(name = "requestDto", description = "DTO отзыва", schema = @Schema(type = "CustomerReviewRequestDtoLong"), required = true)
            }
    )
    @PreAuthorize(value = "hasRole('CUSTOMER')")
    @PostMapping(value = "/registerReview")
    public ResponseEntity<?> registerNewReview(@RequestBody CustomerReviewRequestDto requestDto)
            throws InvalidReviewException,
            UserFlightNotFoundException,
            FlightNotFoundException
    {
        return ResponseEntity.ok(CustomerReviewMapper.mapCustomerReviewToDto(this.customerService.registerNewReview(requestDto)));
    }

    @Operation(
            summary = "Просмотр отзывов клиентов по рейсу",
            description = "Пользователь с ролью (Пилот, Управляющий директорв) выполняет Просмотр отзывов клиентов по рейсу",
            parameters = {
                    @Parameter(name = "dateBegin",
                                description = "Дата начала регистрации",
                                schema = @Schema(type = "LocalDateTime"),
                                required = false),
                    @Parameter(name = "dateEnd",
                            description = "Дата окончания регистрации",
                            schema = @Schema(type = "LocalDateTime"),
                            required = false),
                    @Parameter(name = "flightId",
                            description = "ID рейса",
                            schema = @Schema(type = "Long"),
                            required = false)
            }
    )
    @PreAuthorize(value = "hasAnyRole('CHIEF', 'PILOT')")
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
