package kg.kuban.airport.dto;

import kg.kuban.airport.enums.AirplaneType;

import java.util.List;

public class AirplaneTypesResponseDto {
    private List<AirplaneType> aircraftTypes;

    public AirplaneTypesResponseDto() {
    }

    public List<AirplaneType> getAircraftTypes() {
        return aircraftTypes;
    }

    public AirplaneTypesResponseDto setAircraftTypes(List<AirplaneType> aircraftTypes) {
        this.aircraftTypes = aircraftTypes;
        return this;
    }
}
