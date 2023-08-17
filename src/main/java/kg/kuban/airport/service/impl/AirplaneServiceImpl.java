package kg.kuban.airport.service.impl;

import kg.kuban.airport.dto.AirplaneRequestDto;
import kg.kuban.airport.entity.Aircompany;
import kg.kuban.airport.entity.Airplane;
import kg.kuban.airport.entity.AppUser;
import kg.kuban.airport.enums.AirplaneStatus;
import kg.kuban.airport.enums.AirplanePartStatus;
import kg.kuban.airport.exception.AirplaneNotFoundException;
import kg.kuban.airport.exception.EngineerException;
import kg.kuban.airport.exception.PartInspectionNotFoundException;
import kg.kuban.airport.exception.StatusChangeException;
import kg.kuban.airport.repository.AircompanyRepository;
import kg.kuban.airport.repository.AirplaneRepository;
import kg.kuban.airport.repository.AppUserRepository;
import kg.kuban.airport.service.AirplaneService;
import kg.kuban.airport.service.PartInspectionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class AirplaneServiceImpl implements AirplaneService {

    private AppUserRepository appUserRepository;
    private AircompanyRepository aircompanyRepository;
    private AirplaneRepository airplaneRepository;

    private PartInspectionService partInspectionService;
    /**
     * Регистрация нового самолета (задача Диспетчера)
     * @param airplaneRequestDto
     * @return
     */
    @Override
    @Transactional
    public Airplane registerNewAirplane(AirplaneRequestDto airplaneRequestDto) {

        if (Objects.isNull(airplaneRequestDto)) {
            throw new IllegalArgumentException("Реквизиты самолета пустые! Заполните!");
        }

        Aircompany existingAircompany = this.aircompanyRepository.findByTitle(airplaneRequestDto.getAircompany().getTitle());


        Airplane airplane = new Airplane();

        airplane.setMarka(airplaneRequestDto.getMarka());
        airplane.setBoardNumber(airplaneRequestDto.getBoardNumber());
        airplane.setAirCompany(existingAircompany);

        airplane.setNumberSeats(airplaneRequestDto.getNumberSeats());
        airplane.setStatus(AirplaneStatus.TO_CHECKUP);
        airplane.setServicedBy(null);
        airplane.setDateRegister(LocalDateTime.now());

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
