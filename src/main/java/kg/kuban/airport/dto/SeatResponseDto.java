package kg.kuban.airport.dto;

public class SeatResponseDto {
    private Long id;
    private Integer seatNumber;
    private Boolean isOccupied;

    public SeatResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public SeatResponseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public SeatResponseDto setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
        return this;
    }

    public Boolean getOccupied() {
        return isOccupied;
    }

    public SeatResponseDto setOccupied(Boolean occupied) {
        isOccupied = occupied;
        return this;
    }
}
