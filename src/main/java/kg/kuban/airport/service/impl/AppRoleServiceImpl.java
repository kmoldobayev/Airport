package kg.kuban.airport.service.impl;

import kg.kuban.airport.controller.v1.AppUserController;
import kg.kuban.airport.dto.AppRoleRequestDto;
import kg.kuban.airport.entity.AppRole;
import kg.kuban.airport.entity.AppUser;
import kg.kuban.airport.entity.Position;
import kg.kuban.airport.exception.InvalidCredentialsException;
import kg.kuban.airport.repository.AppRoleRepository;
import kg.kuban.airport.repository.PositionRepository;
import kg.kuban.airport.service.AppRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class AppRoleServiceImpl implements AppRoleService {
    private AppRoleRepository appRoleRepository;
    private PositionRepository positionRepository;
    private final Logger logger = LoggerFactory.getLogger(AppUserController.class);
    @Autowired
    public AppRoleServiceImpl(AppRoleRepository appRoleRepository, PositionRepository positionRepository) {
        this.appRoleRepository = appRoleRepository;
        this.positionRepository = positionRepository;
    }

    @Transactional
    @Override
    public AppRole createRole(AppRoleRequestDto roleDto) throws IllegalArgumentException {
        if (Objects.isNull(roleDto)) {
            throw new IllegalArgumentException("Входящий AppRoleRequestDto is null");
        }

        AppRole possibleDuplicate = this.appRoleRepository.findAll().stream()
                .filter(x -> x.getTitle().equals(roleDto.getTitle()))
                .findFirst()
                .orElse(null);
        logger.info("possibleDuplicate");
        if (Objects.isNull(possibleDuplicate)){
            AppRole appRole = new AppRole();
            appRole.setTitle(roleDto.getTitle());
            logger.info("appUserDto.getPosition()=" + roleDto.getPosition().getTitle());

            Position existingPosition = this.positionRepository.findByTitle(roleDto.getPosition().getTitle());

            appRole.setPosition(existingPosition);
            this.appRoleRepository.save(appRole);
            logger.info("this.appRoleRepository.save(appRole)");
            return appRole;
        } else {
            throw new IllegalArgumentException("Роль с таким названием " + roleDto.getTitle() + " уже есть!");
        }
    }

    @Transactional
    @Override
    public AppRole updateRole(AppRoleRequestDto appRoleDto, Long roleId) throws NoSuchElementException {
        AppRole findRole = this.getRoles().stream().
                filter(x -> x.getId().equals(roleId)).
                findFirst().orElse(null);

        if (Objects.nonNull(findRole)){

            if (findRole.getTitle().equals("ADMIN")) {
                throw new NoSuchElementException("Изменять роль ADMIN нельзя!");
            }

            AppRole appRole = this.getRoleById(roleId);
            appRole.setTitle(appRoleDto.getTitle());
            Position existingPosition = this.positionRepository.findByTitle(appRoleDto.getPosition().getTitle());
            appRole.setPosition(existingPosition);
            this.appRoleRepository.save(appRole);
            return appRole;
        } else {
            throw new NoSuchElementException("Такой ID роли не существует!");
        }
    }

    @Override
    public AppRole getRoleById(Long roleId) throws NoSuchElementException {
        AppRole role = this.appRoleRepository.findAll().stream()
                .filter(x -> x.getId().equals(roleId))
                .findFirst()
                .orElse(null);
        if (Objects.isNull(role)){
            throw new NoSuchElementException ("Роли с таким ID нет");
        }
        return role;
    }

    @Override
    public List<AppRole> getRoles() {

        return this.appRoleRepository.findAll();
    }

    @Transactional
    @Override
    public AppRole deleteRole(Long roleId) throws NoSuchElementException {
        AppRole findRole = this.getRoles().stream().
                filter(x -> x.getId().equals(roleId)).
                findFirst().orElse(null);

        if (Objects.nonNull(findRole)){

            if (findRole.getTitle().equals("ADMIN")) {
                throw new NoSuchElementException("Удалять роль ADMIN нельзя!");
            }

            AppRole appRole = this.getRoleById(roleId);
            this.appRoleRepository.delete(appRole);
            return appRole;
        } else {
            throw new NoSuchElementException("Такой ID роли не существует!");
        }
    }
}
