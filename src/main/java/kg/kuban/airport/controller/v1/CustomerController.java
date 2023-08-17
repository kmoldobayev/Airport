package kg.kuban.airport.controller.v1;

import io.swagger.v3.oas.annotations.tags.Tag;
import kg.kuban.airport.dto.AppUserRequestDto;
import kg.kuban.airport.dto.CustomerRequestDto;
import kg.kuban.airport.entity.Flight;
import kg.kuban.airport.exception.InvalidCredentialsException;
import kg.kuban.airport.mapper.AppUserMapper;
import kg.kuban.airport.mapper.CustomerMapper;
import kg.kuban.airport.mapper.FlightMapper;
import kg.kuban.airport.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/register")
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    public ResponseEntity<?> registerCustomer(
            @RequestBody CustomerRequestDto user
    ) throws InvalidCredentialsException {
        return  ResponseEntity.ok(CustomerMapper.mapAppUserEntityToDto(this.customerService.createCustomer(user)));
    }

    @GetMapping(value = "/customer{id}/pastFlights")
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    public ResponseEntity<?> getMyPastFlights(@PathVariable(value = "id") Long customerId) throws InvalidCredentialsException {
        return  ResponseEntity.ok(FlightMapper.mapFlightEntityListToDto(this.customerService.getMyPastFlights(customerId)));
    }

    @GetMapping(value = "/availableFlights")
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    public ResponseEntity<?> getAvailableFlights(@PathVariable(value = "id") Long customerId) throws InvalidCredentialsException {
        return  ResponseEntity.ok(FlightMapper.mapFlightEntityListToDto(this.customerService.getAvailableFlights(customerId)));
    }

    @GetMapping(value = "/customer{id}/currentFlight")
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    public ResponseEntity<?> getCurrentFlight(@PathVariable(value = "id") Long customerId) throws InvalidCredentialsException {
        return  ResponseEntity.ok(FlightMapper.mapFlightEntityToDto(this.customerService.getCurrentFlight(customerId)));
    }

}
