package kg.kuban.airport.dto;

import kg.kuban.airport.enums.AirplaneType;
import kg.kuban.airport.enums.PartType;

public class PartRequestDto {
    private String title;
    private AirplaneType airplaneType;
    private PartType partType;

    public PartRequestDto() {
    }

    public String getTitle() {
        return title;
    }

    public PartRequestDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public AirplaneType getAirplaneType() {
        return airplaneType;
    }

    public PartRequestDto setAirplaneType(AirplaneType airplaneType) {
        this.airplaneType = airplaneType;
        return this;
    }

    public PartType getPartType() {
        return partType;
    }

    public PartRequestDto setPartType(PartType partType) {
        this.partType = partType;
        return this;
    }
}
