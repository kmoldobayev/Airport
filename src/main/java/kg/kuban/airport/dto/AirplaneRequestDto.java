package kg.kuban.airport.dto;

import kg.kuban.airport.enums.AirplaneType;

import java.time.LocalDateTime;
import java.util.List;

public class AirplaneRequestDto {

    private AirplaneType marka;              // Тип самолета (например, "боинг", "эйрбас", "суперджет" и т.д.) - тип самолета определяет его грузоподъемность, дальность полета, скорость и другие характеристики, которые важны для диспетчерской службы.

    private String boardNumber;

    private AircompanyRequestDto aircompany;
    private Integer numberSeats;
    private List<Long> partIdList;

    public AirplaneRequestDto() {
    }
    public AirplaneType getMarka() {
        return marka;
    }

    public AirplaneRequestDto setMarka(AirplaneType marka) {
        this.marka = marka;
        return this;
    }

    public String getBoardNumber() {
        return boardNumber;
    }

    public AirplaneRequestDto setBoardNumber(String boardNumber) {
        this.boardNumber = boardNumber;
        return this;
    }

    public AircompanyRequestDto getAircompany() {
        return aircompany;
    }

    public AirplaneRequestDto setAircompany(AircompanyRequestDto aircompany) {
        this.aircompany = aircompany;
        return this;
    }

    public Integer getNumberSeats() {
        return numberSeats;
    }

    public AirplaneRequestDto setNumberSeats(Integer numberSeats) {
        this.numberSeats = numberSeats;
        return this;
    }

    public List<Long> getPartIdList() {
        return partIdList;
    }

    public AirplaneRequestDto setPartIdList(List<Long> partIdList) {
        this.partIdList = partIdList;
        return this;
    }
}
