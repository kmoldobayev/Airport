package kg.kuban.airport.service;

import kg.kuban.airport.dto.AppUserRequestDto;
import kg.kuban.airport.entity.AppUser;

import java.util.List;
import java.util.NoSuchElementException;


public interface AppUserService {
    AppUser createUser(AppUserRequestDto user) throws IllegalArgumentException;
    AppUser createCustomer(AppUserRequestDto appUserDto) throws IllegalArgumentException;
    AppUser updateUser(AppUserRequestDto appUserDto, Long userId) throws NoSuchElementException;
    AppUser getUserById(Long userId) throws NoSuchElementException;
    List<AppUser> getUsers(/*String token*/);
    Boolean dismissUser(Long userId) throws IllegalArgumentException;
}
