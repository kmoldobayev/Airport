package kg.kuban.airport.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import kg.kuban.airport.enums.AirplaneType;
import kg.kuban.airport.enums.PartType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Schema(name = "Сущность Часть техосмотра самолета", description = "Описывает сущность части для техсмотра самолета")
@Entity
@Table(name = "parts")
public class AirplanePart {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "airplane_type")
    private AirplaneType airplaneType;

    @Enumerated(EnumType.STRING)
    @Column(name = "part_type")
    private PartType partType;

    @Column(name = "date_register")
    private LocalDateTime dateRegister;

    @ManyToMany(mappedBy = "parts")
    private List<Airplane> airplanes;
    @OneToMany(mappedBy = "part")
    private List<PartInspection> partInspections;

    public AirplanePart() {
    }

    public Long getId() {
        return id;
    }

    public AirplanePart setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public AirplanePart setTitle(String title) {
        this.title = title;
        return this;
    }

    public PartType getPartType() {
        return partType;
    }

    public AirplanePart setPartType(PartType partType) {
        this.partType = partType;
        return this;
    }

    public LocalDateTime getDateRegister() {
        return dateRegister;
    }

    public AirplanePart setDateRegister(LocalDateTime dateRegister) {
        this.dateRegister = dateRegister;
        return this;
    }

    public AirplaneType getAirplaneType() {
        return airplaneType;
    }

    public AirplanePart setAirplaneType(AirplaneType airplaneType) {
        this.airplaneType = airplaneType;
        return this;
    }

    public List<Airplane> getAirplanes() {
        return airplanes;
    }

    public AirplanePart setAirplanes(List<Airplane> airplanes) {
        this.airplanes = airplanes;
        return this;
    }

    public List<PartInspection> getPartInspections() {
        return partInspections;
    }

    public AirplanePart setPartInspections(List<PartInspection> partInspections) {
        this.partInspections = partInspections;
        return this;
    }
}
