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
    @JoinColumn(name = "source_airport", referencedColumnName = "id")
    @JsonIgnore
    private Airport source;                  // Пункт вылета
    @ManyToOne
    @JoinColumn(name = "destination_airport", referencedColumnName = "id")
    private Airport destination;             // Пункт назначения

    @Column(name = "date_register")
    private LocalDateTime dateRegister;    // Дата и время регистрации

    @ManyToOne
    @JoinColumn(name = "airplane_id", referencedColumnName = "id")
    private Airplane airplane;            // Самолет


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


    public Airplane getAirplane() {
        return airplane;
    }

    public Flight setAirplane(Airplane airplane) {
        this.airplane = airplane;
        return this;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public Flight setAvailable(Boolean available) {
        isAvailable = available;
        return this;
    }

    public LocalDateTime getDateRegister() {
        return dateRegister;
    }

    public Flight setDateRegister(LocalDateTime dateRegister) {
        this.dateRegister = dateRegister;
        return this;
    }
}
