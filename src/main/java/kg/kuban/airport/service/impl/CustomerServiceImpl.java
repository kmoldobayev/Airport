package kg.kuban.airport.service.impl;

import com.querydsl.core.BooleanBuilder;
import kg.kuban.airport.controller.v1.AppUserController;
import kg.kuban.airport.dto.CustomerRequestDto;
import kg.kuban.airport.dto.CustomerReviewRequestDto;
import kg.kuban.airport.dto.CustomerReviewResponseDto;
import kg.kuban.airport.entity.*;
import kg.kuban.airport.enums.UserFlightStatus;
import kg.kuban.airport.exception.*;
import kg.kuban.airport.mapper.CustomerReviewMapper;
import kg.kuban.airport.repository.*;
import kg.kuban.airport.service.AppUserService;
import kg.kuban.airport.service.CustomerService;
import kg.kuban.airport.service.FlightService;
import kg.kuban.airport.service.UserFlightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CustomerServiceImpl implements CustomerService {

    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PositionRepository positionRepository;
    private AppUserService appUserService;
    private FlightService flightService;
    private UserFlightService userFlightsService;
    private FlightRepository flightRepository;
    private UserFlightRepository userFlightRepository;
    private CustomerReviewRepository customerReviewRepository;

    private PasswordEncoder bCryptPasswordEncoder;
    private final Logger logger = LoggerFactory.getLogger(AppUserController.class);

    @Autowired
    public CustomerServiceImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository, PositionRepository positionRepository, AppUserService appUserService, FlightService flightService, UserFlightService userFlightsService, FlightRepository flightRepository, UserFlightRepository userFlightRepository, CustomerReviewRepository customerReviewRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        this.positionRepository = positionRepository;
        this.appUserService = appUserService;
        this.flightService = flightService;
        this.userFlightsService = userFlightsService;
        this.flightRepository = flightRepository;
        this.userFlightRepository = userFlightRepository;
        this.customerReviewRepository = customerReviewRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    @Override
    public AppUser createCustomer(CustomerRequestDto appUserDto)
            throws IllegalArgumentException, InvalidCredentialsException {
        if (Objects.isNull(appUserDto)) {
            throw new IllegalArgumentException("Входящий userDto is null");
        }

        if (Objects.isNull(appUserDto.getUserLogin())) {
            throw new InvalidCredentialsException("Login must not be null or empty");
        }
        if (Objects.isNull(appUserDto.getUserPassword()) || appUserDto.getUserPassword().isEmpty()) {
            throw new InvalidCredentialsException("Password must not be null or empty");
        }
        if (Objects.isNull(appUserDto.getFullName()) || appUserDto.getFullName().isEmpty()) {
            throw new InvalidCredentialsException("FullName must not be null or empty");
        }
        logger.info("appUserDto.getUserLogin()=" + appUserDto.getUserLogin());
        //Optional<AppUser> possibleDuplicate = this.appUserRepository.findByUserLogin(appUserDto.getUserLogin());
        Optional<AppUser> possibleDuplicate = this.appUserRepository.findAll().stream()
                .filter(x -> x.getUserLogin().equals(appUserDto.getUserLogin()))
                .findFirst();
        logger.info("possibleDuplicate");
        if (possibleDuplicate.isEmpty()){
            AppUser appUser = new AppUser();

            List<AppRole> userRolesEntityList = new ArrayList<>();
            userRolesEntityList.add(this.appRoleRepository.findByTitle("CUSTOMER"));

            appUser.setAppRoles(userRolesEntityList);
            appUser.setFullName(appUserDto.getFullName());
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
    public List<UserFlight> getMyPastFlights(UserFlightStatus status) throws IllegalArgumentException, UserFlightNotFoundException {
        return this.userFlightsService.getCustomersFlightBookingHistory(status);
    }

//    @Override
//    public List<UserFlight> getAvailableFlights() throws IllegalArgumentException, UserFlightNotFoundException {
//        return this.flightRepository.findAvailableFlights();
//    }

    @Override
    public UserFlight getCurrentFlight() throws IllegalArgumentException, UserFlightNotFoundException {
        return this.userFlightsService.getCurrentFlight();
    }

    @Override
    public List<AppUser> getAllCustomers(LocalDate dateRegisterBegin,
                                         LocalDate dateRegisterEnd,
                                         Boolean isDeleted) throws AppUserNotFoundException {
        BooleanBuilder booleanBuilder = new BooleanBuilder(
                this.appUserService.buildUsersCommonSearchPredicate(dateRegisterBegin, dateRegisterEnd, isDeleted)
        );
        QAppUser root = QAppUser.appUser;

        booleanBuilder.and(root.appRoles.any().title.eq("CUSTOMER"));

        Iterable<AppUser> applicationUsersEntityIterable =
                this.appUserRepository.findAll(booleanBuilder.getValue());

        List<AppUser> appUserList =
                StreamSupport
                        .stream(applicationUsersEntityIterable.spliterator(), false)
                        .collect(Collectors.toList());

        if (appUserList.isEmpty()) {
            throw new AppUserNotFoundException(
                    "Клиентов по указанным параметрам не найдено!"
            );
        }
        return appUserList;
    }

    @Transactional
    @Override
    public CustomerReview registerNewReview(CustomerReviewRequestDto requestDto)
            throws UserFlightNotFoundException, FlightNotFoundException
    {
        if (Objects.isNull(requestDto)) {
            throw new IllegalArgumentException("Создаваемый отзыв не может быть null!");
        }
        if (requestDto.getReview().isEmpty() || Objects.isNull(requestDto.getReview())) {
            throw new IllegalArgumentException("Текст отзыва не может быть null или пустым!");
        }
        if (Objects.isNull(requestDto.getFlightId())) {
            throw new IllegalArgumentException("ID регистрации на рейс не может быть null");
        }

        CustomerReview review = CustomerReviewMapper.mapCustomerDtoToEntity(requestDto);
        AppUser currentUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserFlight registration = this.userFlightsService.getCustomerFlightBookingById(requestDto.getFlightId());
        Flight flight = this.flightService.getFlightEntityByFlightId(registration.getFlight().getId());

        review.setAppUser(currentUser);
        review.setFlight(flight);

        this.customerReviewRepository.save(review);
        return review;
    }

    @Override
    public List<CustomerReviewResponseDto> getAllCustomersReviews(
            LocalDateTime dateRegisterBegin,
            LocalDateTime dateRegisterEnd,
            Long flightId
    )
            throws IncorrectFiltersException, CustomerReviewNotFoundException {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QCustomerReview root = QCustomerReview.customerReview;

        boolean registeredAfterIsNonNull = Objects.nonNull(dateRegisterBegin);
        if(registeredAfterIsNonNull) {
            booleanBuilder.and(root.dateRegister.goe(dateRegisterEnd));
        }
        if(Objects.nonNull(dateRegisterBegin)) {
            if(registeredAfterIsNonNull && dateRegisterEnd.isAfter(dateRegisterBegin)) {
                throw new IncorrectFiltersException(
                        "Неверно заданы фильтры поиска по дате! Начальная дата не может быть позже конечной!"
                );
            }
            booleanBuilder.and(root.dateRegister.goe(dateRegisterEnd));
        }

        Iterable<CustomerReview> clientFeedbacksEntityIterable =
                this.customerReviewRepository.findAll(booleanBuilder.getValue());
        List<CustomerReviewResponseDto> customerReviewResponseDtoList =
                StreamSupport
                        .stream(clientFeedbacksEntityIterable.spliterator(), false)
                        .map(CustomerReviewMapper::mapCustomerReviewToDto)
                        .collect(Collectors.toList());
        if (customerReviewResponseDtoList.isEmpty()) {
            throw new CustomerReviewNotFoundException("Отзывов пользователей по заданным параметрам не найдено!");
        }
        return customerReviewResponseDtoList;
    }


}
