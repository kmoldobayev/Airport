package kg.kuban.airport.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import kg.kuban.airport.enums.AirplaneStatus;
import kg.kuban.airport.enums.AirplaneType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Schema(name = "Сущность Самолета", description = "Описывает сущность самолета в аэропорту")
@Entity
@Table(name = "airplanes")
public class Airplane {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "marka")
    private AirplaneType marka;              // Тип самолета (например, "боинг", "эйрбас", "суперджет" и т.д.)

    @Column(name = "board_number")
    private String boardNumber;

    @ManyToOne
    @JoinColumn(name = "aircompany", referencedColumnName = "id")
    private Aircompany airCompany;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AppUser servicedBy;

    @Column(name = "date_register")
    private LocalDateTime dateRegister;

    @Column(name = "number_seats")
    private Integer numberSeats;            // Количество посадочных мест
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AirplaneStatus status;          // Статус самолета

    @Column(name = "is_available")
    private Boolean isAvailable;

//    @OneToMany(mappedBy = "userFlights", cascade = { CascadeType.MERGE, CascadeType.PERSIST })
//    private List<Seat> airplaneSeats;
//    @OneToMany(mappedBy = "airplanes", cascade = { CascadeType.MERGE, CascadeType.PERSIST })
//    private List<PartInspection> partInspections;
//    @OneToMany(mappedBy = "airplanes")
//    private List<Flight> flights;

    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinTable(
            name = "m2m_airplanes_parts",
            joinColumns = @JoinColumn(name = "airplane_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "part_id", referencedColumnName = "id")
    )
    private List<AirplanePart> parts;

    public Airplane() {
    }

    public Long getId() {
        return id;
    }

    public Airplane setId(Long id) {
        this.id = id;
        return this;
    }

    public AirplaneType getMarka() {
        return marka;
    }

    public Airplane setMarka(AirplaneType marka) {
        this.marka = marka;
        return this;
    }

    public LocalDateTime getDateRegister() {
        return dateRegister;
    }

    public Airplane setDateRegister(LocalDateTime dateRegister) {
        this.dateRegister = dateRegister;
        return this;
    }

    public Integer getNumberSeats() {
        return numberSeats;
    }

    public Airplane setNumberSeats(Integer numberSeats) {
        this.numberSeats = numberSeats;
        return this;
    }



    public String getBoardNumber() {
        return boardNumber;
    }

    public Airplane setBoardNumber(String boardNumber) {
        this.boardNumber = boardNumber;
        return this;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public Airplane setAvailable(Boolean available) {
        isAvailable = available;
        return this;
    }

    public Aircompany getAirCompany() {
        return airCompany;
    }

    public Airplane setAirCompany(Aircompany airCompany) {
        this.airCompany = airCompany;
        return this;
    }

    public AirplaneStatus getStatus() {
        return status;
    }

    public Airplane setStatus(AirplaneStatus status) {
        this.status = status;
        return this;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public Airplane setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
        return this;
    }

    public AppUser getServicedBy() {
        return servicedBy;
    }

    public Airplane setServicedBy(AppUser servicedBy) {
        this.servicedBy = servicedBy;
        return this;
    }

//    public List<Seat> getAirplaneSeats() {
//        return airplaneSeats;
//    }
//
//    public Airplane setAirplaneSeats(List<Seat> airplaneSeats) {
//        this.airplaneSeats = airplaneSeats;
//        return this;
//    }
//
//    public List<PartInspection> getPartInspections() {
//        return partInspections;
//    }
//
//    public Airplane setPartInspections(List<PartInspection> partInspections) {
//        this.partInspections = partInspections;
//        return this;
//    }
//
//    public List<Flight> getFlights() {
//        return flights;
//    }
//
//    public Airplane setFlights(List<Flight> flights) {
//        this.flights = flights;
//        return this;
//    }
}
