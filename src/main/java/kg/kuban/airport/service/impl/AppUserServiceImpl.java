package kg.kuban.airport.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

import kg.kuban.airport.controller.v1.AppUserController;
import kg.kuban.airport.dto.AppUserRequestDto;
import kg.kuban.airport.entity.*;
import kg.kuban.airport.exception.AppUserNotFoundException;
import kg.kuban.airport.exception.InvalidCredentialsException;


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
    public AppUser createUser(AppUserRequestDto appUserDto) throws IllegalArgumentException, InvalidCredentialsException {

        if (Objects.isNull(appUserDto)) {
            throw new IllegalArgumentException("Входящий userDto is null");
        }

        if (Objects.isNull(appUserDto.getUserLogin())) {
            throw new InvalidCredentialsException("Login must not be null or empty");
        }
        if (Objects.isNull(appUserDto.getUserPassword()) || appUserDto.getUserPassword().isEmpty()) {
            throw new InvalidCredentialsException("Password must not be null or empty");
        }
        if (Objects.isNull(appUserDto.getFullName()) || appUserDto.getFullName().isEmpty()) {
            throw new InvalidCredentialsException("FullName must not be null or empty");
        }

        AppUser possibleDuplicate = this.appUserRepository.findAll().stream()
                .filter(x -> x.getUserLogin().equals(appUserDto.getUserLogin()))
                .findFirst()
                .orElse(null);
        logger.info("possibleDuplicate");
        if (Objects.isNull(possibleDuplicate)){
            AppUser appUser = new AppUser();

            logger.info("appUserDto.getPosition()=" + appUserDto.getPosition().getTitle());

            Position existingPosition = this.positionRepository.findByTitle(appUserDto.getPosition().getTitle());

            appUser.setFullName(appUserDto.getFullName());
            appUser.setPosition(existingPosition);
            appUser.setUserLogin(appUserDto.getUserLogin());
            appUser.setUserPassword(bCryptPasswordEncoder.encode(appUserDto.getUserPassword()));

            List<AppRole> userRolesEntityList =
                    //this.appRoleRepository.getAppRolesByPosition(existingPosition);
                    this.appRoleRepository.getAppRolesByTitle(existingPosition.getTitle());
            appUser.setAppRoles(userRolesEntityList);


            appUserRepository.save(appUser);
            logger.info("appUserRepository.save(appUser)");
            return appUser;
        } else {
            throw new IllegalArgumentException("Пользователь с таким логином " + appUserDto.getUserLogin() + " уже есть!");
        }
    }



    @Override
    public AppUser updateUser(AppUserRequestDto appUserDto, Long userId) throws IllegalArgumentException, InvalidCredentialsException {
        if (Objects.isNull(appUserDto)) {
            throw new IllegalArgumentException("Входящий appUserDto пустой!");
        }

        if (Objects.isNull(appUserDto.getUserLogin())) {
            throw new InvalidCredentialsException("Login must not be null or empty");
        }
        if (Objects.nonNull(appUserDto.getUserPassword())) {
            throw new InvalidCredentialsException("Пароль пользователя нельзя менять!!!");
        }
        if (Objects.isNull(appUserDto.getFullName()) || appUserDto.getFullName().isEmpty()) {
            throw new InvalidCredentialsException("Полное имя не должен быть null или пустым!");
        }
        if (Objects.isNull(userId)) {
            throw new IllegalArgumentException("ID пользователя не может быть null!");
        }
        if (userId < 1) {
            throw new IllegalArgumentException("ID пользователя не может быть меньше 1!");
        }

        AppUser findUser = this.appUserRepository.findAll().stream()
                                                .filter(x -> x.getId().equals(userId))
                                                .findFirst().orElse(null);

        if (Objects.nonNull(findUser)){

            if (findUser.getAppRoles().stream().anyMatch(x -> x.getTitle().equals("ADMIN"))) {
                throw new NoSuchElementException("Изменять пользователя с ролью ADMIN нельзя!");
            }

            AppUser appUser = this.getUserById(userId);
            appUser.setUserLogin(appUserDto.getUserLogin());
            Position existingPosition = this.positionRepository.findByTitle(appUserDto.getPosition().getTitle());
            appUser.setPosition(existingPosition);
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
        findUser.setEnabled(false);

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

    public Predicate buildUsersCommonSearchPredicate(
            LocalDate registeredBefore,
            LocalDate registeredAfter,
            Boolean isDeleted
    ) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QAppUser root = QAppUser.appUser;

        if(Objects.nonNull(registeredBefore)) {
            booleanBuilder.and(root.dateBegin.goe(registeredAfter));
        }
        if(Objects.nonNull(registeredBefore)) {
            booleanBuilder.and(root.dateBegin.loe(registeredBefore));
        }
        if(Objects.nonNull(isDeleted)) {
            booleanBuilder.and(root.isEnabled.eq(isDeleted));
        }

        return booleanBuilder;
    }

    @Override
    public List<AppUser> getUserEntitiesByIdList(List<Long> userIdList) throws AppUserNotFoundException
    {
        if(Objects.isNull(userIdList) || userIdList.isEmpty()) {
            throw new IllegalArgumentException("Список ID пользователей не может быть null или пустым!");
        }
        for (Long userId : userIdList) {
            if (userId < 1) {
                throw new IllegalArgumentException("ID пользователя не может быть меньше 1!");
            }
        }

        List<AppUser> applicationUsersEntities =
                this.appUserRepository.getAppUserByIdIn(userIdList);

        if(applicationUsersEntities.isEmpty()) {
            throw new AppUserNotFoundException("Пользователей по заданному списку ID не найдено!");
        }
        return applicationUsersEntities;
    }

}
