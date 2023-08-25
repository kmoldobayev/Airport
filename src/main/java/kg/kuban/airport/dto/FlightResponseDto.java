package kg.kuban.airport.dto;

import java.time.LocalDateTime;

/**
 * FlightResponseDto
 */
public class FlightResponseDto {

    private Long id;                            // Уникальный идентификатор рейса

    private String flightNumber;                      // Номер рейса строковый

    private AirportResponseDto destination;             // Пункт назначения


    private LocalDateTime dateRegister;                 // Дата и время регистрации

    private AirplaneResponseDto airplane;               // Самолет

    public FlightResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public FlightResponseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public FlightResponseDto setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
        return this;
    }

    public AirportResponseDto getDestination() {
        return destination;
    }

    public FlightResponseDto setDestination(AirportResponseDto destination) {
        this.destination = destination;
        return this;
    }

    public LocalDateTime getDateRegister() {
        return dateRegister;
    }

    public FlightResponseDto setDateRegister(LocalDateTime dateRegister) {
        this.dateRegister = dateRegister;
        return this;
    }

    public AirplaneResponseDto getAirplane() {
        return airplane;
    }

    public FlightResponseDto setAirplane(AirplaneResponseDto airplane) {
        this.airplane = airplane;
        return this;
    }
}
