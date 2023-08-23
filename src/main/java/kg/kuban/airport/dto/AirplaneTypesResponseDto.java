package kg.kuban.airport.dto;

import kg.kuban.airport.enums.AirplaneType;

import java.util.List;

public class AirplaneTypesResponseDto {
    private List<AirplaneType> airplaneTypes;

    public AirplaneTypesResponseDto() {
    }

    public List<AirplaneType> getAirplaneTypes() {
        return airplaneTypes;
    }

    public AirplaneTypesResponseDto setAirplaneTypes(List<AirplaneType> airplaneTypes) {
        this.airplaneTypes = airplaneTypes;
        return this;
    }
}
