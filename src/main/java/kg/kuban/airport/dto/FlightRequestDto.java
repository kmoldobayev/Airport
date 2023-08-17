package kg.kuban.airport.dto;

import java.time.LocalDateTime;

public class FlightRequestDto {

    private String number;                              // Номер рейса строковый

    private AirportRequestDto destination;              // Пункт назначения


    private AirplaneRequestDto airplane;                // Самолет

    public FlightRequestDto() {
    }

    public String getNumber() {
        return number;
    }

    public FlightRequestDto setNumber(String number) {
        this.number = number;
        return this;
    }

    public AirportRequestDto getDestination() {
        return destination;
    }

    public FlightRequestDto setDestination(AirportRequestDto destination) {
        this.destination = destination;
        return this;
    }

    public AirplaneRequestDto getAirplane() {
        return airplane;
    }

    public FlightRequestDto setAirplane(AirplaneRequestDto airplane) {
        this.airplane = airplane;
        return this;
    }
}
