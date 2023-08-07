package kg.kuban.airport.service.impl;

import kg.kuban.airport.controller.v1.AppUserController;
import kg.kuban.airport.dto.AppUserRequestDto;
import kg.kuban.airport.entity.*;
import kg.kuban.airport.enums.UserStatus;
import kg.kuban.airport.mapper.PositionMapper;
import kg.kuban.airport.repository.AppRoleRepository;
import kg.kuban.airport.repository.AppUserRepository;
import kg.kuban.airport.repository.PositionRepository;
import kg.kuban.airport.service.AppUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class AppUserServiceImpl implements AppUserService {

    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PositionRepository positionRepository;

    private PasswordEncoder bCryptPasswordEncoder;
    private final Logger logger = LoggerFactory.getLogger(AppUserController.class);

    @Autowired
    public AppUserServiceImpl(AppUserRepository appUserRepository,
                              AppRoleRepository appRoleRepository,
                              PositionRepository positionRepository,
                              PasswordEncoder bCryptPasswordEncoder
                              ) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.positionRepository = positionRepository;
    }

    @Override
    public List<AppUser> getUsers() {

        logger.info("appUserRepository.findAll()");
        return appUserRepository.findAll();
    }

    @Override
    public AppUser createUser(AppUserRequestDto appUserDto) throws IllegalArgumentException {
        AppUser possibleDuplicate = appUserRepository.findAll().stream()
                .filter(x -> x.getUserLogin().equals(appUserDto.getUserLogin()))
                .findFirst()
                .orElse(null);
        logger.info("possibleDuplicate");
        if (Objects.isNull(possibleDuplicate)){
            AppUser appUser = new AppUser();
            appUser.setAppRoles(Collections.singleton(new AppRole(1L, "ROLE_USER")));
            logger.info("appUserDto.getPosition()=" + appUserDto.getPosition().getTitle());

            Position existingPosition = this.positionRepository.findByTitle(appUserDto.getPosition().getTitle());

            appUser.setPosition(existingPosition);
            appUser.setStatus(UserStatus.ACTIVE);
            appUser.setUserLogin(appUserDto.getUserLogin());
            appUser.setUserPassword(bCryptPasswordEncoder.encode("a1b2c3"));
            appUser.setDateBegin(LocalDate.now());
            appUserRepository.save(appUser);
            logger.info("appUserRepository.save(appUser)");
            return appUser;
        } else {
            throw new IllegalArgumentException("Пользователь с таким логином " + appUserDto.getUserLogin() + " уже есть!");
        }
    }

    @Override
    public AppUser updateUser(AppUserRequestDto appUserDto, Long userId) throws NoSuchElementException {
        AppUser findUser = this.getUsers().stream().
                filter(x -> x.getId().equals(userId)).
                findFirst().orElse(null);

        if (Objects.nonNull(findUser)){

            if (findUser.getAppRoles().stream().anyMatch(x -> x.getTitle().equals("ADMIN"))) {
                throw new NoSuchElementException("Изменять пользователя с ролью ADMIN нельзя!");
            }

            AppUser appUser = this.getUserById(userId);
            appUser.setUserLogin(appUserDto.getUserLogin());
            Position existingPosition = this.positionRepository.findByTitle(appUserDto.getPosition().getTitle());
            appUser.setPosition(existingPosition);
            appUser.setStatus(appUserDto.getStatus());
            appUserRepository.save(appUser);
            return appUser;
        } else {
            throw new NoSuchElementException("Такой ID пользователя не существует!");
        }
    }

    @Override
    public Boolean dismissUser(Long userId) throws IllegalArgumentException {
        AppUser findUser = getUserById(userId);
        findUser.setDateEnding(LocalDate.now());
        findUser.setStatus(UserStatus.DISMISSED);
        return true;
    }

    @Override
    public AppUser getUserById(Long userId) throws NoSuchElementException {

        AppUser user = appUserRepository.findAll().stream()
                .filter(x -> x.getId().equals(userId))
                .findFirst()
                .orElse(null);
        if (Objects.isNull(user)){
            throw new NoSuchElementException ("Пользователя с таким ID нет");
        }
        return user;
    }

    public AppUser getUserByLogin(String username) {
        Optional<AppUser> userOptional = this.appUserRepository.findByUserLogin(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("Нет пользователя с ID=" + username);
        }
        return userOptional.get();
    }

}
