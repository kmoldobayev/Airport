package kg.kuban.airport.dto;

import kg.kuban.airport.enums.AirplanePartType;
import kg.kuban.airport.enums.AirplaneType;

public class AirplanePartRequestDto {
    private String title;
    private AirplaneType airplaneType;
    private AirplanePartType airplanePartType;

    public AirplanePartRequestDto() {
    }

    public String getTitle() {
        return title;
    }

    public AirplanePartRequestDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public AirplaneType getAirplaneType() {
        return airplaneType;
    }

    public AirplanePartRequestDto setAirplaneType(AirplaneType airplaneType) {
        this.airplaneType = airplaneType;
        return this;
    }

    public AirplanePartType getAirplanePartType() {
        return airplanePartType;
    }

    public AirplanePartRequestDto setAirplanePartType(AirplanePartType airplanePartType) {
        this.airplanePartType = airplanePartType;
        return this;
    }
}
