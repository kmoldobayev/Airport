package kg.kuban.airport.configuration;

import kg.kuban.airport.security.JwtAuthenticationFilter;
import kg.kuban.airport.security.JwtTokenHandler;
import kg.kuban.airport.security.JwtTokenUtil;
import kg.kuban.airport.service.AppUserDetailsService;
import kg.kuban.airport.service.impl.AppUserDetailsServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@TestConfiguration
public class TestSecurityConfiguration {

    @Bean
    public UserDetailsService appUserDetailsService() {
        UserDetails user = User.builder()
                .username("CHIEF")
                .password("CHIEF")
                .roles("CHIEF")
                .build();
        UserDetails admin = User.builder()
                .username("ADMIN")
                .password("ADMIN")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
    @Bean
    public JwtTokenHandler jwtTokenHandler() {
        return new JwtTokenHandler();
    }
}
