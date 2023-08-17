package kg.kuban.airport.service.impl;

import kg.kuban.airport.dto.PartRequestDto;
import kg.kuban.airport.dto.PartResponseDto;
import kg.kuban.airport.dto.PartTypesResponseDto;
import kg.kuban.airport.entity.AirplanePart;
import kg.kuban.airport.enums.AirplaneType;
import kg.kuban.airport.exception.IncompatiblePartException;
import kg.kuban.airport.exception.PartNotFoundException;
import kg.kuban.airport.service.PartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartServiceImpl implements PartService {

    @Override
    public PartResponseDto registerNewPart(PartRequestDto requestDto) {
        return null;
    }

    @Override
    public List<PartResponseDto> registerNewParts(List<PartRequestDto> partRequestDtoList) {
        return null;
    }

    @Override
    public List<AirplanePart> getPartByPartsIdListAndAirplaneType(List<Long> partsIdList, AirplaneType airplaneType) throws PartNotFoundException, IncompatiblePartException {
        return null;
    }

    @Override
    public List<AirplanePart> getPartsByPartsIdListAndAirplaneId(List<Long> partsIdList, Long airplaneId) throws PartNotFoundException {
        return null;
    }

    @Override
    public PartTypesResponseDto getAllPartTypes() {
        return null;
    }
}
