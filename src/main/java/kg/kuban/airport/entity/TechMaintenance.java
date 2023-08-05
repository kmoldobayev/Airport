package kg.kuban.airport.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tech_maintenances")
public class TechMaintenance {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                     // Уникальный Идентификатор самолета числового типа

    @ManyToOne
    @JoinColumn(name = "tech_insp_id", referencedColumnName = "id")
    private TechInspection techInspId;

    @Column(name = "is_fuel")
    private Boolean isFuel;

    @ManyToOne
    @JoinColumn(name = "engineer_id", referencedColumnName = "id")
    private AppUser engineerId;

    @ManyToOne
    @JoinColumn(name = "plane_id", referencedColumnName = "id")
    @JsonBackReference
    @Fetch(value = FetchMode.JOIN)
    private Airplane planeId;

    @ManyToOne
    @JoinColumn(name = "remont_id", referencedColumnName = "id")
    private RepairWork remontId;

    @Column(name = "date_maintenance")
    private LocalDate dateMaintenance;

    public TechMaintenance() {
    }

    public Long getId() {
        return id;
    }

    public TechMaintenance setId(Long id) {
        this.id = id;
        return this;
    }

    public TechInspection getTechInspId() {
        return techInspId;
    }

    public TechMaintenance setTechInspId(TechInspection techInspId) {
        this.techInspId = techInspId;
        return this;
    }

    public RepairWork getRemontId() {
        return remontId;
    }

    public TechMaintenance setRemontId(RepairWork remontId) {
        this.remontId = remontId;
        return this;
    }

    public Boolean getFuel() {
        return isFuel;
    }

    public TechMaintenance setFuel(Boolean fuel) {
        isFuel = fuel;
        return this;
    }

    public AppUser getEngineerId() {
        return engineerId;
    }

    public TechMaintenance setEngineerId(AppUser engineerId) {
        this.engineerId = engineerId;
        return this;
    }

    public Airplane getPlaneId() {
        return planeId;
    }

    public TechMaintenance setPlaneId(Airplane planeId) {
        this.planeId = planeId;
        return this;
    }



    public LocalDate getDateMaintenance() {
        return dateMaintenance;
    }

    public TechMaintenance setDateMaintenance(LocalDate dateMaintenance) {
        this.dateMaintenance = dateMaintenance;
        return this;
    }
}
