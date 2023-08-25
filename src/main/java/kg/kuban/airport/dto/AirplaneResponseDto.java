package kg.kuban.airport.dto;

import kg.kuban.airport.enums.AirplaneStatus;
import kg.kuban.airport.enums.AirplaneType;

import java.time.LocalDateTime;

public class AirplaneResponseDto {
    private Long id;                     // Уникальный Идентификатор

    private AirplaneType marka;              // Тип самолета (например, "боинг", "эйрбас", "суперджет" и т.д.) - тип самолета определяет его грузоподъемность, дальность полета, скорость и другие характеристики, которые важны для диспетчерской службы.

    private String boardNumber;

    private Integer numberSeats;

    private AirplaneStatus airplaneStatus;

    private AircompanyResponseDto aircompanyResponseDto;

    private LocalDateTime dateRegister;

    public Long getId() {
        return id;
    }

    public AirplaneResponseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getBoardNumber() {
        return boardNumber;
    }

    public AirplaneResponseDto setBoardNumber(String boardNumber) {
        this.boardNumber = boardNumber;
        return this;
    }

    public AirplaneType getMarka() {
        return marka;
    }

    public AirplaneResponseDto setMarka(AirplaneType marka) {
        this.marka = marka;
        return this;
    }


    public LocalDateTime getDateRegister() {
        return dateRegister;
    }

    public AirplaneResponseDto setDateRegister(LocalDateTime dateRegister) {
        this.dateRegister = dateRegister;
        return this;
    }

    public Integer getNumberSeats() {
        return numberSeats;
    }

    public AirplaneResponseDto setNumberSeats(Integer numberSeats) {
        this.numberSeats = numberSeats;
        return this;
    }

    public AirplaneStatus getAirplaneStatus() {
        return airplaneStatus;
    }

    public AirplaneResponseDto setAirplaneStatus(AirplaneStatus airplaneStatus) {
        this.airplaneStatus = airplaneStatus;
        return this;
    }

    public AircompanyResponseDto getAircompanyResponseDto() {
        return aircompanyResponseDto;
    }

    public AirplaneResponseDto setAircompanyResponseDto(AircompanyResponseDto aircompanyResponseDto) {
        this.aircompanyResponseDto = aircompanyResponseDto;
        return this;
    }
}
