package kg.kuban.airport.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;

@Schema(name = "Сущность Должности", description = "Описывает сущность Должности работников аэропорта")
@Entity
@Table(name = "positions")
public class Position {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;

    public Position() {
    }

    public Long getId() {
        return id;
    }

    public Position setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Position setTitle(String title) {
        this.title = title;
        return this;
    }

}
