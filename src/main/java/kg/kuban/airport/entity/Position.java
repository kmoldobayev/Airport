package kg.kuban.airport.entity;

import javax.persistence.*;

@Entity
@Table(name = "positions")
public class Position {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                     // Уникальный Идентификатор самолета числового типа
    @Column(name = "title")
    private String title;

//    @ManyToOne
//    @JoinColumn(name = "role_id", referencedColumnName = "id")
//    private AppRole appRole;              // Роль
//

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
