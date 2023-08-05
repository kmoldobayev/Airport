package kg.kuban.airport.entity;

import javax.persistence.*;

@Entity
@Table(name = "aircompanies")
public class Aircompany {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                     // Уникальный Идентификатор самолета числового типа
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
