package kg.kuban.airport.entity;

import kg.kuban.airport.enums.PartType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "parts")
public class Part {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                     // Уникальный Идентификатор самолета числового типа

    @Column(name = "title")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "part_type")
    private PartType partType;

    @Column(name = "date_register")
    private LocalDateTime dateRegister;

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
