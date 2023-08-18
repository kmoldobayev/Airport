package kg.kuban.airport.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Schema(name = "Сущность Места в самолете", description = "Описывает сущность Места в самолете")
@Entity
@Table(name = "seats")
public class Seat {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seat_number")
    private Integer seatNumber;

    @ManyToOne
    @JoinColumn(name = "airplane_id", referencedColumnName = "id")
    private Airplane airplane;

    @OneToMany(mappedBy = "seat")
    private List<UserFlight> userFlights;

    @Column(name = "is_occupied")
    private Boolean isOccupied;

    public Seat() {
        this.userFlights = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public Seat setId(Long id) {
        this.id = id;
        return this;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public Seat setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
        return this;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public Seat setAirplane(Airplane airplane) {
        this.airplane = airplane;
        return this;
    }

    public Boolean getOccupied() {
        return isOccupied;
    }

    public Seat setOccupied(Boolean occupied) {
        isOccupied = occupied;
        return this;
    }

    public List<UserFlight> getUserFlights() {
        return userFlights;
    }

    public Seat setUserFlights(List<UserFlight> userFlights) {
        this.userFlights = userFlights;
        return this;
    }
}
