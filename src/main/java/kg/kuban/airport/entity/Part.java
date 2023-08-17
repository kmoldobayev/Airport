package kg.kuban.airport.entity;

import kg.kuban.airport.enums.AirplaneType;
import kg.kuban.airport.enums.PartType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "parts")
public class Part {
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

//    @ManyToMany(mappedBy = "part")
//    private List<Airplane> airplanes;
//    @OneToMany(mappedBy = "part")
//    private List<PartInspection> partInspections;

    public Part() {
    }

    public Long getId() {
        return id;
    }

    public Part setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Part setTitle(String title) {
        this.title = title;
        return this;
    }

    public PartType getPartType() {
        return partType;
    }

    public Part setPartType(PartType partType) {
        this.partType = partType;
        return this;
    }

    public LocalDateTime getDateRegister() {
        return dateRegister;
    }

    public Part setDateRegister(LocalDateTime dateRegister) {
        this.dateRegister = dateRegister;
        return this;
    }
}
