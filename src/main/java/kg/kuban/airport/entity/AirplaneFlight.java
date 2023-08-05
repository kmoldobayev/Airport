package kg.kuban.airport.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kg.kuban.airport.enums.AirplaneStatus;

import javax.persistence.*;

@Entity
@Table(name = "airplanes_flights")
public class AirplaneFlight {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                     // Уникальный Идентификатор самолета числового типа

    @ManyToOne
    @JoinColumn(name = "airplane_id", referencedColumnName = "id")
    @JsonIgnore
    private Airplane airplane;

    @ManyToOne
    @JoinColumn(name = "flight_id", referencedColumnName = "id")
    @JsonIgnore
    private Flight flight;

//    @ManyToOne
//    @JoinColumn(name = "crew_id", referencedColumnName = "id")
//    @JsonIgnore
//    private Crew crew;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AirplaneStatus status;                  // Статус рейса

    public AirplaneFlight() {
    }

    public AirplaneStatus getStatus() {
        return status;
    }

    public AirplaneFlight setStatus(AirplaneStatus status) {
        this.status = status;
        return this;
    }

    public Long getId() {
        return id;
    }

    public AirplaneFlight setId(Long id) {
        this.id = id;
        return this;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public AirplaneFlight setAirplane(Airplane airplane) {
        this.airplane = airplane;
        return this;
    }

    public Flight getFlight() {
        return flight;
    }

    public AirplaneFlight setFlight(Flight flight) {
        this.flight = flight;
        return this;
    }


}
