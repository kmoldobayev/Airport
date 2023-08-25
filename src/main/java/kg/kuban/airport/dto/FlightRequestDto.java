package kg.kuban.airport.dto;

import java.time.LocalDateTime;

public class FlightRequestDto {

    private String flightNumber;                              // Номер рейса строковый

    private AirportRequestDto destination;              // Пункт назначения


    private Long airplaneId;                // Самолет

    public FlightRequestDto() {
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public FlightRequestDto setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
        return this;
    }

    public AirportRequestDto getDestination() {
        return destination;
    }

    public FlightRequestDto setDestination(AirportRequestDto destination) {
        this.destination = destination;
        return this;
    }

    public Long getAirplaneId() {
        return airplaneId;
    }

    public FlightRequestDto setAirplaneId(Long airplaneId) {
        this.airplaneId = airplaneId;
        return this;
    }
}
