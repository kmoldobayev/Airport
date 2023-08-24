package kg.kuban.airport.service;

import com.querydsl.core.types.Predicate;
import kg.kuban.airport.dto.AppUserRequestDto;
import kg.kuban.airport.entity.AppUser;
import kg.kuban.airport.exception.AppUserNotFoundException;
import kg.kuban.airport.exception.InvalidCredentialsException;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;


public interface AppUserService {
    AppUser createUser(AppUserRequestDto user) throws IllegalArgumentException, InvalidCredentialsException;
    AppUser updateUser(AppUserRequestDto appUserDto, Long userId) throws IllegalArgumentException, InvalidCredentialsException;
    AppUser getUserById(Long userId) throws NoSuchElementException;
    List<AppUser> getUsers(/*String token*/);
    Boolean dismissUser(Long userId) throws IllegalArgumentException;

    Predicate buildUsersCommonSearchPredicate(
            LocalDate registeredBefore,
            LocalDate registeredAfter,
            Boolean isDeleted
    );

    List<AppUser> getUserEntitiesByIdList(List<Long> userIdList) throws AppUserNotFoundException;

    AppUser getEngineerAppUserById(Long engineerId) throws AppUserNotFoundException;

}
