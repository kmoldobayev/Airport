package kg.kuban.airport.controller.v1;

import kg.kuban.airport.configuration.TestSecurityConfiguration;
import kg.kuban.airport.dto.AuthDto;
import kg.kuban.airport.dto.TokenResponseDto;
import kg.kuban.airport.entity.AppUserTest;
import kg.kuban.airport.repository.*;
import kg.kuban.airport.security.JwtTokenHandler;
import kg.kuban.airport.service.AppUserDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.main.allow-bean-definition-overriding=true")
//@Import(TestSecurityConfiguration.class)

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "spring.main.allow-bean-definition-overriding=true"
)
@ContextConfiguration(classes = TestSecurityConfiguration.class)
@TestPropertySource(value = "classpath:app-test.properties")
@Import(TestSecurityConfiguration.class)
class AuthControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    JwtTokenHandler jwtTokenHandler;

//    @MockBean
//    private AppUserDetailsService appUserDetailsService;

    @Autowired
    private AppUserDetailsService appUserDetailsService;

    @LocalServerPort
    private int port;


    @Test
    void testLogin_OK() {
        AuthDto requestDto = new AuthDto();

        String login = "CUSTOMER";

        requestDto.setLogin(login);
        requestDto.setPassword("CUSTOMER");

        Mockito
                .when(this.appUserDetailsService.loadUserByUsername(login))
                .thenAnswer(invocationOnMock -> AppUserTest.getAppUserTest());

        try {
            URI uri = new URI( "http://localhost:" + port + "/v1/auth/login");

            TokenResponseDto response = this.testRestTemplate.postForObject(
                    uri,
                    requestDto,
                    TokenResponseDto.class
            );

            String usernameFromJwtToken = this.jwtTokenHandler.getUsernameFromToken(response.getAccessToken());
            Assertions.assertEquals(login, usernameFromJwtToken);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }
}