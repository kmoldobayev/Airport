package kg.kuban.airport.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class TestConfiguration {
//    @Autowired
//    private TestUserRepository testUserRepository;

//    @Bean
//    public PasswordEncoder passwordEncoderTest() {
//        return new BCryptPasswordEncoder();
//    }

//    @Bean
//    public void createTestUser() {
//        TestUser testUser = new TestUser();
//        testUser.setId(1L);
//        testUser.setLogin("testuser");
//        testUser.setPassword(passwordEncoderTest().encode("password"));
//        testUser.setRole("ROLE_USER");
//        testUserRepository.save(testUser);
//    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                //.password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
                .password("password")
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                //.password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
                .password("password")
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}
