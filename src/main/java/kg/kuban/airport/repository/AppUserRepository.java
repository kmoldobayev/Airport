package kg.kuban.airport.repository;

import kg.kuban.airport.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUserLogin(String login);

    @Query(value = "select count(*) from app_users", nativeQuery = true)
    Integer countUsers();
}
