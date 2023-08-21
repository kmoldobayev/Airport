package kg.kuban.airport.dto;

import kg.kuban.airport.enums.AirplanePartStatus;
import kg.kuban.airport.enums.AirplanePartType;

import java.time.LocalDateTime;

public class AirplanePartCheckupResponseDto {
    private Long id;
    private Long checkupCode;
    private AirplanePartStatus partStatus;
    private LocalDateTime dateRegister;
    private Long partId;
    private AirplanePartType partType;
    private String partTitle;
    private Long airplaneId;
    private String airplaneTitle;

    public AirplanePartCheckupResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public AirplanePartCheckupResponseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getCheckupCode() {
        return checkupCode;
    }

    public AirplanePartCheckupResponseDto setCheckupCode(Long checkupCode) {
        this.checkupCode = checkupCode;
        return this;
    }

    public AirplanePartStatus getPartStatus() {
        return partStatus;
    }

    public AirplanePartCheckupResponseDto setPartStatus(AirplanePartStatus partStatus) {
        this.partStatus = partStatus;
        return this;
    }

    public AirplanePartStatus getPartState() {
        return partStatus;
    }

    public AirplanePartCheckupResponseDto setPartState(AirplanePartStatus partStatus) {
        this.partStatus = partStatus;
        return this;
    }

    public LocalDateTime getDateRegister() {
        return dateRegister;
    }

    public AirplanePartCheckupResponseDto setDateRegister(LocalDateTime dateRegister) {
        this.dateRegister = dateRegister;
        return this;
    }

    public Long getPartId() {
        return partId;
    }

    public AirplanePartCheckupResponseDto setPartId(Long partId) {
        this.partId = partId;
        return this;
    }

    public AirplanePartType getPartType() {
        return partType;
    }

    public AirplanePartCheckupResponseDto setPartType(AirplanePartType partType) {
        this.partType = partType;
        return this;
    }

    public String getPartTitle() {
        return partTitle;
    }

    public AirplanePartCheckupResponseDto setPartTitle(String partTitle) {
        this.partTitle = partTitle;
        return this;
    }

    public Long getAirplaneId() {
        return airplaneId;
    }

    public AirplanePartCheckupResponseDto setAirplaneId(Long airplaneId) {
        this.airplaneId = airplaneId;
        return this;
    }

    public String getAirplaneTitle() {
        return airplaneTitle;
    }

    public AirplanePartCheckupResponseDto setAirplaneTitle(String airplaneTitle) {
        this.airplaneTitle = airplaneTitle;
        return this;
    }
}
