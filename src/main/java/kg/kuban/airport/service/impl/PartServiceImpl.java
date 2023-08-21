package kg.kuban.airport.service.impl;

import com.querydsl.core.BooleanBuilder;
import kg.kuban.airport.dto.AirplanePartRequestDto;
import kg.kuban.airport.dto.AirplanePartResponseDto;
import kg.kuban.airport.dto.AirplanePartTypesResponseDto;
import kg.kuban.airport.entity.AirplanePart;
import kg.kuban.airport.entity.QAirplanePart;
import kg.kuban.airport.enums.AirplanePartType;
import kg.kuban.airport.enums.AirplaneType;
import kg.kuban.airport.exception.IncompatiblePartException;
import kg.kuban.airport.exception.AirplanePartNotFoundException;
import kg.kuban.airport.mapper.AirplanePartMapper;
import kg.kuban.airport.repository.AirplanePartRepository;
import kg.kuban.airport.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PartServiceImpl implements PartService {

    private final AirplanePartRepository airplanePartRepository;

    @Autowired
    public PartServiceImpl(AirplanePartRepository airplanePartRepository) {
        this.airplanePartRepository = airplanePartRepository;
    }

    @Override
    public AirplanePartResponseDto registerNewPart(AirplanePartRequestDto requestDto) {
        AirplanePart airplanePart = AirplanePartMapper.mapAirplanePartRequestDtoToEntity(requestDto);
        airplanePart = this.airplanePartRepository.save(airplanePart);
        return AirplanePartMapper.mapToAirplanePartResponseDto(airplanePart);
    }

    @Override
    public List<AirplanePartResponseDto> registerNewParts(List<AirplanePartRequestDto> partRequestDtoList) {
        if(partRequestDtoList.isEmpty()) {
            throw new IllegalArgumentException("Список создаваемых деталей не может быть пустым!");
        }

        List<AirplanePart> partsEntities = new ArrayList<>();
        for (AirplanePartRequestDto partRequestDto : partRequestDtoList) {
            // TODO: 30.07.2023 Здесь валидировать дто
            partsEntities.add(AirplanePartMapper.mapAirplanePartRequestDtoToEntity(partRequestDto));
        }
        this.airplanePartRepository.saveAll(partsEntities);
        return AirplanePartMapper.mapToAirplanePartResponseDtoList(partsEntities);
    }

    @Override
    public List<AirplanePart> getPartByPartsIdListAndAirplaneType(List<Long> partsIdList, AirplaneType airplaneType)
            throws AirplanePartNotFoundException, IncompatiblePartException {
        if (Objects.isNull(partsIdList) || partsIdList.isEmpty()) {
            throw new IllegalArgumentException("Список ID деталей не может быть null или пустым!");
        }
        if (Objects.isNull(airplaneType)) {
            throw new IllegalArgumentException("Тип самолета не может быть null!");
        }
        for (Long partId : partsIdList) {
            if (partId < 1) {
                throw new IllegalArgumentException("ID детали не может быть меньше 1!");
            }
        }

        List<AirplanePart> airplaneParts = this.airplanePartRepository.getPartsByIdIn(partsIdList);
        if (airplaneParts.isEmpty()) {
            throw new AirplanePartNotFoundException("Деталей по заданным ID не найдено!");
        }

        for (AirplanePart part : airplaneParts) {
            if (!part.getAirplaneType().equals(airplaneType)) {
                throw new IncompatiblePartException(
                        String.format(
                                "Деталь %s [%s] не подходит к самолетам типа %s!",
                                part.getTitle(),
                                part.getPartType(),
                                airplaneType.toString()
                        )
                );
            }
        }
        return airplaneParts;
    }

    @Override
    public List<AirplanePart> getPartsByPartsIdListAndAirplaneId(List<Long> partsIdList, Long airplaneId)
            throws AirplanePartNotFoundException {
        if (Objects.isNull(partsIdList) || partsIdList.isEmpty()) {
            throw new IllegalArgumentException("Список ID деталей не может быть null или пустым!");
        }
        if (Objects.isNull(airplaneId)) {
            throw new IllegalArgumentException("ID самолета не может быть null!");
        }
        if (airplaneId < 1L) {
            throw new IllegalArgumentException("ID самолета не может быть меньше 1!");
        }
        for (Long partId : partsIdList) {
            if(partId < 1) {
                throw new IllegalArgumentException("ID детали не может быть меньше 1!");
            }
        }

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QAirplanePart root = QAirplanePart.airplanePart;

        booleanBuilder.and(root.id.in(partsIdList));
        booleanBuilder.and(root.airplanes.any().id.eq(airplaneId));

        Iterable<AirplanePart> partsEntityIterable =
                this.airplanePartRepository.findAll(booleanBuilder.getValue());
        List<AirplanePart> partsEntityList =
                StreamSupport
                        .stream(partsEntityIterable.spliterator(), false)
                        .collect(Collectors.toList());

        if (partsEntityList.isEmpty()) {
            throw new AirplanePartNotFoundException("Деталей самолета по заданным ID деталей и ID самолета не найдено!");
        }
        return partsEntityList;
    }

    @Override
    public AirplanePartTypesResponseDto getAllPartTypes() {

        return AirplanePartMapper.mapToAirplanePartTypesResponseDto(List.of(AirplanePartType.values()));
    }
}
