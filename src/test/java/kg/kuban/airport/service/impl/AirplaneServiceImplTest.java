package kg.kuban.airport.service.impl;

import kg.kuban.airport.repository.AppRoleRepository;
import kg.kuban.airport.repository.AppUserRepository;
import kg.kuban.airport.repository.PositionRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        AirplaneServiceImpl.class
})
class AirplaneServiceImplTest {
    @Autowired
    private AirplaneServiceImpl airplaneService;

    @MockBean(answer = Answers.RETURNS_DEFAULTS)
    private AppUserRepository appUserRepository;

    @MockBean(answer = Answers.RETURNS_DEFAULTS)
    private AppRoleRepository appRoleRepository;

    @MockBean(answer = Answers.RETURNS_DEFAULTS)
    private PositionRepository positionRepository;

    @MockBean(answer = Answers.RETURNS_DEFAULTS)
    private PasswordEncoder passwordEncoder;
}