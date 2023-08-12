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


    private LocalDateTime dateRegister;    // Дата и время реистрации

    private AirplaneResponseDto airplane;            // Самолет

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
