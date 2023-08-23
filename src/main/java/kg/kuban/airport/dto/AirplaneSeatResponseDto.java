package kg.kuban.airport.dto;

public class AirplaneSeatResponseDto {
    private Long id;
    private Integer seatNumber;
    private Boolean isOccupied;

    public AirplaneSeatResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public AirplaneSeatResponseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public AirplaneSeatResponseDto setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
        return this;
    }

    public Boolean getOccupied() {
        return isOccupied;
    }

    public AirplaneSeatResponseDto setOccupied(Boolean occupied) {
        isOccupied = occupied;
        return this;
    }
}
