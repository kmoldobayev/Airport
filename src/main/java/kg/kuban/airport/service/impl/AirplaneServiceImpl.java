package kg.kuban.airport.service.impl;

import kg.kuban.airport.dto.AirplaneRequestDto;
import kg.kuban.airport.dto.AirplaneResponseDto;
import kg.kuban.airport.dto.AirplanePartCheckupRequestDto;
import kg.kuban.airport.dto.AirplaneTypesResponseDto;
import kg.kuban.airport.entity.*;
import kg.kuban.airport.enums.AirplaneStatus;
import kg.kuban.airport.enums.AirplanePartStatus;
import kg.kuban.airport.enums.AirplaneType;
import kg.kuban.airport.exception.*;
import kg.kuban.airport.mapper.AircompanyMapper;
import kg.kuban.airport.mapper.AirplaneMapper;
import kg.kuban.airport.repository.AircompanyRepository;
import kg.kuban.airport.repository.AirplaneRepository;
import kg.kuban.airport.repository.AppUserRepository;
import kg.kuban.airport.repository.SeatRepository;
import kg.kuban.airport.service.AirplaneService;
import kg.kuban.airport.service.PartCheckupService;
import kg.kuban.airport.service.PartService;
import kg.kuban.airport.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AirplaneServiceImpl implements AirplaneService {

    private final AppUserRepository appUserRepository;
    private final AircompanyRepository aircompanyRepository;
    private final AirplaneRepository airplaneRepository;
    private final SeatRepository seatRepository;

    private final PartCheckupService partCheckupService;
    private final SeatService seatService;
    private final PartService partService;

    @Autowired
    public AirplaneServiceImpl(AppUserRepository appUserRepository, AircompanyRepository aircompanyRepository, AirplaneRepository airplaneRepository, SeatRepository seatRepository, PartCheckupService partCheckupService, SeatService seatService, PartService partService) {
        this.appUserRepository = appUserRepository;
        this.aircompanyRepository = aircompanyRepository;
        this.airplaneRepository = airplaneRepository;
        this.seatRepository = seatRepository;
        this.partCheckupService = partCheckupService;
        this.seatService = seatService;
        this.partService = partService;
    }

    @Override
    public Airplane assignAirplaneRepairs(Long airplaneId, Long userId) {
        return null;
    }

    /**
     * Регистрация нового самолета (задача Диспетчера)
     * @param airplaneRequestDto
     * @return Airplane
     */
    @Override
    @Transactional
    public Airplane registerNewAirplane(AirplaneRequestDto airplaneRequestDto)
            throws AirplanePartNotFoundException,
            IncompatiblePartException {

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
        //airplane.setDateRegister(LocalDateTime.now());

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

    @Override
    @Transactional
    public Airplane assignAirplaneCheckup(Long airplaneId, Long userId)
            throws AirplaneNotFoundException, StatusChangeException, EngineerIsBusyException
    {
        Airplane airplane = this.findAirplaneById(airplaneId);
        if (!airplane.getStatus().equals(AirplaneStatus.ON_CHECKUP)) {
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
        if (!airplane.getStatus().equals(AirplaneStatus.TO_CHECKUP) && !airplane.getStatus().equals(AirplaneStatus.ON_REPAIRS)) {
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

    @Override
    @Transactional
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

    @Override
    public Airplane confirmAirplaneServiceAbility(Long airplaneId)
            throws AirplaneNotFoundException, AirplanePartCheckupNotFoundException, StatusChangeException {

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

        airplane = this.airplaneRepository.save(airplane);
        return airplane;
    }

//    @Override
//    public Airplane assignAirplaneRepairs(Airplane airplane, AppUser appUser) {
//
//        airplane.setServicedBy(appUser);
//
//        return airplane;
//    }
    @Override
    public Airplane sendAirplaneToRegistrationConfirmation(Long AirplaneId) throws AirplaneNotFoundException, StatusChangeException {
        return null;
    }

    @Override
    public Airplane confirmAirplaneRegistration(Long AirplaneId) throws AirplaneNotFoundException, StatusChangeException {
        return null;
    }

    @Override
    public Airplane refuelAirplane(Long AirplaneId) throws AirplaneNotFoundException, StatusChangeException, EngineerIsBusyException {
        return null;
    }

    @Override
    public Airplane assignAirplaneRefueling(Long AirplaneId, Long engineerId) throws AirplaneNotFoundException, StatusChangeException, AppUserNotFoundException, EngineerIsBusyException {
        return null;
    }

    @Override
    public List<AirplaneResponseDto> getAllAirplanes(AirplaneType AirplaneType, AirplaneStatus AirplaneStatus, LocalDateTime registeredBefore, LocalDateTime registeredAfter) throws IncorrectFiltersException, AirplaneNotFoundException {
        return null;
    }

    @Override
    public List<AirplaneResponseDto> getAirplanesForRepairs(AirplaneType AirplaneType, LocalDateTime registeredBefore, LocalDateTime registeredAfter) throws IncorrectFiltersException, AirplaneNotFoundException {
        return null;
    }

    @Override
    public List<AirplaneResponseDto> getNewAirplanes(AirplaneType airplaneType, LocalDateTime registeredBefore, LocalDateTime registeredAfter) throws IncorrectFiltersException, AirplaneNotFoundException {
        return null;
    }

    @Override
    public List<AirplaneResponseDto> getAirplanesForRefueling(AirplaneType AirplaneType, LocalDateTime registeredBefore, LocalDateTime registeredAfter) throws IncorrectFiltersException, AirplaneNotFoundException {
        return null;
    }

    @Override
    public AirplaneTypesResponseDto getAllAirplaneTypes() {
        return AirplaneMapper.mapToAirplaneTypesResponseDto(List.of(AirplaneType.values()));
    }

}
