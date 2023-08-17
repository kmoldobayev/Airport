package kg.kuban.airport.dto;

import kg.kuban.airport.enums.AirplanePartStatus;

public class PartInspectionsRequestDto {

    private AirplanePartStatus partStatus;
    private Long partId;
    private Long airplaneId;

    public PartInspectionsRequestDto() {
    }

    public AirplanePartStatus getPartState() {
        return partStatus;
    }

    public PartInspectionsRequestDto setPartState(AirplanePartStatus partStatus) {
        this.partStatus = partStatus;
        return this;
    }

    public Long getPartId() {
        return partId;
    }

    public PartInspectionsRequestDto setPartId(Long partId) {
        this.partId = partId;
        return this;
    }

    public Long getAirplaneId() {
        return airplaneId;
    }

    public PartInspectionsRequestDto setAirplaneId(Long airplaneId) {
        this.airplaneId = airplaneId;
        return this;
    }
}
