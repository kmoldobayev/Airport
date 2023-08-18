package kg.kuban.airport.service.impl;

import kg.kuban.airport.dto.PartInspectionsRequestDto;
import kg.kuban.airport.dto.PartInspectionsResponseDto;
import kg.kuban.airport.dto.PartStatesResponseDto;
import kg.kuban.airport.entity.Airplane;
import kg.kuban.airport.entity.AirplanePart;
import kg.kuban.airport.entity.AirplanePartInspection;
import kg.kuban.airport.enums.AirplanePartStatus;
import kg.kuban.airport.exception.*;
import kg.kuban.airport.mapper.InspectionMapper;
import kg.kuban.airport.repository.AirplanePartInspectionRepository;
import kg.kuban.airport.service.PartInspectionService;
import kg.kuban.airport.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PartInspectionServiceImpl implements PartInspectionService {
    private static final Long MIN_INSPECTION_CODE_VALUE = 1L;

    private final AirplanePartInspectionRepository partInspectionRepository;
    private final PartService partsService;

    private Long currentMaxInspectionCode;

    @Autowired
    public PartInspectionServiceImpl(
            AirplanePartInspectionRepository partInspectionRepository,
            PartService partService
    ) {
        this.partInspectionRepository = partInspectionRepository;
        this.partsService = partService;
    }

    @PostConstruct
    private void init() {
        this.currentMaxInspectionCode = this.partInspectionRepository.getCurrentMaxInspectionCode();
        if(Objects.isNull(this.currentMaxInspectionCode)) {
            this.currentMaxInspectionCode = MIN_INSPECTION_CODE_VALUE;
        }
    }

    @Override
    public List<PartInspectionsResponseDto> registerPartInspections(
            Airplane airplane,
            List<PartInspectionsRequestDto> requestDtoList
    )
            throws  PartNotFoundException,
                    IncompatiblePartException,
                    AirplaneIsNotOnServiceException,
                    IllegalAirplaneException {
        if(Objects.isNull(requestDtoList)) {
            throw new IllegalArgumentException("Список создаваемых осмотров деталей не может быть null!");
        }

        List<AirplanePartInspection> partInspectionsEntities = new ArrayList<>();
        List<Long> partIdList = new ArrayList<>();
        Long airplaneId = requestDtoList.get(0).getAirplaneId();

        for (PartInspectionsRequestDto requestDto : requestDtoList) {
            Long requestDtoAirplaneId = requestDto.getAirplaneId();
            if (!airplaneId.equals(requestDtoAirplaneId)) {
                throw new IllegalAirplaneException(
                        "Недопустимый ID самолета! ID самолета для всех деталей техосмотра должен быть одинаковым"
                );
            }
            partInspectionsEntities.add(InspectionMapper.mapPartInspectionsRequestDtoToEntity(requestDto));
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
//            partInspectionsEntities.get(i)
//                    .setPart(part)
//                    .setDateRegister(localDateTimeNow)
//                    .setAirplane(airplane)
//                    .setAppUser(airplane.getServicedBy());
//        }

        this.currentMaxInspectionCode += 1L;
        for (AirplanePartInspection inspection : partInspectionsEntities) {
            inspection.setInspectionCode(this.currentMaxInspectionCode);
        }

        partInspectionsEntities = this.partInspectionRepository.saveAll(partInspectionsEntities);
        return InspectionMapper.mapToPartInspectionsResponseDtoList(partInspectionsEntities);
    }

    @Override
    public List<PartInspectionsResponseDto> getPartInspectionsHistory(Long airplaneId) throws PartInspectionNotFoundException
    {
//        if(Objects.isNull(airplaneId)) {
//            throw new IllegalArgumentException("ID самолета не может быть null!");
//        }
//
//        BooleanBuilder booleanBuilder = new BooleanBuilder();
//        QPartInspection root = QPartInspection.partInspection;
//
//        booleanBuilder.and(root.airplane.id.eq(airplaneId));
//
//        Iterable<PartInspection> partInspectionsIterable =
//                this.partInspectionRepository.findAll(booleanBuilder.getValue());
//        List<PartInspectionsResponseDto> partInspectionsResponseDtoList =
//                StreamSupport
//                        .stream(partInspectionsIterable.spliterator(), false)
//                        .map(InspectionMapper::mapToPartInspectionsResponseDto)
//                        .collect(Collectors.toList());
//
//        if(partInspectionsResponseDtoList.isEmpty()) {
//            throw new PartInspectionsNotFoundException(
//                    String.format(
//                            "По ID[%d] самолета технических осмотров не найдено!",
//                            airplaneId
//                    )
//            );
//        }
//        return partInspectionsResponseDtoList;
        return null;
    }

    @Override
    public List<AirplanePartInspection> getLastAirplaneInspections(Long airplaneId) throws PartInspectionNotFoundException {
        if (Objects.isNull(airplaneId)) {
            throw new IllegalArgumentException("ID самолета не может быть null!");
        }

        List<AirplanePartInspection> lastInspection =
                this.partInspectionRepository.getLastAirplaneInspectionByAirplaneId(airplaneId);
        if(lastInspection.isEmpty()) {
            throw new PartInspectionNotFoundException(
                    String.format("Для самолета с ID[%d] не найдено ни одного техосмотра!", airplaneId)
            );
        }
        return lastInspection;
    }

    @Override
    public AirplanePartStatus getLastAirplaneInspectionResult(Long airplaneId)
            throws PartInspectionNotFoundException
    {
        List<AirplanePartInspection> partInspectionsEntityList =
                this.getLastAirplaneInspections(airplaneId);

        for (AirplanePartInspection partInspection : partInspectionsEntityList) {
            if(partInspection.getStatus().equals(AirplanePartStatus.MAINTENANCE)) {
                return AirplanePartStatus.MAINTENANCE;
            }
        }
        return AirplanePartStatus.OK;
    }

    @Override
    public PartStatesResponseDto getAllPartStates() {
        return InspectionMapper.mapToPartStatesResponseDto(List.of(AirplanePartStatus.values()));
    }
}
