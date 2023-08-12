package kg.kuban.airport.service;

import kg.kuban.airport.dto.AppUserRequestDto;
import kg.kuban.airport.dto.CustomerRequestDto;
import kg.kuban.airport.dto.FlightResponseDto;
import kg.kuban.airport.entity.AppUser;
import kg.kuban.airport.entity.Flight;

import java.util.List;

public interface CustomerService {
    AppUser createCustomer(CustomerRequestDto user) throws IllegalArgumentException;
    List<Flight> getMyPastFlights(Long customerId) throws IllegalArgumentException;
    List<Flight> getAvailableFlights(Long customerId) throws IllegalArgumentException;
    Flight getCurrentFlight(Long customerId) throws IllegalArgumentException;
}
