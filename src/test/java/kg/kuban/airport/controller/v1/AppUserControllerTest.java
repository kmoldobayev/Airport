package kg.kuban.airport.controller.v1;

import kg.kuban.airport.configuration.TestSecurityConfiguration;
import kg.kuban.airport.dto.AppUserResponseDto;
import kg.kuban.airport.dto.AuthDto;
import kg.kuban.airport.dto.TokenResponseDto;
import kg.kuban.airport.exception.InvalidCredentialsException;
import kg.kuban.airport.security.JwtTokenHandler;
import kg.kuban.airport.security.JwtTokenUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
import org.springframework.security.test.context.support.WithUserDetails;
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

    @Autowired
    JwtTokenUtil jwtTokenUtil;

//    @Autowired
//    UserDetailsService userDetailsService;

    @Autowired
    UserDetailsService appUserDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @LocalServerPort
    private int port;

    @BeforeAll
    public static void setup() {

    }

    private String generateToken(String username, String role) {
        UserDetails userDetails = this.appUserDetailsService.loadUserByUsername(username);
        //Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(userDetails, null, userDetails.getAuthorities());

        return this.jwtTokenUtil.generateToken(userDetails, role);
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

    private TokenResponseDto getAuthHeaderForUser2(String login, String password) {
        try {
            AuthDto authRequest = new AuthDto();
            authRequest.setLogin(login);
            authRequest.setPassword(password);

            URI uri = new URI("http://localhost:" + port + "/v1/auth/login");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<AuthDto> request = new HttpEntity<>(authRequest, headers);

            ResponseEntity<TokenResponseDto> authResponse = template.exchange(uri.toString(), HttpMethod.POST, request, TokenResponseDto.class);

            return authResponse.getBody();
        } catch(Exception ex) {
            Assertions.fail(ex);
        }
        return null;
    }

    public TokenResponseDto loginUser(String username, String password) throws InvalidCredentialsException {
        TokenResponseDto result = new TokenResponseDto();
        UserDetails user = this.appUserDetailsService.loadUserByUsername(username);
        if (this.passwordEncoder.matches(password, this.passwordEncoder.encode(user.getPassword()))) {
            Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(user, null, user.getAuthorities());
            String jwtToken = this.jwtTokenHandler.generateToken(authentication);
            result.setAccessToken(jwtToken);
            return result;
        }
        return null;
    }
    private String getCreateToken() throws InvalidCredentialsException{
        TokenResponseDto authResponse = loginUser("ADMIN", "ADMIN");
        String tokenString = authResponse.getAccessToken();
        return tokenString;
    }
    @Test
   // @WithUserDetails(value="ADMIN", userDetailsServiceBeanName="appUserDetailsService")
    void testGetUsers_OK()  throws InvalidCredentialsException {
        try {
            //TokenResponseDto authResponse = getAuthHeaderForUser2("ADMIN2", "ADMIN2");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            //String token = getCreateToken();
            //String token = authResponse.getAccessToken();
            String token = generateToken("ADMIN", "ADMIN");
            headers.set("Authorization", "Bearer " + token);

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