package kg.kuban.airport.service.impl;

import kg.kuban.airport.dto.AirplaneRequestDto;
import kg.kuban.airport.entity.*;
import kg.kuban.airport.enums.AirplaneStatus;
import kg.kuban.airport.enums.AirplanePartStatus;
import kg.kuban.airport.exception.*;
import kg.kuban.airport.mapper.AircompanyMapper;
import kg.kuban.airport.repository.AircompanyRepository;
import kg.kuban.airport.repository.AirplaneRepository;
import kg.kuban.airport.repository.AppUserRepository;
import kg.kuban.airport.repository.SeatRepository;
import kg.kuban.airport.service.AirplaneService;
import kg.kuban.airport.service.PartInspectionService;
import kg.kuban.airport.service.PartService;
import kg.kuban.airport.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final PartInspectionService partInspectionService;
    private final SeatService seatService;
    private final PartService partService;

    @Autowired
    public AirplaneServiceImpl(AppUserRepository appUserRepository, AircompanyRepository aircompanyRepository, AirplaneRepository airplaneRepository, SeatRepository seatRepository, PartInspectionService partInspectionService, SeatService seatService, PartService partService) {
        this.appUserRepository = appUserRepository;
        this.aircompanyRepository = aircompanyRepository;
        this.airplaneRepository = airplaneRepository;
        this.seatRepository = seatRepository;
        this.partInspectionService = partInspectionService;
        this.seatService = seatService;
        this.partService = partService;
    }

    /**
     * Регистрация нового самолета (задача Диспетчера)
     * @param airplaneRequestDto
     * @return
     */
    @Override
    @Transactional
    public Airplane registerNewAirplane(AirplaneRequestDto airplaneRequestDto)
            throws PartNotFoundException,
            IncompatiblePartException
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
        //airplane.setDateRegister(LocalDateTime.now());

        // Генерация мест в самолете в зависимости от макс количества вместимости самолета
        List<Seat> airplaneSeats = this.seatService.generateSeats(airplaneRequestDto.getNumberSeats());
        airplane.setAirplaneSeats(airplaneSeats);
        for (Seat airplaneSeat : airplaneSeats) {
            airplaneSeat.setAirplane(airplane);
        }
//        // Создание частей самолета по техосмотру в зависимости от марки самолета
//        List<AirplanePart> airplaneParts = this.partService.getPartByPartsIdListAndAirplaneType(
//                airplaneRequestDto.getPartIdList(),
//                airplane.getMarka()
//        );
//        airplane.setParts(airplaneParts);
//        for (AirplanePart part : airplaneParts) {
//            part.getAirplanes().add(airplane);
//        }


        this.airplaneRepository.save(airplane);

        return airplane;
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

    /**
     * Выдача на осмотр инженеру (задача Главного инженера )
     * Главный инженер записывает в поле user_id ссылку на инженера
     * @param airplaneId
     * @param userId
     * @return
     */

    @Override
    @Transactional
    public Airplane assignInspection(Long airplaneId, Long userId) throws AirplaneNotFoundException, StatusChangeException, EngineerException {
        Airplane airplane = this.findAirplaneById(airplaneId);
        if (!airplane.getStatus().equals(AirplaneStatus.ON_CHECKUP)) {
            throw new StatusChangeException(
                    "Для назначения техосмотра самолет должен быть передан на техосмотр диспетчером!"
            );
        }

        AppUser engineer = this.appUserRepository.getReferenceById(userId);
        if(Objects.nonNull(engineer.getServicedAirplane())) {
            throw new EngineerException(
                    String.format(
                            "Невозможно назначить инженера с ID[%d] на техосмотр." +
                                    " В данный момент инженер обслуживает другой самолет!",
                            userId
                    )
            );
        }

        airplane.setServicedBy(engineer);

        return airplane;
    }

    /**
     * Составляет технический осмотр деталей
     * @param airplane
     * @param appUser
     * @return
     */
    @Override
    public Airplane registerNewInspection(Airplane airplane, AppUser appUser) {

        airplane.setServicedBy(appUser);

        return airplane;
    }

    @Override
    public Airplane confirmAirplaneServiceAbility(Long airplaneId) throws AirplaneNotFoundException, PartInspectionNotFoundException, StatusChangeException {

        Airplane airplane = this.findAirplaneById(airplaneId);
        if (!airplane.getStatus().equals(AirplaneStatus.INSPECTED)) {
            throw new StatusChangeException(
                    "Чтобы подтвердить исправность самолета самолет должен быть осмотрен инженером!"
            );
        }

        if (!this.partInspectionService.getLastAirplaneInspectionResult(airplaneId).equals(AirplanePartStatus.OK)) {
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

    @Override
    public Airplane assignAirplaneRepairs(Airplane airplane, AppUser appUser) {

        airplane.setServicedBy(appUser);

        return airplane;
    }
}
