package kg.kuban.airport.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import kg.kuban.airport.controller.v1.AppUserController;
import kg.kuban.airport.dto.AppUserRequestDto;
import kg.kuban.airport.entity.*;
import kg.kuban.airport.exception.InvalidCredentialsException;
import kg.kuban.airport.repository.AppRoleRepository;
import kg.kuban.airport.repository.AppUserRepository;
import kg.kuban.airport.repository.PositionRepository;
import kg.kuban.airport.service.AppUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
            appUser.setUserLogin(appUserDto.getUserLogin());
            appUser.setUserPassword(bCryptPasswordEncoder.encode(appUserDto.getUserPassword()));
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


}
