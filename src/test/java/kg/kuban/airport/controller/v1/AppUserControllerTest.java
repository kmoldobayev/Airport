package kg.kuban.airport.controller.v1;

import kg.kuban.airport.configuration.TestSecurityConfiguration;
import kg.kuban.airport.dto.AppUserResponseDto;
import kg.kuban.airport.dto.AuthDto;
import kg.kuban.airport.dto.TokenResponseDto;
import kg.kuban.airport.exception.InvalidCredentialsException;
import kg.kuban.airport.repository.AppRoleRepository;
import kg.kuban.airport.repository.AppUserRepository;
import kg.kuban.airport.security.JwtTokenHandler;
import kg.kuban.airport.security.JwtTokenUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URI;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.main.allow-bean-definition-overriding=true")
@Import(TestSecurityConfiguration.class)
class AppUserControllerTest {

    @Autowired
    TestRestTemplate template;

    @Autowired
    JwtTokenHandler jwtTokenHandler;

    //@MockBean
    @Autowired
    private AppUserRepository appUserRepository;
//    @MockBean
//    private AppRoleRepository appRoleRepository;
//    @MockBean
//    private UserPositionsEntityRepository userPositionsEntityRepository;
//    @MockBean
//    private UserDetailsService userDetailsService;

    @Autowired
    UserDetailsService appUserDetailsService;

    @LocalServerPort
    private int port;

    HttpHeaders headers;

    @BeforeEach
    public void setup() {
        UserDetails userDetails = this.appUserDetailsService.loadUserByUsername("ADMIN");
        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(userDetails, null, userDetails.getAuthorities());
        String token = this.jwtTokenHandler.generateToken(authentication);

        this.headers = new HttpHeaders();
        this.headers.setContentType(MediaType.APPLICATION_JSON);
        this.headers.set("Authorization", "Bearer " + token);
    }

    @Test
    void testGetUsers_OK()  throws InvalidCredentialsException {
        try {

            URI uri = new URI("http://localhost:" + port + "/users/users");

            ResponseEntity<List<AppUserResponseDto>> entity = template.exchange(uri,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    new ParameterizedTypeReference<List<AppUserResponseDto>>() {}
            );

            Assertions.assertEquals(HttpStatus.OK, entity.getStatusCode());
            Assertions.assertEquals(MediaType.APPLICATION_JSON, entity.getHeaders().getContentType());

            List<AppUserResponseDto> users = entity.getBody();
            Assertions.assertTrue(users.size() >= 1);
        } catch(Exception ex) {
            Assertions.fail(ex);
        }
    }

    @Test
    void getUserById() {
    }

    @Test
    void testCreateUser_OK() {

    }

    @Test
    void updateUser() {
    }

    @Test
    void dismissUser() {
    }

    @Test
    void getUserRoles() {
    }

    @Test
    void updateUserRole() {
    }

    @Test
    void createUserRole() {
    }

    @Test
    void getAllData() {
    }

    @Test
    void generateReports() {
    }
}