package kg.kuban.airport.service;

import kg.kuban.airport.dto.PartRequestDto;
import kg.kuban.airport.dto.PartResponseDto;
import kg.kuban.airport.dto.PartTypesResponseDto;
import kg.kuban.airport.entity.Part;
import kg.kuban.airport.enums.AirplaneType;
import kg.kuban.airport.exception.IncompatiblePartException;
import kg.kuban.airport.exception.PartNotFoundException;
import java.util.List;

public interface PartService {
    PartResponseDto registerNewPart(PartRequestDto requestDto);

    List<PartResponseDto> registerNewParts(List<PartRequestDto> partRequestDtoList);

    List<Part> getPartByPartsIdListAndAirplaneType(
            List<Long> partsIdList,
            AirplaneType airplaneType
    ) throws PartNotFoundException, IncompatiblePartException;

    List<Part> getPartsByPartsIdListAndAirplaneId(List<Long> partsIdList, Long airplaneId) throws PartNotFoundException;

    PartTypesResponseDto getAllPartTypes();
}
