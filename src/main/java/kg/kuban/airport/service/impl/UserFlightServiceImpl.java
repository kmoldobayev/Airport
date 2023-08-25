package kg.kuban.airport.service.impl;

import com.querydsl.core.BooleanBuilder;
import kg.kuban.airport.dto.UserFlightRegistrationResponseDto;
import kg.kuban.airport.dto.UserFlightRequestDto;
import kg.kuban.airport.entity.*;
import kg.kuban.airport.enums.FlightStatus;
import kg.kuban.airport.enums.UserFlightStatus;
import kg.kuban.airport.exception.*;
import kg.kuban.airport.mapper.FlightMapper;
import kg.kuban.airport.repository.UserFlightRepository;
import kg.kuban.airport.service.AppUserService;
import kg.kuban.airport.service.FlightService;
import kg.kuban.airport.service.SeatService;
import kg.kuban.airport.service.UserFlightService;
import kg.kuban.airport.util.AppUserRoleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserFlightServiceImpl implements UserFlightService {
    private final UserFlightRepository userFlightRepository;
    private final SeatService seatService;
    private final AppUserService appUserService;
    private final FlightService flightService;

    @Autowired
    public UserFlightServiceImpl(UserFlightRepository userFlightRepository, SeatService seatService, AppUserService appUserService, FlightService flightService) {
        this.userFlightRepository = userFlightRepository;
        this.seatService = seatService;
        this.appUserService = appUserService;
        this.flightService = flightService;
    }

    @Transactional
    @Override
    public List<UserFlight> registerEmployeesForFlight(List<UserFlightRequestDto> requestDtoList)
            throws
            FlightNotFoundException,
            IllegalFlightException,
            AppUserNotFoundException,
            NotEnoughRolesForCrewRegistrationException,
            InvalidUserRoleException {
        if(Objects.isNull(requestDtoList) || requestDtoList.isEmpty()) {
            throw new IllegalArgumentException(
                    "Список регистрируемых на рейс сотрудников не может быть null или пустым!"
            );
        }

        Long flightId = requestDtoList.get(0).getFlightId();
        List<Long> crewMembersIdList = new ArrayList<>();
        for (UserFlightRequestDto requestDto : requestDtoList) {
            Long crewMemberId = requestDto.getUserId();
            Long comparativeFlightId = requestDto.getFlightId();
            if(Objects.isNull(comparativeFlightId) || Objects.isNull(crewMemberId)) {
                throw new IllegalArgumentException(
                        "ID рейса, на который регистрируется сотрудник, и ID сотрудника не может быть null!"
                );
            }
            if(comparativeFlightId < 1L || crewMemberId < 1L) {
                throw new IllegalArgumentException(
                        "ID рейса, на который регистрируется сотрудник, и ID сотруднка не может быть меньше 1!"
                );
            }
            if(!flightId.equals(comparativeFlightId)){
                throw new IllegalArgumentException(
                        "ID рейса для всех регистрируемых на этот рейс сотрудников должен совпадать!"
                );
            }
            crewMembersIdList.add(crewMemberId);
        }

        Flight flight = this.flightService.getFlightEntityByFlightId(flightId);
        if (!flight.getStatus().equals(FlightStatus.REGISTERED)) {
            throw new IllegalFlightException(
                    "Регистрация сотрудников возможна только на недавно зарегистрированный рейс!"
            );
        }

        List<AppUser> crewMembers =
                this.appUserService.getUserEntitiesByIdList(crewMembersIdList);
        boolean userRolesEnoughForRegistration =
                AppUserRoleUtils.checkIfApplicationUsersListContainsSuchUserRolesTitles(
                        crewMembers,
                        "PILOT", "STEWARD", "CHIEF_STEWARD"
                );
        if (!userRolesEnoughForRegistration) {
            throw new NotEnoughRolesForCrewRegistrationException(
                    "Для регистрации рейса необходимо, чтобы в команде был хотя бы 1 пилот, стюард и старший стюард!"
            );
        }
        boolean crewMembersListContainsOnlyUsersWithValidRoles =
                AppUserRoleUtils.checkIfEachApplicationUserInListContainsRequiredRoles(
                        crewMembers,
                        "PILOT", "STEWARD", "CHIEF_STEWARD"
                );
        if(!crewMembersListContainsOnlyUsersWithValidRoles) {
            throw new InvalidUserRoleException(
                    "Список регистрируемых на рейс сотрудников содержит пользователя с недопустимыми ролями!"
            );
        }

        flight.setStatus(FlightStatus.CREW_MEMBERS_REGISTERED);
        List<UserFlight> crewMembersRegistrations = new ArrayList<>();
        for (AppUser crewMember : crewMembers) {
            UserFlight crewMemberRegistration = new UserFlight();
            crewMemberRegistration.setAppUser(crewMember);

            crewMemberRegistration.setFlight(flight);
            flight.getUserFlights().add(crewMemberRegistration);

            crewMemberRegistration.setStatus(UserFlightStatus.CREW_MEMBER_REGISTERED_FOR_FLIGHT);

            crewMembersRegistrations.add(crewMemberRegistration);
        }

        crewMembersRegistrations = this.userFlightRepository.saveAll(crewMembersRegistrations);
        return crewMembersRegistrations
                .stream()
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public UserFlight bookingCustomerForFlight(UserFlightRequestDto requestDto)
            throws 
            FlightNotFoundException,
            IllegalFlightException,
            AirplaneSeatNotFoundException, SeatBookingException {
        if(Objects.isNull(requestDto)) {
            throw new IllegalArgumentException("Создаваемая регистрация клиента на рейс не может быть null!");
        }
        if(Objects.isNull(requestDto.getFlightId()) || Objects.isNull(requestDto.getAirplaneSeatId())) {
            throw new IllegalArgumentException(
                    "ID рейса, на который регистрируется клиент, и ID бронируемого места не могут быть null"
            );
        }
        if(requestDto.getFlightId() < 1L || requestDto.getAirplaneSeatId() < 1L) {
            throw new IllegalArgumentException(
                    "ID рейса, на который регистрируется клиент, и ID бронируемого места не могут быть меньше 1!"
            );
        }

        AppUser CUSTOMER =
                (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Flight flight = this.flightService.getFlightEntityByFlightId(requestDto.getFlightId());
        if(!flight.getStatus().equals(FlightStatus.SELLING_TICKETS)) {
            throw new IllegalFlightException(
                    "Регистрация пользователей возможно только на рейсы, на которые продаются билеты!"
            );
        }

        Seat Seat = this.seatService.bookingSeat(requestDto.getAirplaneSeatId());
        flight = this.flightService.updateNumberOfRemainingTickets(flight.getId());

        UserFlight userFlight = new UserFlight();
        userFlight.setFlight(flight);
        userFlight.setSeat(Seat);
        userFlight.setAppUser(CUSTOMER);
        userFlight.setStatus(UserFlightStatus.CUSTOMER_BOOKING_FOR_FLIGHT);

        this.userFlightRepository.save(userFlight);
        return userFlight;
    }

    @Transactional
    @Override
    public UserFlight cancelCustomerBooking(Long bookingId)
            throws
            UserFlightNotFoundException,
            TicketCancelingException,
            AirplaneSeatNotFoundException,
            SeatBookingException,
            FlightNotFoundException
    {
        AppUser customer =
                (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserFlight customerBooking =
                this.getCustomerFlightBookingByCustomerIdAndUserFlightId(bookingId, customer.getId());
        if (!customerBooking.getFlight().getStatus().equals(FlightStatus.SELLING_TICKETS)) {
            throw new TicketCancelingException(
                    "Ошибка отмены регистрации на рейс! Отменить регистрацию возможно только во время продажи билетов!"
            );
        }

        this.seatService.cancelBookingSeat(customerBooking.getSeat().getId());
        this.flightService.updateNumberOfRemainingTickets(customerBooking.getFlight().getId());

        customerBooking.setStatus(UserFlightStatus.CUSTOMER_BOOKING_DECLINED);

        this.userFlightRepository.save(customerBooking);
        return customerBooking;
    }

    @Transactional
    @Override
    public UserFlight checkCustomer(Long customerBookingId)
            throws UserFlightNotFoundException,
            StatusChangeException
    {
        UserFlight customerBooking = this.getCustomerFlightBookingById(customerBookingId);
        Flight flight = customerBooking.getFlight();
        if(!flight.getStatus().equals(FlightStatus.CUSTOMER_CHECK)) {
            throw new StatusChangeException(
                    "Чтобы начать проверку мест клиентов она должна быть назначена диспетчером!"
            );
        }
        if (!customerBooking.getStatus().equals(UserFlightStatus.CUSTOMER_BOOKING_FOR_FLIGHT)) {
            throw new StatusChangeException(
                    "Чтобы проверить правильность занятого клиентом места он должен быть зарегестрированным на рейс!"
            );
        }

        customerBooking.setStatus(UserFlightStatus.CUSTOMER_CHECKED);

        this.userFlightRepository.save(customerBooking);
        return customerBooking;
    }

    @Transactional
    @Override
    public UserFlight distributeCustomersFood(Long customerBookingId)
            throws UserFlightNotFoundException,
            StatusChangeException
    {
        UserFlight customerBooking = this.getCustomerFlightBookingById(customerBookingId);
        Flight flight = customerBooking.getFlight();
        if(!flight.getStatus().equals(FlightStatus.FLIGHT_FOOD_DISTRIBUTION)) {
            throw new StatusChangeException(
                    "Чтобы начать раздачу еды она должна быть назначена главным стюардом!"
            );
        }
        if(!customerBooking.getStatus().equals(UserFlightStatus.CUSTOMER_BRIEFED)) {
            throw new StatusChangeException(
                    "Чтобы провести клиенту раздачу еды он должен быть помечен как проиструктированный!"
            );
        }

        customerBooking.setStatus(UserFlightStatus.CUSTOMER_FOOD_DISTRIBUTED);

        customerBooking = this.userFlightRepository.save(customerBooking);
        return customerBooking;
    }

    @Transactional
    @Override
    public UserFlight conductCustomersBriefing(Long customerBookingId)
            throws UserFlightNotFoundException,
            StatusChangeException
    {
        UserFlight customerBooking = this.getCustomerFlightBookingById(customerBookingId);
        Flight flight = customerBooking.getFlight();
        if (!flight.getStatus().equals(FlightStatus.CUSTOMERS_BRIEFING)) {
            throw new StatusChangeException(
                    "Чтобы начать инструктаж клиентов он должен быть назначен главным стюардом!"
            );
        }
        if (!customerBooking.getStatus().equals(UserFlightStatus.CUSTOMER_CHECKED)) {
            throw new StatusChangeException(
                    "Чтобы провести клиенту инструктаж стюард должен проверить занял ли клиент свое место в салоне!"
            );
        }

        customerBooking.setStatus(UserFlightStatus.CUSTOMER_BRIEFED);

        customerBooking = this.userFlightRepository.save(customerBooking);
        return customerBooking;
    }

    @Transactional
    @Override
    public UserFlight confirmReadinessForFlight() throws UserFlightNotFoundException, StatusChangeException
    {
        AppUser authorizedUser =
                (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserFlight userFlight = this.getUserFlightRegistrationByUserId(authorizedUser.getId());
        if(!userFlight.getFlight().getStatus().equals(FlightStatus.CUSTOMERS_READY)) {
            throw new StatusChangeException(
                    "Проверка готовности экипажа может начаться только после проверки готовности клиентов!"
            );
        }
        if(!userFlight.getStatus().equals(UserFlightStatus.CREW_MEMBER_REGISTERED_FOR_FLIGHT)) {
            throw new StatusChangeException(
                    String.format(
                            "Пользователь с ID[%d] не был зарегистрирован на данный рейс как член экипажа!",
                            authorizedUser.getId()
                    )
            );
        }

        userFlight.setStatus(UserFlightStatus.CREW_MEMBER_READY);
        this.checkIfAllCrewMembersIsReadyForFlight(userFlight.getFlight().getId());

        this.userFlightRepository.save(userFlight);
        return userFlight;
    }

    @Override
    public List<UserFlight> getAllUserRegistrations(
            Long flightId,
            UserFlightStatus status,
            Long userId,
            Boolean isCustomer
    )
            throws UserFlightNotFoundException {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QUserFlight root = QUserFlight.userFlight;

        if(Objects.nonNull(flightId)) {
            booleanBuilder.and(root.flight.id.eq(flightId));
        }
        if(Objects.nonNull(status)) {
            booleanBuilder.and(root.status.eq(status));
        }
        if(Objects.nonNull(userId)) {
            if(userId < 1L) {
                throw new IllegalArgumentException("ID пользователя не может быть меньше 1!");
            }
            booleanBuilder.and(root.appUser.id.eq(userId));
        }
        if(Objects.nonNull(isCustomer)) {
            if(isCustomer) {
                booleanBuilder.and(root.appUser.position.title.eq("CUSTOMER"));
            } else {
                booleanBuilder.and(root.appUser.position.title.ne("CUSTOMER"));
            }
        }

        Iterable<UserFlight> userFlightIterable =
                this.userFlightRepository.findAll(booleanBuilder.getValue());
        List<UserFlight> userFlight =
                StreamSupport
                        .stream(userFlightIterable.spliterator(), false)
                        .collect(Collectors.toList());
        if(userFlight.isEmpty()) {
            throw new UserFlightNotFoundException(
                    "Регистраций пользователей на рейс по укзанным параметрам не найдено!"
            );
        }
        return userFlight;
    }

    @Transactional
    @Override
    public List<UserFlight> getAllCustomerBookings(
            Long flightId,
            UserFlightStatus status
    )
            throws UserFlightNotFoundException
    {
        return this.getAllUserRegistrations(flightId, status, null, Boolean.TRUE);
    }

    @Override
    public List<UserFlight> getAllEmployeesBookings(
            Long flightId,
            UserFlightStatus status
    )
            throws UserFlightNotFoundException
    {
        return this.getAllUserRegistrations(flightId, status, null, Boolean.FALSE);
    }

    @Override
    public List<UserFlight> getAllCustomerBookingsForCurrentFlight(UserFlightStatus status)
            throws UserFlightNotFoundException
    {
        AppUser authorizedUser =
                (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserFlight userRegistration = this.getUserFlightRegistrationByUserId(authorizedUser.getId());
        return this.getAllCustomerBookings(userRegistration.getFlight().getId(), status);
    }

    @Override
    public List<UserFlight> getCustomersFlightBookingHistory(UserFlightStatus status)
            throws UserFlightNotFoundException,
            IllegalArgumentException
    {
        AppUser authorizedUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.getAllUserRegistrations(null, status, authorizedUser.getId(), Boolean.TRUE);
    }

    @Override
    public UserFlight getCurrentFlight()
            throws UserFlightNotFoundException,
            IllegalArgumentException
    {
        AppUser authorizedUser =
                (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.getUserFlightRegistrationByUserId(authorizedUser.getId());
    }

    @Override
    public UserFlight getCustomerFlightBookingByCustomerIdAndUserFlightId(
            Long registrationId,
            Long userId
    ) throws IllegalArgumentException, UserFlightNotFoundException {
        if(Objects.isNull(registrationId)) {
            throw new IllegalArgumentException("ID регистрации на рейс может быть null!");
        }
        if(registrationId < 1L) {
            throw new IllegalArgumentException("ID регистрации на рейс не может быть меньше 1!");
        }

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QUserFlight root = QUserFlight.userFlight;

        booleanBuilder.and(root.status.eq(UserFlightStatus.CUSTOMER_BOOKING_FOR_FLIGHT));
        booleanBuilder.and(root.id.eq(registrationId));
        booleanBuilder.and(root.appUser.id.eq(userId));

        Optional<UserFlight> userFlightOptional =
                this.userFlightRepository.findOne(booleanBuilder.getValue());
        if(userFlightOptional.isEmpty()) {
            throw new UserFlightNotFoundException(
                    String.format(
                            "Регистраций клиента с ID[%d] на рейс с ID[%d] не было найдено!",
                            userId,
                            registrationId
                    )
            );
        }
        return userFlightOptional.get();
    }

    @Override
    public UserFlight getCustomerFlightBookingById(Long bookingId)
            throws IllegalArgumentException,
            UserFlightNotFoundException
    {
        if (Objects.isNull(bookingId)) {
            throw new IllegalArgumentException("ID регистрации на рейс может быть null!");
        }
        if (bookingId < 1L) {
            throw new IllegalArgumentException("ID регистрации на рейс не может быть меньше 1!");
        }

        Optional<UserFlight> userFlightOptional =
                this.userFlightRepository.getUserFlightById(bookingId);

        if(userFlightOptional.isEmpty()) {
            throw new UserFlightNotFoundException(
                    String.format("Регистрации пользователя на рейс с ID[%d] не найдено!", bookingId)
            );
        }
        return userFlightOptional.get();
    }

    @Override
    public UserFlight getUserFlightRegistrationByUserId(Long userId)
            throws IllegalArgumentException, UserFlightNotFoundException {
        if(Objects.isNull(userId)) {
            throw new IllegalArgumentException("ID регистрации пользователя на рейс не может быть null!");
        }
        if(userId < 1L) {
            throw new IllegalArgumentException("ID регистрации пользователя на рейс не может быть меньше 1!");
        }

        Optional<UserFlight> userFlightOptional =
                this.userFlightRepository.getUserFlightByAppUserId(userId);

        if(userFlightOptional.isEmpty()) {
            throw new UserFlightNotFoundException(
                    String.format("Для пользователя с ID[%d] не найдено ни одной регистрации на рейс!", userId)
            );
        }
        return userFlightOptional.get();
    }

    @Transactional
    @Override
    public boolean checkIfAllPassengersOfFlightHaveStatus(
            Long flightId,
            UserFlightStatus status
    ) {
        if(Objects.isNull(flightId) || Objects.isNull(status)) {
            throw new IllegalArgumentException(
                    "ID рейса и статус регистрации пользователя на рейс не могут быть null!"
            );
        }
        if(flightId < 1L) {
            throw new IllegalArgumentException("ID рейса не может быть меньше 1!");
        }

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QUserFlight root = QUserFlight.userFlight;
        booleanBuilder.and(root.flight.id.eq(flightId));
        booleanBuilder.and(root.appUser.position.title.eq("CUSTOMER"));

        Iterable<UserFlight> userFlightsEntitiesIterable =
                this.userFlightRepository.findAll(booleanBuilder.getValue());
        List<UserFlight> userFlightsEntities =
                StreamSupport
                        .stream(userFlightsEntitiesIterable.spliterator(), false)
                        .collect(Collectors.toList());

        for (UserFlight userRegistration : userFlightsEntities) {
            if(!userRegistration.getStatus().equals(status)) {
                return false;
            }
        }
        return true;
    }

    @Transactional
    @Override
    public boolean checkIfAllCrewMembersIsReadyForFlight(Long flightId)
    {
        if(Objects.isNull(flightId)) {
            throw new IllegalArgumentException(
                    "ID рейса не может быть null!"
            );
        }
        if(flightId < 1L) {
            throw new IllegalArgumentException("ID рейса не может быть меньше 1!");
        }

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QUserFlight root = QUserFlight.userFlight;
        booleanBuilder.and(root.flight.id.eq(flightId));
        booleanBuilder.and(root.appUser.position.title.ne("CUSTOMER"));

        Iterable<UserFlight> userFlightsEntitiesIterable =
                this.userFlightRepository.findAll(booleanBuilder.getValue());
        List<UserFlight> userFlightsEntities =
                StreamSupport
                        .stream(userFlightsEntitiesIterable.spliterator(), false)
                        .collect(Collectors.toList());

        for (UserFlight userRegistration : userFlightsEntities) {
            if (!userRegistration.getStatus().equals(UserFlightStatus.CREW_MEMBER_READY)) {
                return false;
            }
        }
        return true;
    }
}
