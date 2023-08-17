package kg.kuban.airport.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.util.List;

@Schema(name = "Сущность Аэропорт", description = "Описывает сущность аэропорта")
@Entity
@Table(name = "airports")
public class Airport {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "city")
    private String city;

    public Airport() {
    }

    public Long getId() {
        return id;
    }

    public Airport setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Airport setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Airport setCity(String city) {
        this.city = city;
        return this;
    }

    //private List<Airplane> aircraftList;
}
