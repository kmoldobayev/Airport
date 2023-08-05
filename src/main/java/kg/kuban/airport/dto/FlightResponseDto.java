package kg.kuban.airport.dto;

import java.time.LocalDateTime;

/**
 * FlightResponseDto
 */
public class FlightResponseDto {

    private Long id;                            // Уникальный идентификатор рейса

    private String number;                      // Номер рейса строковый

    private AirportResponseDto source;                  // Пункт вылета

    private AirportResponseDto destination;             // Пункт назначения


    private LocalDateTime departureTime;    // Дата и время вылета

    private LocalDateTime arrivalTime;      // Дата и время прибытия

    private AircompanyResponseDto aircompany;            // Авиакомпания

    public FlightResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public FlightResponseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNumber() {
        return number;
    }

    public FlightResponseDto setNumber(String number) {
        this.number = number;
        return this;
    }

    public AirportResponseDto getSource() {
        return source;
    }

    public FlightResponseDto setSource(AirportResponseDto source) {
        this.source = source;
        return this;
    }

    public AirportResponseDto getDestination() {
        return destination;
    }

    public FlightResponseDto setDestination(AirportResponseDto destination) {
        this.destination = destination;
        return this;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public FlightResponseDto setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
        return this;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public FlightResponseDto setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
        return this;
    }

    public AircompanyResponseDto getAircompany() {
        return aircompany;
    }

    public FlightResponseDto setAircompany(AircompanyResponseDto aircompany) {
        this.aircompany = aircompany;
        return this;
    }
}
