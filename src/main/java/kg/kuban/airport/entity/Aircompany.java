package kg.kuban.airport.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.persistence.*;

@Schema(name = "Сущность Авиакомпания", description = "Описывает сущность авиакомпании в аэропорту")
@Entity
@Table(name = "aircompanies")
public class Aircompany {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;

    public Aircompany() {
    }

    public Long getId() {
        return id;
    }

    public Aircompany setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Aircompany setTitle(String title) {
        this.title = title;
        return this;
    }
}
