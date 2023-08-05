package kg.kuban.airport.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
public class Flight {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                     // Уникальный идентификатор рейса
    @Column(name = "flight_number")
    private String flightNumber;                  // Номер рейса строковый
    @ManyToOne
    @JoinColumn(name = "source", referencedColumnName = "id")
    @JsonIgnore
    private Airport source;                  // Пункт вылета
    @ManyToOne
    @JoinColumn(name = "destination", referencedColumnName = "id")
    private Airport destination;             // Пункт назначения

    @Column(name = "departure_datetime")
    private LocalDateTime departureTime;    // Дата и время вылета
    @Column(name = "arrival_datetime")
    private LocalDateTime arrivalTime;      // Дата и время прибытия

    @ManyToOne
    @JoinColumn(name = "aircompany", referencedColumnName = "id")
    private Aircompany aircompany;            // Доступен или нет


    @Column(name = "isAvailable")
    private Boolean isAvailable;            // Доступен или нет

    public Long getId() {
        return id;
    }

    public Flight setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public Flight setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
        return this;
    }

    public Airport getSource() {
        return this.source;
    }

    public Flight setSource(Airport source) {
        this.source = source;
        return this;
    }

    public Airport getDestination() {
        return destination;
    }

    public Flight setDestination(Airport destination) {
        this.destination = destination;
        return this;
    }



    public Aircompany getAircompany() {
        return aircompany;
    }

    public Flight setAircompany(Aircompany aircompany) {
        this.aircompany = aircompany;
        return this;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public Flight setAvailable(Boolean available) {
        isAvailable = available;
        return this;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public Flight setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
        return this;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public Flight setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
        return this;
    }

}
