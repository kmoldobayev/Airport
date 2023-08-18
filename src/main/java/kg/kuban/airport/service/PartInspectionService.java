package kg.kuban.airport.service;

import kg.kuban.airport.dto.PartInspectionsRequestDto;
import kg.kuban.airport.dto.PartInspectionsResponseDto;
import kg.kuban.airport.dto.PartStatesResponseDto;
import kg.kuban.airport.entity.Airplane;
import kg.kuban.airport.entity.AirplanePartInspection;
import kg.kuban.airport.enums.AirplanePartStatus;
import kg.kuban.airport.exception.*;

import java.util.List;

public interface PartInspectionService {

    List<PartInspectionsResponseDto> registerPartInspections(
            Airplane airplane,
            List<PartInspectionsRequestDto> requestDtoList
    )
            throws  PartNotFoundException,
                    AirplaneNotFoundException,
                    IncompatiblePartException,
                    AirplaneIsNotOnServiceException,
                    IllegalAirplaneException;

    List<PartInspectionsResponseDto> getPartInspectionsHistory(Long airplaneId)
            throws PartInspectionNotFoundException;

    List<AirplanePartInspection> getLastAirplaneInspections(Long airplaneId)
            throws PartInspectionNotFoundException;

    AirplanePartStatus getLastAirplaneInspectionResult(Long airplaneId)
            throws PartInspectionNotFoundException;

    PartStatesResponseDto getAllPartStates();
}
