package kg.kuban.airport.service.impl;

import kg.kuban.airport.controller.v1.AppUserController;
import kg.kuban.airport.dto.AppUserRequestDto;
import kg.kuban.airport.dto.CustomerRequestDto;
import kg.kuban.airport.dto.FlightResponseDto;
import kg.kuban.airport.entity.AppRole;
import kg.kuban.airport.entity.AppUser;
import kg.kuban.airport.entity.Flight;
import kg.kuban.airport.repository.AppUserRepository;
import kg.kuban.airport.repository.FlightRepository;
import kg.kuban.airport.repository.UserFlightRepository;
import kg.kuban.airport.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class CustomerServiceImpl implements CustomerService {

    private AppUserRepository appUserRepository;
    private FlightRepository flightRepository;
    private UserFlightRepository userFlightRepository;

    private PasswordEncoder bCryptPasswordEncoder;
    private final Logger logger = LoggerFactory.getLogger(AppUserController.class);

    @Override
    public AppUser createCustomer(CustomerRequestDto appUserDto) throws IllegalArgumentException {
        AppUser possibleDuplicate = appUserRepository.findAll().stream()
                .filter(x -> x.getUserLogin().equals(appUserDto.getUserLogin()))
                .findFirst()
                .orElse(null);
        logger.info("possibleDuplicate");
        if (Objects.isNull(possibleDuplicate)){
            AppUser appUser = new AppUser();
            appUser.setAppRoles(Collections.singleton(new AppRole(1L, "ROLE_CUSTOMER")));

            appUser.setUserLogin(appUserDto.getUserLogin());
            appUser.setUserPassword(bCryptPasswordEncoder.encode(appUserDto.getUserPassword()));
            appUserRepository.save(appUser);
            logger.info("appUserRepository.save(appUser)");
            return appUser;
        } else {
            throw new IllegalArgumentException("Пользователь с таким логином " + appUserDto.getUserLogin() + " уже есть!");
        }
    }

    @Override
    public List<Flight> getMyPastFlights(Long customerId) throws IllegalArgumentException {
        return null;
    }

    @Override
    public List<Flight> getAvailableFlights(Long customerId) throws IllegalArgumentException {
        return this.flightRepository.findAvailableFlights();
    }

    @Override
    public Flight getCurrentFlight(Long customerId) throws IllegalArgumentException {
        return null;
    }
}
