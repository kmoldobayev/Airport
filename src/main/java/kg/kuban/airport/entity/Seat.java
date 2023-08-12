package kg.kuban.airport.entity;

import javax.persistence.*;

@Entity
@Table(name = "seats")
public class Seat {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                     // Уникальный Идентификатор самолета числового типа

    @Column(name = "seat_number")
    private Integer seatNumber;

    @ManyToOne
    @JoinColumn(name = "airplane_id", referencedColumnName = "id")
    private Airplane airplane;

    @Column(name = "is_occupied")
    private Boolean is_occupied;

    public Seat() {
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

    public Boolean getIs_occupied() {
        return is_occupied;
    }

    public Seat setIs_occupied(Boolean is_occupied) {
        this.is_occupied = is_occupied;
        return this;
    }
}
