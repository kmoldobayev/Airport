package kg.kuban.airport.service;

import kg.kuban.airport.dto.AirplanePartRequestDto;
import kg.kuban.airport.dto.AirplanePartResponseDto;
import kg.kuban.airport.dto.AirplanePartTypesResponseDto;
import kg.kuban.airport.entity.AirplanePart;
import kg.kuban.airport.enums.AirplaneType;
import kg.kuban.airport.exception.IncompatiblePartException;
import kg.kuban.airport.exception.AirplanePartNotFoundException;
import java.util.List;

public interface PartService {
    AirplanePartResponseDto registerNewPart(AirplanePartRequestDto requestDto);

    List<AirplanePartResponseDto> registerNewParts(List<AirplanePartRequestDto> partRequestDtoList);

    List<AirplanePart> getPartByPartsIdListAndAirplaneType(
            List<Long> partsIdList,
            AirplaneType airplaneType
    ) throws AirplanePartNotFoundException, IncompatiblePartException;

    List<AirplanePart> getPartsByPartsIdListAndAirplaneId(List<Long> partsIdList, Long airplaneId) throws AirplanePartNotFoundException;

    AirplanePartTypesResponseDto getAllPartTypes();
}
