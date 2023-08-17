package kg.kuban.airport.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import kg.kuban.airport.enums.AirplaneStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Schema(name = "Сущность Пользователь и Рейс", description = "Описывает сущность бронирования клиентов на рейс и в тоже время используется для статусов экипажа рейса")
@Entity
@Table(name = "users_flights")
public class UserFlight {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "seat_id", referencedColumnName = "id")
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "flight_id", referencedColumnName = "id")
    private Flight flight;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AppUser appUser;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AirplaneStatus status;                  // Статус самолета

    @Column(name = "date_register")
    private LocalDateTime dateRegister;            // Дата регистрации



    public UserFlight() {
    }

    @PrePersist
    private void prePersist() {
        this.dateRegister = LocalDateTime.now();
    }

    public AirplaneStatus getStatus() {
        return status;
    }

    public UserFlight setStatus(AirplaneStatus status) {
        this.status = status;
        return this;
    }

    public Long getId() {
        return id;
    }

    public UserFlight setId(Long id) {
        this.id = id;
        return this;
    }

    public Flight getFlight() {
        return flight;
    }

    public UserFlight setFlight(Flight flight) {
        this.flight = flight;
        return this;
    }

    public Seat getSeat() {
        return seat;
    }

    public UserFlight setSeat(Seat seat) {
        this.seat = seat;
        return this;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public UserFlight setAppUser(AppUser appUser) {
        this.appUser = appUser;
        return this;
    }

    public LocalDateTime getDateRegister() {
        return dateRegister;
    }

    public UserFlight setDateRegister(LocalDateTime dateRegister) {
        this.dateRegister = dateRegister;
        return this;
    }
}
