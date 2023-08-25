package kg.kuban.airport.service.impl;

import kg.kuban.airport.dto.AirplanePartCheckupRequestDto;
import kg.kuban.airport.dto.AirplanePartStatusResponseDto;
import kg.kuban.airport.entity.Airplane;
import kg.kuban.airport.entity.AirplanePart;
import kg.kuban.airport.entity.AirplanePartCheckup;
import kg.kuban.airport.enums.AirplanePartStatus;
import kg.kuban.airport.exception.*;
import kg.kuban.airport.mapper.AirplanePartCheckupMapper;
import kg.kuban.airport.repository.AirplanePartCheckupRepository;
import kg.kuban.airport.service.AirplanePartCheckupService;
import kg.kuban.airport.service.AirplanePartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AirplanePartCheckupServiceImpl implements AirplanePartCheckupService {
    private static final Long MIN_Checkup_CODE_VALUE = 1L;

    private final AirplanePartCheckupRepository airplanePartCheckupRepository;
    private final AirplanePartService partsService;

    private Long currentMaxCheckupCode;

    @Autowired
    public AirplanePartCheckupServiceImpl(AirplanePartCheckupRepository airplanePartCheckupRepository, AirplanePartService partsService) {
        this.airplanePartCheckupRepository = airplanePartCheckupRepository;
        this.partsService = partsService;
    }

    @PostConstruct
    private void init() {
        this.currentMaxCheckupCode = this.airplanePartCheckupRepository.getCurrentMaxCheckupCode();
        if(Objects.isNull(this.currentMaxCheckupCode)) {
            this.currentMaxCheckupCode = MIN_Checkup_CODE_VALUE;
        }
    }

    @Override
    public List<AirplanePartCheckup> registerPartCheckups(
            Airplane airplane,
            List<AirplanePartCheckupRequestDto> requestDtoList
    )
            throws AirplanePartNotFoundException,
                    IncompatiblePartException,
                    AirplaneIsNotOnServiceException,
                    IllegalAirplaneException {
        if(Objects.isNull(requestDtoList)) {
            throw new IllegalArgumentException("Список создаваемых осмотров деталей не может быть null!");
        }

        List<AirplanePartCheckup> partCheckupsEntities = new ArrayList<>();
        List<Long> partIdList = new ArrayList<>();
        Long airplaneId = requestDtoList.get(0).getAirplaneId();

        for (AirplanePartCheckupRequestDto requestDto : requestDtoList) {
            Long requestDtoAirplaneId = requestDto.getAirplaneId();
            if (!airplaneId.equals(requestDtoAirplaneId)) {
                throw new IllegalAirplaneException(
                        "Недопустимый ID самолета! ID самолета для всех деталей техосмотра должен быть одинаковым"
                );
            }
            partCheckupsEntities.add(AirplanePartCheckupMapper.mapAirplanePartCheckupRequestDtoToEntity(requestDto));
            partIdList.add(requestDto.getPartId());
        }
        if (!airplane.getId().equals(airplaneId)) {
            throw new IllegalAirplaneException(
                    String.format("Ошибка! Обслуживание было назначено для другого самолета с ID[%d].", airplaneId)
            );
        }

        List<AirplanePart> partsEntities =
                this.partsService.getPartsByPartsIdListAndAirplaneId(partIdList, airplaneId);

        if (Objects.isNull(airplane.getServicedBy())){
            throw new AirplaneIsNotOnServiceException(
                    String.format(
                            "Для обслуживания самолета с ID[%d] не было назначено ни одного инженера!",
                            airplaneId
                    )
            );
        }

//        LocalDateTime localDateTimeNow = LocalDateTime.now();
//        for (int i = 0; i < partsEntities.size(); i++) {
//            Part part = partsEntities.get(i);
//            if (!part.getAircraftType().equals(airplane.getMarka())) {
//                throw new IncompatiblePartException(
//                        String.format(
//                                "Деталь %s [%s] не подходит к самолетам типа %s!",
//                                part.getTitle(),
//                                part.getPartType(),
//                                airplane.getMarka().toString()
//                        )
//                );
//            }
//
//            partCheckupsEntities.get(i)
//                    .setPart(part)
//                    .setDateRegister(localDateTimeNow)
//                    .setAirplane(airplane)
//                    .setAppUser(airplane.getServicedBy());
//        }

        this.currentMaxCheckupCode += 1L;
        for (AirplanePartCheckup airplanePartCheckup : partCheckupsEntities) {
            airplanePartCheckup.setCheckupCode(this.currentMaxCheckupCode);
        }

        this.airplanePartCheckupRepository.saveAll(partCheckupsEntities);
        return partCheckupsEntities;
    }

    @Override
    public List<AirplanePartCheckup> getPartCheckupsHistory(Long airplaneId) throws AirplanePartCheckupNotFoundException
    {
//        if(Objects.isNull(airplaneId)) {
//            throw new IllegalArgumentException("ID самолета не может быть null!");
//        }
//
//        BooleanBuilder booleanBuilder = new BooleanBuilder();
//        QPartCheckup root = QPartCheckup.partCheckup;
//
//        booleanBuilder.and(root.airplane.id.eq(airplaneId));
//
//        Iterable<PartCheckup> partCheckupsIterable =
//                this.partCheckupRepository.findAll(booleanBuilder.getValue());
//        List<PartCheckupsResponseDto> partCheckupsResponseDtoList =
//                StreamSupport
//                        .stream(partCheckupsIterable.spliterator(), false)
//                        .map(CheckupMapper::mapToPartCheckupsResponseDto)
//                        .collect(Collectors.toList());
//
//        if(partCheckupsResponseDtoList.isEmpty()) {
//            throw new PartCheckupsNotFoundException(
//                    String.format(
//                            "По ID[%d] самолета технических осмотров не найдено!",
//                            airplaneId
//                    )
//            );
//        }
//        return partCheckupsResponseDtoList;
        return null;
    }

    @Override
    public List<AirplanePartCheckup> getLastAirplaneCheckups(Long airplaneId) throws AirplanePartCheckupNotFoundException {
        if (Objects.isNull(airplaneId)) {
            throw new IllegalArgumentException("ID самолета не может быть null!");
        }

        List<AirplanePartCheckup> lastCheckup =
                this.airplanePartCheckupRepository.getLastAirplaneCheckupByAirplaneId(airplaneId);
        if(lastCheckup.isEmpty()) {
            throw new AirplanePartCheckupNotFoundException(
                    String.format("Для самолета с ID[%d] не найдено ни одного техосмотра!", airplaneId)
            );
        }
        return lastCheckup;
    }

    @Override
    public AirplanePartStatus getLastAirplaneCheckupResult(Long airplaneId)
            throws AirplanePartCheckupNotFoundException
    {
        List<AirplanePartCheckup> partCheckupsEntityList =
                this.getLastAirplaneCheckups(airplaneId);

        for (AirplanePartCheckup partCheckup : partCheckupsEntityList) {
            if(partCheckup.getStatus().equals(AirplanePartStatus.MAINTENANCE)) {
                return AirplanePartStatus.MAINTENANCE;
            }
        }
        return AirplanePartStatus.OK;
    }

    @Override
    public AirplanePartStatusResponseDto getAllPartStates() {
        return AirplanePartCheckupMapper.mapToPartStatesResponseDto(List.of(AirplanePartStatus.values()));
    }


}
