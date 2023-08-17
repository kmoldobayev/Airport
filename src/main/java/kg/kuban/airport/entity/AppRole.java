package kg.kuban.airport.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Schema(name = "Сущность Роль пользователя", description = "Описывает сущность роль пользователя")
@Entity
@Table(name = "app_roles")
public class AppRole implements GrantedAuthority {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Transient
    @ManyToMany(mappedBy = "appRoles")
    private Set<AppUser> users;

    @ManyToOne
    @JoinColumn(name = "position_id", referencedColumnName = "id")
    private Position position;


    public AppRole() {
    }

    public AppRole(Long id) {
        this.id = id;
    }

    public AppRole(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public AppRole setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public AppRole setTitle(String title) {
        this.title = title;
        return this;
    }

    public Set<AppUser> getUsers() {
        return users;
    }

    public AppRole setUsers(Set<AppUser> users) {
        this.users = users;
        return this;
    }

    public Position getPosition() {
        return position;
    }

    public AppRole setPosition(Position position) {
        this.position = position;
        return this;
    }

    @Override
    public String getAuthority() {

        return "ROLE_" + this.title;
    }


}
