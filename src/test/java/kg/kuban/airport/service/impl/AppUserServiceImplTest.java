package kg.kuban.airport.service.impl;

import kg.kuban.airport.dto.AppUserRequestDto;
import kg.kuban.airport.dto.PositionRequestDto;
import kg.kuban.airport.entity.AppUser;
import kg.kuban.airport.repository.AppRoleRepository;
import kg.kuban.airport.repository.AppUserRepository;
import kg.kuban.airport.repository.PositionRepository;
import kg.kuban.airport.service.AppUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        AppUserServiceImpl.class,
        PasswordEncoder.class,
        SecurityContext.class
})
public class AppUserServiceImplTest {

    @Autowired
    private AppUserServiceImpl appUserService;

    @MockBean(answer = Answers.RETURNS_DEFAULTS)
    private AppUserRepository appUserRepository;

    @MockBean(answer = Answers.RETURNS_DEFAULTS)
    private AppRoleRepository appRoleRepository;

    @MockBean(answer = Answers.RETURNS_DEFAULTS)
    private PositionRepository positionRepository;

    private PasswordEncoder passwordEncoder;


    @BeforeAll
    public static void init() {

    }

    @BeforeEach
    public void flash() {

    }

    @Test
    void getUsers() {
    }

    @Test
    void testCreateUser_OK() {
        ///final AppUser appUser = new AppUser();
        //appUser.setUserLogin()
        AppUserService userService = new AppUserServiceImpl(
                this.appUserRepository,
                this.appRoleRepository,
                this.positionRepository,
                this.passwordEncoder
                );

        PositionRequestDto positionRequestDto = new PositionRequestDto();
        positionRequestDto.setTitle("CHIEF");

        AppUserRequestDto appUserDto = new AppUserRequestDto();
        appUserDto
                .setUserLogin("test")
                .setUserPassword(passwordEncoder.encode("test"))
                .setFullName("test")
                .setPosition(positionRequestDto);
        try {
            AppUser user = userService.createUser(appUserDto);

            ///verify(appUserRepository, times(1)).save(any(AppUser.class));

//            assert user.getLogin().equals("test");
//            assert user.getPassword().equals(Integer.toString(Objects.hash("test")));
//            assert user.getName().equals("test");
//            assert user.getEmail().equals("test@gmail.com");
        } catch (Exception ex) {
            Assertions.fail();
        }
    }

    @Test
    void createCustomer() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void dismissUser() {
    }

    @Test
    void getUserById() {
    }

    @Test
    void getUserByLogin() {
    }
}