package kg.kuban.airport.service;

import kg.kuban.airport.dto.AirplaneRequestDto;
import kg.kuban.airport.dto.FlightRequestDto;
import kg.kuban.airport.entity.Airplane;
import kg.kuban.airport.entity.AppUser;
import kg.kuban.airport.entity.Flight;
import kg.kuban.airport.exception.AirplaneNotFoundException;
import kg.kuban.airport.exception.EngineerException;
import kg.kuban.airport.exception.PartInspectionNotFoundException;
import kg.kuban.airport.exception.StatusChangeException;

public interface AirplaneService{
    Airplane registerNewAirplane(AirplaneRequestDto airplaneRequestDto);
    Airplane assignInspection(Long airplaneId, Long userId) throws AirplaneNotFoundException, StatusChangeException, EngineerException;
    Airplane registerNewInspection(Airplane airplane, AppUser appUser);
    Airplane confirmAirplaneServiceAbility(Long airplaneId) throws AirplaneNotFoundException, PartInspectionNotFoundException, StatusChangeException ;
    Airplane assignAirplaneRepairs(Airplane airplane, AppUser appUser);
}
