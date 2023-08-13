package kg.kuban.airport.controller.v1;

import kg.kuban.airport.dto.AppUserResponseDto;
import kg.kuban.airport.dto.AuthDto;
import kg.kuban.airport.dto.TokenResponseDto;
import kg.kuban.airport.exception.InvalidCredentialsException;
import kg.kuban.airport.security.JwtTokenHandler;
import org.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.main.allow-bean-definition-overriding=true")
class AppUserControllerTest {

    @Autowired
    TestRestTemplate template;

    @Autowired
    JwtTokenHandler jwtTokenHandler;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @LocalServerPort
    private int port;

    @BeforeAll
    public static void setup() {

    }

    private TokenResponseDto getAuthHeaderForUser(String login, String password) {
        try {
            AuthDto authRequest = new AuthDto();
            authRequest.setLogin(login);
            authRequest.setPassword(password);

            URI uri = new URI("http://localhost:" + port + "/v1/auth/login");

            TokenResponseDto authResponse = template.postForObject(uri.toString(), authRequest, TokenResponseDto.class);
            return authResponse;
        } catch(Exception ex) {
            Assertions.fail(ex);
        }
        return null;
    }

    public TokenResponseDto loginUser(String username, String password) throws InvalidCredentialsException {
        TokenResponseDto result = new TokenResponseDto();
        UserDetails user = this.userDetailsService.loadUserByUsername(username);
        if (this.passwordEncoder.matches(password, this.passwordEncoder.encode(user.getPassword()))) {
            Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(user, null, user.getAuthorities());
            String jwtToken = this.jwtTokenHandler.generateToken(authentication);
            result.setAccessToken(jwtToken);
            return result;
        }
        return null;
    }

    private String getCreateToken() throws InvalidCredentialsException{

        TokenResponseDto authResponse = loginUser("admin", "password");
        String tokenString = authResponse.getAccessToken();
        return tokenString;
    }



    @Test
    void testGetUsers_OK()  throws InvalidCredentialsException {
        TokenResponseDto authResponse = getAuthHeaderForUser("user", "password");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String token = getCreateToken();
        //String token = authResponse.getAccessToken();

        headers.set("Authorization", "Bearer " + token);

        ResponseEntity<List<AppUserResponseDto>> entity = template.exchange("/users/users",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<AppUserResponseDto>>() {}
        );

        Assertions.assertEquals(HttpStatus.OK, entity.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON, entity.getHeaders().getContentType());

        List<AppUserResponseDto> users = entity.getBody();
        Assertions.assertTrue(users.size() >= 1);
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