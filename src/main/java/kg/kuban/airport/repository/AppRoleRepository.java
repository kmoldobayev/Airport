package kg.kuban.airport.repository;

import kg.kuban.airport.entity.AppRole;
import kg.kuban.airport.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppRoleRepository extends JpaRepository<AppRole, Long>, QuerydslPredicateExecutor<AppRole> {
    AppRole findByTitle(String title);

    List<AppRole> getAppRolesByPosition(Position position);
}
