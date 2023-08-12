package kg.kuban.airport.service;

import kg.kuban.airport.dto.AppRoleRequestDto;
import kg.kuban.airport.dto.AppUserRequestDto;
import kg.kuban.airport.entity.AppRole;
import kg.kuban.airport.entity.AppUser;

import java.util.List;
import java.util.NoSuchElementException;

public interface AppRoleService {
    AppRole createRole(AppRoleRequestDto user) throws IllegalArgumentException;
    AppRole updateRole(AppRoleRequestDto appRoleDto, Long userId) throws NoSuchElementException;
    AppRole getRoleById(Long roleId) throws NoSuchElementException;
    List<AppRole> getRoles();
}
