package kg.kuban.airport.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import kg.kuban.airport.dto.AirportRequestDto;
import kg.kuban.airport.dto.FlightRequestDto;
import kg.kuban.airport.dto.FlightResponseDto;
import kg.kuban.airport.entity.*;
import kg.kuban.airport.enums.AirplaneStatus;
import kg.kuban.airport.enums.FlightStatus;
import kg.kuban.airport.enums.UserFlightStatus;
import kg.kuban.airport.exception.*;
import kg.kuban.airport.mapper.AirportMapper;
import kg.kuban.airport.mapper.FlightMapper;
import kg.kuban.airport.repository.AirplaneRepository;
import kg.kuban.airport.repository.AirportRepository;
import kg.kuban.airport.repository.FlightRepository;
import kg.kuban.airport.service.AirplaneService;
import kg.kuban.airport.service.FlightService;
import kg.kuban.airport.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FlightServiceImpl implements FlightService {

    private final AirplaneRepository airplaneRepository;
    private final AirportRepository airportRepository;

    private final FlightRepository flightRepository;
    private final AirplaneService airplaneService;
    private final SeatService seatService;

    @Autowired
    public FlightServiceImpl(AirplaneRepository airplaneRepository, AirportRepository airportRepository, FlightRepository flightRepository, AirplaneService airplaneService, SeatService seatService) {
        this.airplaneRepository = airplaneRepository;
        this.airportRepository = airportRepository;
        this.flightRepository = flightRepository;
        this.airplaneService = airplaneService;
        this.seatService = seatService;
    }

    @Transactional
    @Override
    public Flight registerNewFlight(FlightRequestDto flightRequestDto)
            throws AirplaneNotFoundException, UnavailableAirplaneException
    {


//        Optional<Airplane> existingAirplane = this.airplaneRepository.getAirplaneById(flightRequestDto.getAirplaneId());
//        Airport existingAirportDestination = this.airportRepository.findByTitle(flightRequestDto.getDestination().getTitle());
//
//        Flight flight = new Flight();
//
//        flight.setFlightNumber(flightRequestDto.getFlightNumber());
//        flight.setAirplane(existingAirplane.get());
//        flight.setAvailable(false);
//        flight.setDestination(existingAirportDestination);
//        flight.setDateRegister(LocalDateTime.now());
//
//
//        return flight;

        if (Objects.isNull(flightRequestDto)) {
            throw new IllegalArgumentException("Создаваемый рейс не может быть null!");
        }

        Flight flight = FlightMapper.mapFlightRequestDtoToEntity(flightRequestDto);
        Airplane airplane = this.airplaneService.findAirplaneById(flightRequestDto.getAirplaneId());
        if (!airplane.getStatus().equals(AirplaneStatus.AVAILABLE)) {
            throw new UnavailableAirplaneException(
                    "Ошибка! Для регистрации нового рейса необходимо выбрать самолет," +
                            " зарегестрированный в системе как доступный"
            );
        }
        flight.setAirplane(airplane);

        Integer airplaneSeatsNumber = this.seatService.getNumberFreeSeatsByAirplane(airplane.getId());
        flight.setStatus(FlightStatus.REGISTERED);
        flight.setTicketsLeft(airplaneSeatsNumber);

        Airport existingAirportDestination = this.airportRepository.findByTitle(flightRequestDto.getDestination().getTitle());
        flight.setDestination(existingAirportDestination);


        this.flightRepository.save(flight);
        return flight;
    }

    @Transactional
    @Override
    public Flight updateNumberOfRemainingTickets(Long flightId) throws FlightNotFoundException
    {
        Flight flight = this.getFlightEntityByFlightId(flightId);

        Integer freeSeats = this.seatService.getNumberFreeSeatsByAirplane(flight.getId());
        if (freeSeats.equals(0)) {
            flight.setStatus(FlightStatus.SOLD_OUT);
        }

        flight.setTicketsLeft(freeSeats);
        return this.flightRepository.save(flight);
    }

    @Transactional
    @Override
    public void informThatAllCrewMembersIsReadyForFlight(Long flightId)
            throws FlightNotFoundException, StatusChangeException
    {
        Flight flight = this.getFlightEntityByFlightId(flightId);
        if (!flight.getStatus().equals(FlightStatus.CUSTOMERS_READY)) {
            throw new StatusChangeException(
                    "Перед проверкой готовности членов экипажа необходимо проверить готовность пассажиров!"
            );
        }
        flight.setStatus(FlightStatus.CREW_READY);
        this.flightRepository.save(flight);
    }

    @Transactional
    @Override
    public void informThatAllCustomersAreChecked(Long flightId) throws FlightNotFoundException, StatusChangeException {
        Flight Flight = this.getFlightEntityByFlightId(flightId);
        if(!Flight.getStatus().equals(FlightStatus.CUSTOMER_CHECK)) {
            throw new StatusChangeException(
                    "Чтобы провести проверку готовности пассажиров, она должна быть назначена диспетчером!"
            );
        }
        Flight.setStatus(FlightStatus.CUSTOMERS_CHECKED);
        this.flightRepository.save(Flight);
    }

    @Transactional
    @Override
    public void informThatAllCustomersAreBriefed(Long flightId) throws FlightNotFoundException, StatusChangeException {
        Flight Flight = this.getFlightEntityByFlightId(flightId);
        if(!Flight.getStatus().equals(FlightStatus.CUSTOMERS_BRIEFED)) {
            throw new StatusChangeException(
                    "Чтобы провести инструктаж пассажиров он должен быть назначен главным стюардом!"
            );
        }
        Flight.setStatus(FlightStatus.CUSTOMERS_BRIEFED);
        this.flightRepository.save(Flight);
    }

    @Transactional
    @Override
    public void informThatAllCustomersFoodIsDistributed(Long flightId) throws FlightNotFoundException, StatusChangeException {
        Flight Flight = this.getFlightEntityByFlightId(flightId);
        if (!Flight.getStatus().equals(FlightStatus.FLIGHT_FOOD_DISTRIBUTION)) {
            throw new StatusChangeException(
                    "Чтобы провести раздачу еды она должна быть назначена главным стюардом!"
            );
        }
        Flight.setStatus(FlightStatus.FLIGHT_FOOD_DISTRIBUTED);
        this.flightRepository.save(Flight);
    }

    @Transactional
    @Override
    public Flight confirmFlightRegistration(Long flightId)
            throws FlightNotFoundException, StatusChangeException
    {
        Flight Flight = this.getFlightEntityByFlightId(flightId);
        if (!Flight.getStatus().equals(FlightStatus.CREW_MEMBERS_REGISTERED)) {
            throw new StatusChangeException(
                    "Чтобы регистрация рейса могла быть подтверждена на" +
                            " него сначала должны быть зарегестрированы все необходимые члены экипажа!"
            );
        }

        Flight.setStatus(FlightStatus.SELLING_TICKETS);
        this.flightRepository.save(Flight);

        return Flight;
    }

    @Transactional
    @Override
    public Flight initiateFlightDeparturePreparations(Long flightId)
            throws
            FlightNotFoundException,
            StatusChangeException
    {
        Flight Flight = this.getFlightEntityByFlightId(flightId);
        if(!Flight.getStatus().equals(FlightStatus.SOLD_OUT)) {
            throw new StatusChangeException(
                    "Перед инициацией отправки рейса на рейс должны быть выкуплены все билеты!"
            );
        }

        Flight.setStatus(FlightStatus.DEPARTURE_INITIATED);
        this.flightRepository.save(Flight);

        return Flight;
    }

    @Transactional
    @Override
    public Flight initiateCrewPreparation(Long flightId) throws FlightNotFoundException, StatusChangeException
    {
        Flight Flight = this.getFlightEntityByFlightId(flightId);
        if (!Flight.getStatus().equals(FlightStatus.TECH_PREP_COMPLETE)) {
            throw new StatusChangeException(
                    "Перед началом проверки клиентов должна быть проведена заправка самолета!"
            );
        }

        Flight.setStatus(FlightStatus.CUSTOMER_CHECK);
        this.flightRepository.save(Flight);

        return Flight;
    }

    @Transactional
    @Override
    public Flight confirmAirplaneRefueling(Long flightId)
            throws FlightNotFoundException,
            StatusChangeException,
            AirplaneNotReadyException
    {
        Flight Flight = this.getFlightEntityByFlightId(flightId);
        if (!Flight.getStatus().equals(FlightStatus.DEPARTURE_INITIATED)) {
            throw new StatusChangeException(
                    "Чтобы провести заправку самолета должна быть инициировона отправка рейса!"
            );
        }
        if (!Flight.getAirplane().getStatus().equals(AirplaneStatus.REFUELED)) {
            throw new AirplaneNotReadyException(
                    "Ошибка подтверждения заправки самолета! Заправка не была проведена!"
            );
        }

        Flight.setStatus(FlightStatus.TECH_PREP_COMPLETE);
        this.flightRepository.save(Flight);

        return Flight;
    }

    @Transactional
    @Override
    public Flight assignBriefing(Long flightId) throws FlightNotFoundException, StatusChangeException
    {
        Flight Flight = this.getFlightEntityByFlightId(flightId);
        if (!Flight.getStatus().equals(FlightStatus.CUSTOMERS_CHECKED)) {
            throw new StatusChangeException(
                    "Перед проведением инструктажа необходимо проверить правильность занимаемых клиентами мест!"
            );
        }

        Flight.setStatus(FlightStatus.CUSTOMERS_BRIEFING);
        this.flightRepository.save(Flight);

        return Flight;
    }

    @Transactional
    @Override
    public Flight confirmCustomerReadiness(Long flightId) throws FlightNotFoundException, StatusChangeException
    {
        Flight Flight = this.getFlightEntityByFlightId(flightId);
        if (!Flight.getStatus().equals(FlightStatus.CUSTOMERS_BRIEFED)) {
            throw new StatusChangeException(
                    "Для подтверждения инструктажа необходимо чтобы все клиенты были проинструктированы!"
            );
        }

        Flight.setStatus(FlightStatus.CUSTOMERS_READY);
        this.flightRepository.save(Flight);

        return Flight;
    }

    @Transactional
    @Override
    public Flight initiateDeparture(Long flightId) throws FlightNotFoundException, StatusChangeException
    {
        Flight Flight = this.getFlightEntityByFlightId(flightId);
        if(!Flight.getStatus().equals(FlightStatus.CREW_READY)) {
            throw new StatusChangeException(
                    "Для инициации старта рейса необходимо, чтобы все члены экипажа подтвердили свою готовность!"
            );
        }

        Flight.setStatus(FlightStatus.DEPARTURE_READY);
        this.flightRepository.save(Flight);

        return Flight;
    }

    @Transactional
    @Override
    public Flight confirmDeparture(Long flightId) throws FlightNotFoundException, StatusChangeException
    {
        Flight flight = this.getFlightEntityByFlightId(flightId);
        if (!flight.getStatus().equals(FlightStatus.DEPARTURE_READY)) {
            throw new StatusChangeException(
                    "Для подтверждения старта рейса он должен быть инициирован диспетчером!"
            );
        }

        flight.setStatus(FlightStatus.DEPARTURE_CONFIRMED);
        flight = this.flightRepository.save(flight);

        return flight;
    }

    @Transactional
    @Override
    public Flight startFlight(Long flightId) throws FlightNotFoundException, StatusChangeException
    {
        Flight Flight = this.getFlightEntityByFlightId(flightId);
        if (!Flight.getStatus().equals(FlightStatus.DEPARTURE_CONFIRMED)) {
            throw new StatusChangeException(
                    "Для подтверждения старта рейса он должен быть инициирован диспетчером!"
            );
        }

        Flight.setStatus(FlightStatus.FLIGHT_STARTED);
        Flight.getAirplane().setStatus(AirplaneStatus.IN_AIR);
        this.flightRepository.save(Flight);

        return Flight;
    }

    @Transactional
    @Override
    public Flight assignFoodDistribution(Long flightId) throws FlightNotFoundException, StatusChangeException
    {
        Flight Flight = this.getFlightEntityByFlightId(flightId);
        if(!Flight.getStatus().equals(FlightStatus.FLIGHT_STARTED)) {
            throw new StatusChangeException(
                    "Для назначения раздачи еды рейс должен быть начат!"
            );
        }

        Flight.setStatus(FlightStatus.FLIGHT_FOOD_DISTRIBUTION);
        Flight = this.flightRepository.save(Flight);

        return Flight;
    }

    @Transactional
    @Override
    public Flight requestLanding(Long flightId)
            throws FlightNotFoundException,
            StatusChangeException
    {
        Flight Flight = this.getFlightEntityByFlightId(flightId);
        if(!Flight.getStatus().equals(FlightStatus.FLIGHT_FOOD_DISTRIBUTED)) {
            throw new StatusChangeException(
                    "Для запроса посадки раздача еды должна закончиться и все клиенты должны занять свои места!"
            );
        }

        Flight.setStatus(FlightStatus.LANDING_REQUESTED);
        Flight = this.flightRepository.save(Flight);

        return Flight;
    }

    @Transactional
    @Override
    public Flight assignLanding(Long flightId) throws FlightNotFoundException, StatusChangeException
    {
        Flight Flight = this.getFlightEntityByFlightId(flightId);
        if (!Flight.getStatus().equals(FlightStatus.LANDING_REQUESTED)) {
            throw new StatusChangeException(
                    "Для назначения посадки она должна быть запрошена пилотом!!"
            );
        }

        Flight.setStatus(FlightStatus.LANDING_PENDING_CONFIRMATION);
        Flight = this.flightRepository.save(Flight);

        return Flight;
    }

    @Transactional
    @Override
    public Flight confirmLanding(Long flightId) throws FlightNotFoundException, StatusChangeException
    {
        Flight Flight = this.getFlightEntityByFlightId(flightId);
        if(!Flight.getStatus().equals(FlightStatus.LANDING_PENDING_CONFIRMATION)) {
            throw new StatusChangeException(
                    "Для подтверждения разрешения посадки она должна быть назначена диспетчером!"
            );
        }

        Flight.setStatus(FlightStatus.LANDING_CONFIRMED);
        this.flightRepository.save(Flight);

        return Flight;
    }

    @Transactional
    @Override
    public Flight endFlight(Long flightId)
            throws FlightNotFoundException,
            StatusChangeException
    {
        Flight Flight = this.getFlightEntityByFlightId(flightId);
        if (!Flight.getStatus().equals(FlightStatus.LANDING_CONFIRMED)) {
            throw new StatusChangeException(
                    "Для посадки самолета она должна быть подтверждена главным диспетчером!"
            );
        }

        Flight.setStatus(FlightStatus.ARRIVED);
        Flight.getAirplane().setStatus(AirplaneStatus.TO_CHECKUP);
        for (UserFlight userFlight : Flight.getUserFlights()) {
            userFlight.setStatus(UserFlightStatus.ARRIVED);
            if (userFlight.getAppUser().getPosition().getTitle().equals("CUSTOMER")) {
                userFlight.getSeat().setOccupied(Boolean.FALSE);
            }
        }

        Flight = this.flightRepository.save(Flight);

        return Flight;
    }

    @Override
    public List<Flight> getAvailableFlights(
            LocalDateTime dateRegisterBeg,
            LocalDateTime dateRegisterEnd,
            FlightStatus flightStatus
    )
            throws IncorrectFiltersException, FlightNotFoundException {
        BooleanBuilder booleanBuilder = new BooleanBuilder(
                this.createCommonFlightsSearchPredicate(dateRegisterBeg, dateRegisterEnd, flightStatus)
        );

        List<Flight> flightList = this.flightRepository.findAvailableFlights();
        if (flightList.isEmpty()) {
            throw new FlightNotFoundException("Рейсы по заданным параметрам не найдены!");
        }
        return flightList;
    }

    @Override
    public List<Flight> getFlightsForTicketBooking(
            LocalDateTime dateCreateBeg,
            LocalDateTime dateCreateEnd,
            AirportRequestDto flightDestination
    )
            throws FlightNotFoundException,
            IncorrectFiltersException
    {
        BooleanBuilder booleanBuilder = new BooleanBuilder(
                this.createCommonFlightsSearchPredicate(dateCreateBeg, dateCreateEnd, FlightStatus.SELLING_TICKETS)
        );
        QFlight root = QFlight.flight;

        booleanBuilder.and(root.destination.eq(AirportMapper.mapAirportDtoToEntity(flightDestination)));
        booleanBuilder.and(root.ticketsLeft.gt(0));

        Iterable<Flight> FlightIterable =
                this.flightRepository.findAll(booleanBuilder.getValue());
        List<Flight> flightList =
                StreamSupport
                        .stream(FlightIterable.spliterator(), false)
                        .collect(Collectors.toList());
        if (flightList.isEmpty()) {
            throw new FlightNotFoundException(
                    "Рейсы, на которые продвются билеты, по заданным параметрам не найдены!"
            );
        }
        return flightList;
    }

    @Override
    public Flight getFlightEntityByFlightId(Long flightId) throws FlightNotFoundException
    {
        if(Objects.isNull(flightId)) {
            throw new IllegalArgumentException("ID рейса не может быть null!");
        }

        Optional<Flight> FlightOptional = this.flightRepository.getFlightById(flightId);
        if(FlightOptional.isEmpty()) {
            throw new FlightNotFoundException(
                    String.format("Рейса с ID[%d] не найдено!")
            );
        }
        return FlightOptional.get();
    }

    private Predicate createCommonFlightsSearchPredicate(
            LocalDateTime registeredAfter,
            LocalDateTime registeredBefore,
            FlightStatus flightStatus
    ) throws IncorrectFiltersException {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QFlight root = QFlight.flight;

        if(Objects.nonNull(flightStatus)) {
            booleanBuilder.and(root.status.eq(flightStatus));
        }
        boolean registeredAfterIsNonNull = Objects.nonNull(registeredAfter);
        if(registeredAfterIsNonNull) {
            booleanBuilder.and(root.dateRegister.goe(registeredAfter));
        }
        if(Objects.nonNull(registeredBefore)) {
            if(registeredAfterIsNonNull && registeredAfter.isAfter(registeredBefore)) {
                throw new IncorrectFiltersException(
                        "Неверно заданы фильтры поиска по дате! Начальная дата не может быть позже конечной!"
                );
            }
            booleanBuilder.and(root.dateRegister.goe(registeredAfter));
        }

        return booleanBuilder.getValue();
    }
}
