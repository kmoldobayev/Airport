package kg.kuban.airport.service;

import kg.kuban.airport.dto.AirplanePartCheckupRequestDto;
import kg.kuban.airport.dto.AirplanePartCheckupResponseDto;
import kg.kuban.airport.dto.AirplanePartStatusResponseDto;
import kg.kuban.airport.dto.PartStatesResponseDto;
import kg.kuban.airport.entity.Airplane;
import kg.kuban.airport.entity.AirplanePartCheckup;
import kg.kuban.airport.enums.AirplanePartStatus;
import kg.kuban.airport.exception.*;

import java.util.List;

public interface PartCheckupService {

    List<AirplanePartCheckup> registerPartCheckups(
            Airplane airplane,
            List<AirplanePartCheckupRequestDto> requestDtoList
    )
            throws  PartNotFoundException,
                    AirplaneNotFoundException,
                    IncompatiblePartException,
                    AirplaneIsNotOnServiceException,
                    IllegalAirplaneException;

    List<AirplanePartCheckup> getPartCheckupsHistory(Long airplaneId)
            throws PartCheckupNotFoundException;

    List<AirplanePartCheckup> getLastAirplaneCheckups(Long airplaneId)
            throws PartCheckupNotFoundException;

    AirplanePartStatus getLastAirplaneCheckupResult(Long airplaneId)
            throws PartCheckupNotFoundException;

    AirplanePartStatusResponseDto getAllPartStates();
}
