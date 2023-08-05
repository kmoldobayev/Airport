package kg.kuban.airport.entity;

import javax.persistence.*;

@Entity
@Table(name = "equipments")
public class Equipment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                     // Уникальный Идентификатор самолета числового типа

}
