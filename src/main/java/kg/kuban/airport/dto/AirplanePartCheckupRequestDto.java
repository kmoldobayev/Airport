package kg.kuban.airport.dto;

import kg.kuban.airport.enums.AirplanePartStatus;

public class AirplanePartCheckupRequestDto {

    private AirplanePartStatus partStatus;
    private Long partId;
    private Long airplaneId;

    public AirplanePartCheckupRequestDto() {
    }

    public AirplanePartStatus getPartState() {
        return partStatus;
    }

    public AirplanePartCheckupRequestDto setPartState(AirplanePartStatus partStatus) {
        this.partStatus = partStatus;
        return this;
    }

    public Long getPartId() {
        return partId;
    }

    public AirplanePartCheckupRequestDto setPartId(Long partId) {
        this.partId = partId;
        return this;
    }

    public Long getAirplaneId() {
        return airplaneId;
    }

    public AirplanePartCheckupRequestDto setAirplaneId(Long airplaneId) {
        this.airplaneId = airplaneId;
        return this;
    }
}
