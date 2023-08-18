package kg.kuban.airport.dto;

import kg.kuban.airport.enums.AirplanePartStatus;
import kg.kuban.airport.enums.AirplanePartType;

import java.time.LocalDateTime;

public class PartInspectionsResponseDto {
    private Long id;
    private Long inspectionCode;
    private AirplanePartStatus partStatus;
    private LocalDateTime dateRegister;
    private Long partId;
    private AirplanePartType partType;
    private String partTitle;
    private Long airplaneId;
    private String airplaneTitle;

    public PartInspectionsResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public PartInspectionsResponseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getInspectionCode() {
        return inspectionCode;
    }

    public PartInspectionsResponseDto setInspectionCode(Long inspectionCode) {
        this.inspectionCode = inspectionCode;
        return this;
    }

    public AirplanePartStatus getPartState() {
        return partStatus;
    }

    public PartInspectionsResponseDto setPartState(AirplanePartStatus partStatus) {
        this.partStatus = partStatus;
        return this;
    }

    public LocalDateTime getDateRegister() {
        return dateRegister;
    }

    public PartInspectionsResponseDto setDateRegister(LocalDateTime dateRegister) {
        this.dateRegister = dateRegister;
        return this;
    }

    public Long getPartId() {
        return partId;
    }

    public PartInspectionsResponseDto setPartId(Long partId) {
        this.partId = partId;
        return this;
    }

    public AirplanePartType getPartType() {
        return partType;
    }

    public PartInspectionsResponseDto setPartType(AirplanePartType partType) {
        this.partType = partType;
        return this;
    }

    public String getPartTitle() {
        return partTitle;
    }

    public PartInspectionsResponseDto setPartTitle(String partTitle) {
        this.partTitle = partTitle;
        return this;
    }

    public Long getAirplaneId() {
        return airplaneId;
    }

    public PartInspectionsResponseDto setAirplaneId(Long airplaneId) {
        this.airplaneId = airplaneId;
        return this;
    }

    public String getAirplaneTitle() {
        return airplaneTitle;
    }

    public PartInspectionsResponseDto setAirplaneTitle(String airplaneTitle) {
        this.airplaneTitle = airplaneTitle;
        return this;
    }
}
