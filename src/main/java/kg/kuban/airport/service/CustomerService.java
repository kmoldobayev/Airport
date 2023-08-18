package kg.kuban.airport.service;

import kg.kuban.airport.dto.*;
import kg.kuban.airport.entity.AppUser;
import kg.kuban.airport.entity.CustomerReview;
import kg.kuban.airport.entity.Flight;
import kg.kuban.airport.entity.UserFlight;
import kg.kuban.airport.exception.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CustomerService {
    AppUser createCustomer(CustomerRequestDto user) throws IllegalArgumentException;
//    List<UserFlight> getMyPastFlights(Long customerId) throws IllegalArgumentException;
//    List<UserFlight> getAvailableFlights(Long customerId) throws IllegalArgumentException;
//    UserFlight getCurrentFlight(Long customerId) throws IllegalArgumentException;

    List<AppUser> getAllClients(
            LocalDate dateRegisterBegin,
            LocalDate dateRegisterEnd,
            Boolean isDeleted
    ) throws AppUserNotFoundException;

    CustomerReview registerNewReview(CustomerReviewRequestDto requestDto) throws UserFlightNotFoundException,
            FlightNotFoundException;
    List<CustomerReviewResponseDto> getAllCustomersReviews(
            LocalDateTime dateRegisterBegin,
            LocalDateTime dateRegisterEnd,
            Long flightId
    ) throws IncorrectFiltersException, CustomerReviewNotFoundException;
}
