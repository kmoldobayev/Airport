package kg.kuban.airport.repository;

import kg.kuban.airport.entity.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
    AppRole findByTitle(String title);
}
