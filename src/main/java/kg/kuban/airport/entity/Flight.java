package kg.kuban.airport.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import kg.kuban.airport.enums.FlightStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Schema(name = "Сущность Рейс самолета", description = "Описывает сущность рейса самолета")
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
    @JoinColumn(name = "destination", referencedColumnName = "id")
    private Airport destination;             // Пункт назначения

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private FlightStatus status;

    @Column(name = "tickets_left")
    private Integer ticketsLeft;

    @Column(name = "date_register")
    private LocalDateTime dateRegister;    // Дата и время регистрации

    @ManyToOne
    @JoinColumn(name = "airplane_id", referencedColumnName = "id")
    private Airplane airplane;            // Самолет


    @Column(name = "isAvailable")
    private Boolean isAvailable;            // Доступен или нет

    @OneToMany(mappedBy = "flight", cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    private List<UserFlight> userFlights;
    @OneToMany(mappedBy = "flight")
    private List<CustomerReview> customerReviews;

    public Long getId() {
        return id;
    }

    @PrePersist
    public void init() {
        this.isAvailable = false;
        this.dateRegister = LocalDateTime.now();
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

    public FlightStatus getStatus() {
        return status;
    }

    public Flight setStatus(FlightStatus status) {
        this.status = status;
        return this;
    }

    public Integer getTicketsLeft() {
        return ticketsLeft;
    }

    public Flight setTicketsLeft(Integer ticketsLeft) {
        this.ticketsLeft = ticketsLeft;
        return this;
    }

    public List<UserFlight> getUserFlights() {
        return userFlights;
    }

    public Flight setUserFlights(List<UserFlight> userFlights) {
        this.userFlights = userFlights;
        return this;
    }

    public List<CustomerReview> getCustomerReviews() {
        return customerReviews;
    }

    public Flight setCustomerReviews(List<CustomerReview> customerReviews) {
        this.customerReviews = customerReviews;
        return this;
    }
}
