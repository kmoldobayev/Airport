package kg.kuban.airport.configuration;

import kg.kuban.airport.repository.*;
import kg.kuban.airport.security.JwtAuthenticationFilter;
import kg.kuban.airport.security.JwtTokenHandler;
import kg.kuban.airport.security.JwtTokenUtil;
import kg.kuban.airport.service.AppUserDetailsService;
import kg.kuban.airport.service.impl.AppUserDetailsServiceImpl;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@TestConfiguration
@TestPropertySource(value = "classpath:app-test.properties")
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class TestSecurityConfiguration {

    @MockBean
    private EntityManagerFactory entityManagerFactory;

    @MockBean
    private AppUserRepository appUserRepository;

    @MockBean
    private AppRoleRepository appRoleRepository;

    @MockBean
    private AircompanyRepository aircompanyRepository;

    @MockBean
    private AirplaneRepository airplaneRepository;

    @MockBean
    private SeatRepository seatRepository;

    @MockBean
    private PositionRepository positionRepository;

    @MockBean
    private AirplanePartCheckupRepository airplanePartCheckupRepository;

    @MockBean
    private AirplanePartRepository airplanePartRepository;

    @MockBean
    private AirportRepository airportRepository;

    @MockBean
    private FlightRepository flightRepository;

    @MockBean
    private UserFlightRepository userFlightRepository;

    @MockBean
    private CustomerReviewRepository customerReviewRepository;

//    @MockBean
//    private AppUserDetailsService appUserDetailsService;

//    @MockBean
//    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public UserDetailsService appUserDetailsService() {
        UserDetails chief = User.builder()
                .username("CHIEF")
                .password("CHIEF")
                .roles("CHIEF")
                .build();
        UserDetails dispatcher = User.builder()
                .username("DISPATCHER")
                .password("DISPATCHER")
                .roles("DISPATCHER")
                .build();
        UserDetails admin = User.builder()
                .username("ADMIN")
                .password("ADMIN")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(chief, admin, dispatcher);
    }
    @Bean
    public JwtTokenHandler jwtTokenHandler() {
        return new JwtTokenHandler();
    }
}
