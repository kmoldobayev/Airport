package kg.kuban.airport.dto;

import kg.kuban.airport.enums.AirplaneType;
import kg.kuban.airport.enums.PartType;

import java.time.LocalDateTime;

public class PartResponseDto {
    private Long id;
    private String title;
    private AirplaneType aircraftType;
    private PartType partType;
    private LocalDateTime dateRegister;

    public PartResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public PartResponseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PartResponseDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public AirplaneType getAircraftType() {
        return aircraftType;
    }

    public PartResponseDto setAircraftType(AirplaneType aircraftType) {
        this.aircraftType = aircraftType;
        return this;
    }

    public PartType getPartType() {
        return partType;
    }

    public PartResponseDto setPartType(PartType partType) {
        this.partType = partType;
        return this;
    }

    public LocalDateTime getDateRegister() {
        return dateRegister;
    }

    public PartResponseDto setDateRegister(LocalDateTime dateRegister) {
        this.dateRegister = dateRegister;
        return this;
    }
}
