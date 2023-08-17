package kg.kuban.airport.repository;

import kg.kuban.airport.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long>, QuerydslPredicateExecutor<AppUser> {
    Optional<AppUser> findByUserLogin(String login);

    @Query(value = "select count(*) from app_users", nativeQuery = true)
    Integer countUsers();
}
