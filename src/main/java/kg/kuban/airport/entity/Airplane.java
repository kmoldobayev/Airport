package kg.kuban.airport.entity;

import kg.kuban.airport.enums.AirplaneStatus;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "airplanes")
public class Airplane {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                     // Уникальный Идентификатор самолета числового типа

    @Column(name = "model")
    private String model;                   // Модель самолета

    @ManyToOne
    @JoinColumn(name = "marka", referencedColumnName = "id")
    private AirplaneType marka;              // Тип самолета (например, "боинг", "эйрбас", "суперджет" и т.д.) - тип самолета определяет его грузоподъемность, дальность полета, скорость и другие характеристики, которые важны для диспетчерской службы.

    @Column(name = "board_number")
    private Integer boardNumber;

    @ManyToOne
    @JoinColumn(name = "aircompany", referencedColumnName = "id")
    private Aircompany airCompany;

    @Column(name = "date_manufacturing")
    private LocalDate date_manufacturing;

    @Column(name = "numberOfSeats")
    private Integer numberOfSeats;          // Количество посадочных мест
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AirplaneStatus status;          // Статус самолета, которое указывает на текущий статус самолета (например, находится в ремонте, готов к вылету, находится в полете, на обслуживании, находится на земле т.д.).

    @Column(name = "is_available")
    private Boolean is_available;

    public Airplane() {
    }

    public Long getId() {
        return id;
    }

    public Airplane setId(Long id) {
        this.id = id;
        return this;
    }

    public String getModel() {
        return model;
    }

    public Airplane setModel(String model) {
        this.model = model;
        return this;
    }

    public AirplaneType getType() {
        return marka;
    }

    public Airplane setType(AirplaneType type) {
        this.marka = type;
        return this;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public Airplane setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
        return this;
    }

    public AirplaneStatus getAirplaneStatus() {
        return status;
    }

    public Airplane setAirplaneStatus(AirplaneStatus status) {
        this.status = status;
        return this;
    }

    public AirplaneType getMarka() {
        return marka;
    }

    public Airplane setMarka(AirplaneType marka) {
        this.marka = marka;
        return this;
    }

    public Integer getBoardNumber() {
        return boardNumber;
    }

    public Airplane setBoardNumber(Integer boardNumber) {
        this.boardNumber = boardNumber;
        return this;
    }

    public Aircompany getAirCompany() {
        return airCompany;
    }

    public Airplane setAirCompany(Aircompany airCompany) {
        this.airCompany = airCompany;
        return this;
    }

    public LocalDate getDate_manufacturing() {
        return date_manufacturing;
    }

    public Airplane setDate_manufacturing(LocalDate date_manufacturing) {
        this.date_manufacturing = date_manufacturing;
        return this;
    }

    public AirplaneStatus getStatus() {
        return status;
    }

    public Airplane setStatus(AirplaneStatus status) {
        this.status = status;
        return this;
    }

    public Boolean getIs_available() {
        return is_available;
    }

    public Airplane setIs_available(Boolean is_available) {
        this.is_available = is_available;
        return this;
    }
}
