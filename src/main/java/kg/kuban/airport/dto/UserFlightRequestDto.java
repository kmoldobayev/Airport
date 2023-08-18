package kg.kuban.airport.dto;

public class UserFlightRequestDto {
    private Long airplaneSeatId;
    private Long flightId;
    private Long userId;

    public UserFlightRequestDto() {
    }

    public Long getAirplaneSeatId() {
        return airplaneSeatId;
    }

    public UserFlightRequestDto setAirplaneSeatId(Long airplaneSeatId) {
        this.airplaneSeatId = airplaneSeatId;
        return this;
    }

    public Long getFlightId() {
        return flightId;
    }

    public UserFlightRequestDto setFlightId(Long flightId) {
        this.flightId = flightId;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public UserFlightRequestDto setUserId(Long userId) {
        this.userId = userId;
        return this;
    }
}
