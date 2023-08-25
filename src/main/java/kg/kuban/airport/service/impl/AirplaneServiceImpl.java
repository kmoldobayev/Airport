package kg.kuban.airport.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import kg.kuban.airport.dto.AirplaneRequestDto;
import kg.kuban.airport.dto.AirplanePartCheckupRequestDto;
import kg.kuban.airport.dto.AirplaneTypesResponseDto;
import kg.kuban.airport.entity.*;
import kg.kuban.airport.enums.AirplaneStatus;
import kg.kuban.airport.enums.AirplanePartStatus;
import kg.kuban.airport.enums.AirplaneType;
import kg.kuban.airport.enums.FlightStatus;
import kg.kuban.airport.exception.*;
import kg.kuban.airport.mapper.AircompanyMapper;
import kg.kuban.airport.mapper.AirplaneMapper;
import kg.kuban.airport.repository.AircompanyRepository;
import kg.kuban.airport.repository.AirplaneRepository;
import kg.kuban.airport.repository.AppUserRepository;
import kg.kuban.airport.repository.SeatRepository;
import kg.kuban.airport.service.*;
import kg.kuban.airport.util.AppUserRoleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AirplaneServiceImpl implements AirplaneService {

    private final AppUserRepository appUserRepository;
    private final AircompanyRepository aircompanyRepository;
    private final AirplaneRepository airplaneRepository;
    private final SeatRepository seatRepository;

    private final AppUserService appUserService;
    private final AirplanePartCheckupService partCheckupService;
    private final SeatService seatService;
    private final AirplanePartService partService;
    
    @Autowired
    public AirplaneServiceImpl(AppUserRepository appUserRepository, AircompanyRepository aircompanyRepository, AirplaneRepository airplaneRepository, SeatRepository seatRepository, AppUserService appUserService, AirplanePartCheckupService partCheckupService, SeatService seatService, AirplanePartService partService) {
        this.appUserRepository = appUserRepository;
        this.aircompanyRepository = aircompanyRepository;
        this.airplaneRepository = airplaneRepository;
        this.seatRepository = seatRepository;
        this.appUserService = appUserService;
        this.partCheckupService = partCheckupService;
        this.seatService = seatService;
        this.partService = partService;
    }

    @Transactional
    @Override
    public Airplane assignAirplaneRepairs(Long airplaneId, Long engineersId)
            throws EngineerIsBusyException,
                    StatusChangeException,
                    AppUserNotFoundException,
                    AirplaneNotFoundException,
                    AirplanePartCheckupNotFoundException,
                    StatusNotFoundException
    {

        Airplane airplane = this.findAirplaneById(airplaneId);
        if (!airplane.getStatus().equals(AirplaneStatus.INSPECTED)) {
            throw new StatusChangeException(
                    "Чтобы отправить самолет на ремонт самолет должен быть осмотрен инженером!"
            );
        }
        if (!this.partCheckupService.getLastAirplaneCheckupResult(airplaneId).equals(AirplanePartStatus.MAINTENANCE)) {
            throw new StatusChangeException(
                    String.format(
                            "Чтобы отправить самолет на ремонт хотя бы одна деталь самолета должны быть неисправна!" +
                                    "Результат последнего техосмотра: %s",
                            AirplanePartStatus.OK.toString()
                    )
            );
        }

        AppUser engineer = this.appUserService.getEngineerAppUserById(engineersId);
        if (Objects.nonNull(engineer.getServicedAirplane())) {
            throw new EngineerIsBusyException(
                    String.format(
                            "Невозможно назначить инженера с ID[%d] на ремонт самолета." +
                                    " В данный момент инженер обслуживает другой самолет!",
                            engineersId
                    )
            );
        }

        airplane.setStatus(AirplaneStatus.ON_REPAIRS);
        engineer.setServicedAirplane(airplane);
        airplane.setServicedBy(engineer);

        this.airplaneRepository.save(airplane);
        //"Самолет отправлен на ремонт! Текущий статус самолета:[%s]"
        return airplane;
    }

    /**
     * Регистрация нового самолета (задача Диспетчера)
     * @param airplaneRequestDto
     * @return Airplane
     */
    @Transactional
    @Override
    public Airplane registerNewAirplane(AirplaneRequestDto airplaneRequestDto)
            throws AirplanePartNotFoundException, IncompatiblePartException
    {

        if (Objects.isNull(airplaneRequestDto)) {
            throw new IllegalArgumentException("Реквизиты самолета пустые! Заполните!");
        }

        if (Objects.isNull(airplaneRequestDto.getAircompany())) {
            throw new IllegalArgumentException("Заполните авиакомпанию (поле aircompany)!");
        }

        Aircompany aircompany = AircompanyMapper.mapAircompanyRequestDtoToEntity(airplaneRequestDto.getAircompany());

        Optional<Aircompany> existingAircompany = this.aircompanyRepository.findAircompanyByTitle(aircompany.getTitle());


        Airplane airplane = new Airplane();

        airplane.setMarka(airplaneRequestDto.getMarka());
        airplane.setBoardNumber(airplaneRequestDto.getBoardNumber());
        airplane.setAirCompany(existingAircompany.get());

        airplane.setNumberSeats(airplaneRequestDto.getNumberSeats());
        airplane.setStatus(AirplaneStatus.TO_CHECKUP);
        airplane.setServicedBy(null);

        // Генерация мест в самолете в зависимости от макс количества вместимости самолета
        List<Seat> airplaneSeats = this.seatService.generateSeats(airplaneRequestDto.getNumberSeats());
        airplane.setAirplaneSeats(airplaneSeats);
        for (Seat airplaneSeat : airplaneSeats) {
            airplaneSeat.setAirplane(airplane);
        }
        // Создание частей самолета по техосмотру в зависимости от марки самолета
        List<AirplanePart> airplaneParts = this.partService.getPartByPartsIdListAndAirplaneType(
                airplaneRequestDto.getPartIdList(),
                airplane.getMarka()
        );
        if (airplaneParts.size() > 0) {
            airplane.setParts(airplaneParts);
            for (AirplanePart part : airplaneParts) {
                part.getAirplanes().add(airplane);
            }
        }

        this.airplaneRepository.save(airplane);

        return airplane;
    }

    /**
     * Выдача на осмотр инженеру (задача Главного инженера )
     * Главный инженер записывает в поле user_id ссылку на инженера
     * @param airplaneId
     * @param userId
     * @return
     */

    @Transactional
    @Override
    public Airplane assignAirplaneCheckup(Long airplaneId, Long userId)
            throws AirplaneNotFoundException, StatusChangeException, EngineerIsBusyException
    {
        Airplane airplane = this.findAirplaneById(airplaneId);
        if (!airplane.getStatus().equals(AirplaneStatus.TO_CHECKUP)) {
            throw new StatusChangeException(
                    "Для назначения техосмотра самолет должен быть передан на техосмотр диспетчером!"
            );
        }

        AppUser engineer = this.appUserRepository.getReferenceById(userId);
        if(Objects.nonNull(engineer.getServicedAirplane())) {
            throw new EngineerIsBusyException(
                    String.format(
                            "Невозможно назначить инженера с ID[%d] на техосмотр." +
                                    " В данный момент инженер обслуживает другой самолет!",
                            userId
                    )
            );
        }

        engineer.setServicedAirplane(airplane);
        airplane.setServicedBy(engineer);
        airplane.setStatus(AirplaneStatus.ON_CHECKUP);

        this.airplaneRepository.save(airplane);
        return airplane;
    }

    /**
     * Составляет технический осмотр деталей
     * @param airplaneId
     * @param partCheckupsRequestDtoList
     * @return List<AirplanePartCheckup>
     */
    @Transactional
    @Override
    public List<AirplanePartCheckup> checkupAirplane(Long airplaneId,
                                                     List<AirplanePartCheckupRequestDto> partCheckupsRequestDtoList)
            throws
                AirplaneNotFoundException,
                StatusChangeException,
                WrongEngineerException,
                AirplaneIsNotOnServiceException,
                AirplanePartNotFoundException,
                IllegalAirplaneException,
                IncompatiblePartException {
        if (partCheckupsRequestDtoList.isEmpty()) {
            throw new IllegalArgumentException("Список осмотров деталей не может быть null!");
        }

        Airplane airplane = this.findAirplaneById(airplaneId);
        if (!airplane.getStatus().equals(AirplaneStatus.ON_CHECKUP) && !airplane.getStatus().equals(AirplaneStatus.ON_REPAIRS)) {
            throw new StatusChangeException(
                    "Для проведения техосмотра самолета он должен быть назначен главным инжененром!"
            );
        }

        AppUser engineer = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!engineer.getId().equals(airplane.getServicedBy().getId())) {
            throw new WrongEngineerException(
                    String.format(
                            "Ошибка! Технический осмотр самолета с ID[%d] был назначен другому инженеру!",
                            airplane.getId()
                    )
            );
        }

        List<AirplanePartCheckup> airplanePartCheckupList =
                this.partCheckupService.registerPartCheckups(airplane, partCheckupsRequestDtoList);

        airplane.getServicedBy().setServicedAirplane(null);
        airplane.setServicedBy(null);
        airplane.setStatus(AirplaneStatus.INSPECTED);

        this.airplaneRepository.save(airplane);
        return airplanePartCheckupList;
    }

    public Airplane findAirplaneById(Long airplaneId) throws AirplaneNotFoundException
    {
        if(Objects.isNull(airplaneId)) {
            throw new IllegalArgumentException("ID самолета не может быть null!");
        }
        Optional<Airplane> airplaneOptional =
                this.airplaneRepository.findById(airplaneId);
        if (airplaneOptional.isEmpty()) {
            throw new AirplaneNotFoundException(String.format("Самолета с ID %d не найдено!", airplaneId));
        }
        return airplaneOptional.get();
    }

    @Transactional
    @Override
    public Boolean deleteNewAirplane(Long airplaneId)
            throws IllegalArgumentException, AirplaneNotFoundException, AirplaneSeatNotFoundException
    {
        Airplane airplane = findAirplaneById(airplaneId);



        this.airplaneRepository.delete(airplane);
        //this.seatRepository.deleteSeatsByAirplane_Id(airplaneId);

        List<Seat> seatsInAirplane = this.seatService.getAllSeats(airplaneId, false);

        this.seatRepository.deleteAll();
        return true;
    }

    @Transactional
    @Override
    public Airplane confirmAirplaneServiceAbility(Long airplaneId)
            throws AirplaneNotFoundException,
                AirplanePartCheckupNotFoundException,
                StatusChangeException,
                StatusNotFoundException
    {

        Airplane airplane = this.findAirplaneById(airplaneId);
        if (!airplane.getStatus().equals(AirplaneStatus.INSPECTED)) {
            throw new StatusChangeException(
                    "Чтобы подтвердить исправность самолета самолет должен быть осмотрен инженером!"
            );
        }

        if (!this.partCheckupService.getLastAirplaneCheckupResult(airplaneId).equals(AirplanePartStatus.OK)) {
            throw new StatusChangeException(
                    String.format(
                            "Чтобы подтвердить исправность самолета все детали самолета должны быть исправны!" +
                                    "Результат последнего техосмотра: %s",
                            AirplanePartStatus.MAINTENANCE.toString()
                    )
            );
        }

        airplane.setStatus(AirplaneStatus.SERVICEABLE);

        this.airplaneRepository.save(airplane);
        return airplane;
    }

    @Transactional
    @Override
    public Airplane sendAirplaneToRegistrationConfirmation(Long airplaneId)
            throws AirplaneNotFoundException, StatusChangeException {
        Airplane airplane = this.findAirplaneById(airplaneId);
        if (!airplane.getStatus().equals(AirplaneStatus.SERVICEABLE)) {
            throw new StatusChangeException(
                    "Чтобы отправить самолет на подверждение регистрации его" +
                            " техосмотр должен быть подтвержден главным инженером!"
            );
        }

        airplane.setStatus(AirplaneStatus.REGISTRATION_PENDING_CONFIRMATION);

        this.airplaneRepository.save(airplane);
        //"Самолет отправлен на подтверждение регистрации! Текущий статус самолета:[%s]"
        return airplane;
    }

    @Transactional
    @Override
    public Airplane confirmAirplaneRegistration(Long airplaneId) throws AirplaneNotFoundException, StatusChangeException {
        Airplane airplane = this.findAirplaneById(airplaneId);
        if(!airplane.getStatus().equals(AirplaneStatus.REGISTRATION_PENDING_CONFIRMATION)) {
            throw new StatusChangeException(
                    "Для подтверждения регистрации самолета он должен быть направлен главному диспетчеру диспетчером"
            );
        }

        airplane.setStatus(AirplaneStatus.AVAILABLE);
        //"Регистрация самолета подтверждена! Текущий статус самолета: [%s]"
                
        this.airplaneRepository.save(airplane);
        return airplane;
    }

    @Transactional
    @Override
    public Airplane refuelAirplane(Long airplaneId)
            throws AirplaneNotFoundException, StatusChangeException, EngineerIsBusyException {
        Airplane airplane = this.findAirplaneById(airplaneId);
//        List<Flight> airplanesFlights = airplane.getFlights();
//        if (!airplanesFlights.get(airplanesFlights.size() - 1).getStatus().equals(FlightStatus.DEPARTURE_INITIATED)) {
//            throw new StatusChangeException(
//                    "Чтобы отправить самолет на заправку отправка рейса самолета должна быть инициирована!"
//            );
//        }
//
        airplane.setStatus(AirplaneStatus.REFUELED);

        //"Самолет отправлен на заправку! Текущий статус самолета:[%s]"

        this.airplaneRepository.save(airplane);
        return airplane;
    }

    @Transactional
    @Override
    public Airplane assignAirplaneRefueling(Long airplaneId, 
                                            Long engineerId) 
            throws AirplaneNotFoundException,
            StatusChangeException,
            AppUserNotFoundException,
            EngineerIsBusyException,
            FlightNotFoundException
    {
        Airplane airplane = this.findAirplaneById(airplaneId);
        List<Flight> airplanesFlights = airplane.getFlights();
        if (airplanesFlights.size() == 0) {
            throw new FlightNotFoundException("Не найдены рейсы по данному самолету!");
        }
        if (!airplanesFlights.get(airplanesFlights.size() - 1).getStatus().equals(FlightStatus.DEPARTURE_INITIATED)) {
            throw new StatusChangeException(
                    "Чтобы отправить самолет на заправку отправка рейса самолета должна быть инициирована!"
            );
        }

        AppUser engineer = this.appUserService.getEngineerAppUserById(engineerId);
        if (Objects.nonNull(engineer.getServicedAirplane())) {
            throw new EngineerIsBusyException(
                    String.format(
                            "Невозможно назначить инженера с ID[%d] на заправку." +
                                    " В данный момент инженер обслуживает другой самолет!",
                            engineerId
                    )
            );
        }

        airplane.setStatus(AirplaneStatus.ON_REFUELING);

        engineer.setServicedAirplane(airplane);
        airplane.setServicedBy(engineer);
        //"Самолет отправлен на заправку! Текущий статус самолета:[%s]"
                
        this.airplaneRepository.save(airplane);
        return airplane;
    }

    @Override
    public List<Airplane> getAllAirplanes( LocalDateTime dateRegisterBeg,
                                           LocalDateTime dateRegisterEnd,
                                           AirplaneType airplaneType,
                                           AirplaneStatus airplaneStatus)
            throws IncorrectFiltersException, AirplaneNotFoundException
    {
        BooleanBuilder booleanBuilder = new BooleanBuilder(
                this.buildCommonAirplanesSearchPredicate(airplaneType, dateRegisterBeg, dateRegisterEnd)
        );
        QAirplane root = QAirplane.airplane;

        if (Objects.nonNull(airplaneStatus)) {
            booleanBuilder.and(root.status.eq(airplaneStatus));
        }

        return this.findAirplanesByBuiltPredicate(booleanBuilder.getValue());
    }

    @Override
    public List<Airplane> getAirplanesForRepairs(AirplaneType airplaneType,
                                                            LocalDateTime registeredBefore,
                                                            LocalDateTime registeredAfter)
            throws IncorrectFiltersException, AirplaneNotFoundException
    {
        AppUser authorizedUser =
                (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        BooleanBuilder booleanBuilder = new BooleanBuilder(
                this.buildCommonAirplanesSearchPredicate(airplaneType, registeredAfter, registeredBefore)
        );
        QAirplane root = QAirplane.airplane;
        booleanBuilder.and(root.status.eq(AirplaneStatus.ON_REPAIRS));
        if (AppUserRoleUtils.checkIfUserRolesListContainsSuchUserRoleTitle(
                authorizedUser.getAppRoles(),
                "ENGINEER"
        )) {
            booleanBuilder.and(root.servicedBy.id.eq(authorizedUser.getId()));
        }
        return this.findAirplanesByBuiltPredicate(booleanBuilder.getValue());
    }

    @Override
    public List<Airplane> getNewAirplanes(LocalDateTime dateRegisterBeg,
                                          LocalDateTime dateRegisterEnd,
                                          AirplaneType airplaneType)
            throws IncorrectFiltersException, AirplaneNotFoundException {
        AppUser authorizedUser =
                (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BooleanBuilder booleanBuilder = new BooleanBuilder(
                this.buildCommonAirplanesSearchPredicate(airplaneType, dateRegisterBeg, dateRegisterEnd)
        );
        QAirplane root = QAirplane.airplane;
        booleanBuilder.and(root.status.eq(AirplaneStatus.TO_CHECKUP));
        if (AppUserRoleUtils.checkIfUserRolesListContainsSuchUserRoleTitle(
                authorizedUser.getAppRoles(),
                "ENGINEER"
        )) {
            booleanBuilder.and(root.servicedBy.id.eq(authorizedUser.getId()));
        }
        return this.findAirplanesByBuiltPredicate(booleanBuilder.getValue());
    }

    @Override
    public List<Airplane> getAirplanesForRefueling(AirplaneType airplaneType,
                                                   LocalDateTime registeredBefore,
                                                   LocalDateTime registeredAfter)
            throws IncorrectFiltersException, AirplaneNotFoundException
    {
        AppUser authorizedUser =
                (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BooleanBuilder booleanBuilder = new BooleanBuilder(
                this.buildCommonAirplanesSearchPredicate(airplaneType, registeredAfter, registeredBefore)
        );
        QAirplane root = QAirplane.airplane;
        booleanBuilder.and(root.status.eq(AirplaneStatus.ON_REFUELING));
        if (AppUserRoleUtils.checkIfUserRolesListContainsSuchUserRoleTitle(
                authorizedUser.getAppRoles(),
                "ENGINEER"
        )) {
            booleanBuilder.and(root.servicedBy.id.eq(authorizedUser.getId()));
        }
        return this.findAirplanesByBuiltPredicate(booleanBuilder.getValue());
    }

    @Override
    public AirplaneTypesResponseDto getAllAirplaneTypes() {
        return AirplaneMapper.mapToAirplaneTypesResponseDto(List.of(AirplaneType.values()));
    }

    private Predicate buildCommonAirplanesSearchPredicate(
            AirplaneType airplaneType,
            LocalDateTime registeredAfter,
            LocalDateTime registeredBefore
    )
            throws IncorrectFiltersException
    {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QAirplane root = QAirplane.airplane;

        if (Objects.nonNull(airplaneType)) {
            booleanBuilder.and(root.marka.eq(airplaneType));
        }
        boolean registeredAfterIsNonNull = Objects.nonNull(registeredAfter);
        if (registeredAfterIsNonNull) {
            booleanBuilder.and(root.dateRegister.goe(registeredAfter));
        }
        if (Objects.nonNull(registeredBefore)) {
            if(registeredAfterIsNonNull && registeredAfter.isAfter(registeredBefore)) {
                throw new IncorrectFiltersException(
                        "Неверно заданы фильтры поиска по дате! Начальная дата не может быть позже конечной!"
                );
            }
            booleanBuilder.and(root.dateRegister.goe(registeredAfter));
        }

        return booleanBuilder.getValue();
    }

    private List<Airplane> findAirplanesByBuiltPredicate(Predicate builtPredicate)
            throws AirplaneNotFoundException
    {
        Iterable<Airplane> AirplaneIterable =
                this.airplaneRepository.findAll(builtPredicate);
        List<Airplane> airplaneList =
                StreamSupport
                        .stream(AirplaneIterable.spliterator(), false)
                        .collect(Collectors.toList());

        if (airplaneList.isEmpty()) {
            throw new AirplaneNotFoundException("Самолетов по заданным параметрам не найдено!");
        }
        return airplaneList;
    }

}
