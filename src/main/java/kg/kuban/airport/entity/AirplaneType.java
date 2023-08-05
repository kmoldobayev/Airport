package kg.kuban.airport.entity;

import javax.persistence.*;

@Entity
@Table(name = "airplane_types")
public class AirplaneType {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                     // Уникальный Идентификатор самолета числового типа
    @Column(name = "title")
    private String title;

    public AirplaneType() {
    }

    public Long getId() {
        return id;
    }

    public AirplaneType setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public AirplaneType setTitle(String title) {
        this.title = title;
        return this;
    }
}
