package kg.kuban.airport.dto;

import kg.kuban.airport.enums.AirplanePartType;
import kg.kuban.airport.enums.AirplaneType;

import java.time.LocalDateTime;

public class AirplanePartResponseDto {
    private Long id;
    private String title;
    private AirplaneType airplaneType;
    private AirplanePartType airplanePartType;
    private LocalDateTime dateRegister;

    public AirplanePartResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public AirplanePartResponseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public AirplanePartResponseDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public AirplaneType getAirplaneType() {
        return airplaneType;
    }

    public AirplanePartResponseDto setAirplaneType(AirplaneType airplaneType) {
        this.airplaneType = airplaneType;
        return this;
    }

    public AirplanePartType getAirplanePartType() {
        return airplanePartType;
    }

    public AirplanePartResponseDto setAirplanePartType(AirplanePartType airplanePartType) {
        this.airplanePartType = airplanePartType;
        return this;
    }

    public LocalDateTime getDateRegister() {
        return dateRegister;
    }

    public AirplanePartResponseDto setDateRegister(LocalDateTime dateRegister) {
        this.dateRegister = dateRegister;
        return this;
    }
}
