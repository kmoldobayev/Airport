package kg.kuban.airport.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "repair_works")
public class RepairWork {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                     // Уникальный Идентификатор самолета числового типа

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "cost_work")
    private Integer costWork;

    @Column(name = "date_begin")
    private LocalDate dateBegin;

    @Column(name = "date_ending")
    private LocalDate dateEnding;

    @ManyToOne
    @JoinColumn(name = "equipment_id", referencedColumnName = "id")
    private Equipment equipmentId;


    public RepairWork() {
    }

    public Long getId() {
        return id;
    }

    public RepairWork setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public RepairWork setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public RepairWork setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getCostWork() {
        return costWork;
    }

    public RepairWork setCostWork(Integer costWork) {
        this.costWork = costWork;
        return this;
    }

    public LocalDate getDateBegin() {
        return dateBegin;
    }

    public RepairWork setDateBegin(LocalDate dateBegin) {
        this.dateBegin = dateBegin;
        return this;
    }

    public LocalDate getDateEnding() {
        return dateEnding;
    }

    public RepairWork setDateEnding(LocalDate dateEnding) {
        this.dateEnding = dateEnding;
        return this;
    }

    public Equipment getEquipmentId() {
        return equipmentId;
    }

    public RepairWork setEquipmentId(Equipment equipmentId) {
        this.equipmentId = equipmentId;
        return this;
    }
}
