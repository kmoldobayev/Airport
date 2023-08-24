package kg.kuban.airport.service;

import kg.kuban.airport.dto.AirplaneRequestDto;
import kg.kuban.airport.dto.AirplaneResponseDto;
import kg.kuban.airport.dto.AirplanePartCheckupRequestDto;
import kg.kuban.airport.dto.AirplaneTypesResponseDto;
import kg.kuban.airport.entity.Airplane;
import kg.kuban.airport.entity.AirplanePartCheckup;
import kg.kuban.airport.enums.AirplaneStatus;
import kg.kuban.airport.enums.AirplaneType;
import kg.kuban.airport.exception.*;

import java.time.LocalDateTime;
import java.util.List;

public interface AirplaneService{
    Airplane registerNewAirplane(AirplaneRequestDto airplaneRequestDto)
            throws AirplanePartNotFoundException, IncompatiblePartException;
    Airplane assignAirplaneCheckup(Long airplaneId, Long userId)
            throws AirplaneNotFoundException, StatusChangeException, EngineerIsBusyException;

    List<AirplanePartCheckup> checkupAirplane(
            Long AirplaneId,
            List<AirplanePartCheckupRequestDto> partInspectionsRequestDtoList
    )
            throws AirplaneNotFoundException,
            StatusChangeException,
            WrongEngineerException,
            AirplaneIsNotOnServiceException,
            AirplanePartNotFoundException,
            IllegalAirplaneException,
            IncompatiblePartException;

    Airplane sendAirplaneToRegistrationConfirmation(Long AirplaneId)
            throws AirplaneNotFoundException,
            StatusChangeException;


    Airplane confirmAirplaneRegistration(Long AirplaneId)
            throws AirplaneNotFoundException,
            StatusChangeException;

    Airplane refuelAirplane(Long AirplaneId)
            throws
            AirplaneNotFoundException,
            StatusChangeException,
            EngineerIsBusyException;



    Airplane assignAirplaneRefueling(Long AirplaneId, Long engineerId)
            throws AirplaneNotFoundException,
            StatusChangeException,
            AppUserNotFoundException,
            EngineerIsBusyException;



    List<Airplane> getAllAirplanes(
            AirplaneType AirplaneType,
            AirplaneStatus AirplaneStatus,
            LocalDateTime registeredBefore,
            LocalDateTime registeredAfter
    )
            throws IncorrectFiltersException,
            AirplaneNotFoundException;

    List<Airplane> getAirplanesForRepairs(
            AirplaneType AirplaneType,
            LocalDateTime registeredBefore,
            LocalDateTime registeredAfter
    )
            throws IncorrectFiltersException,
            AirplaneNotFoundException;

    Boolean deleteNewAirplane(Long airplaneId) throws IllegalArgumentException, AirplaneNotFoundException, AirplaneSeatNotFoundException;
    //Airplane registerNewInspection(Airplane airplane, AppUser appUser);
    Airplane confirmAirplaneServiceAbility(Long airplaneId) throws AirplaneNotFoundException, AirplanePartCheckupNotFoundException, StatusChangeException ;
    Airplane assignAirplaneRepairs(Long airplaneId, Long userId)
            throws EngineerIsBusyException,
            StatusChangeException,
            AppUserNotFoundException,
            AirplaneNotFoundException,
            AirplanePartCheckupNotFoundException;

    List<Airplane> getNewAirplanes(
            AirplaneType airplaneType,
            LocalDateTime registeredBefore,
            LocalDateTime registeredAfter
    )
            throws IncorrectFiltersException,
            AirplaneNotFoundException;

    List<Airplane> getAirplanesForRefueling(
            AirplaneType AirplaneType,
            LocalDateTime registeredBefore,
            LocalDateTime registeredAfter
    )
            throws IncorrectFiltersException,
            AirplaneNotFoundException;

    Airplane findAirplaneById(Long airplaneId) throws AirplaneNotFoundException;

    AirplaneTypesResponseDto getAllAirplaneTypes();
}
