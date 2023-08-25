package kg.kuban.airport.service;

import kg.kuban.airport.dto.AirplanePartCheckupRequestDto;
import kg.kuban.airport.dto.AirplanePartStatusResponseDto;
import kg.kuban.airport.entity.Airplane;
import kg.kuban.airport.entity.AirplanePartCheckup;
import kg.kuban.airport.enums.AirplanePartStatus;
import kg.kuban.airport.exception.*;

import java.util.List;

public interface AirplanePartCheckupService {

    List<AirplanePartCheckup> registerPartCheckups(
            Airplane airplane,
            List<AirplanePartCheckupRequestDto> requestDtoList
    )
            throws AirplanePartNotFoundException,
                    AirplaneNotFoundException,
                    IncompatiblePartException,
                    AirplaneIsNotOnServiceException,
                    IllegalAirplaneException;

    List<AirplanePartCheckup> getPartCheckupsHistory(Long airplaneId)
            throws AirplanePartCheckupNotFoundException;

    List<AirplanePartCheckup> getLastAirplaneCheckups(Long airplaneId)
            throws AirplanePartCheckupNotFoundException;

    AirplanePartStatus getLastAirplaneCheckupResult(Long airplaneId)
            throws AirplanePartCheckupNotFoundException;

    AirplanePartStatusResponseDto getAllPartStates();
}
