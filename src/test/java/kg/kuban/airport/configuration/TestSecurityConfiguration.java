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
                //.password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
                .password("CHIEF")
                .roles("CHIEF")
                .build();
        UserDetails admin = User.builder()
                .username("ADMIN")
                //.password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
                .password("ADMIN")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
//
//    public String getUsername() {
//        UserDetailsService userDetailsService = appUserDetailsService();
//        return userDetailsService.
//    }

    @Bean
    public JwtTokenHandler jwtTokenHandler() {
        return new JwtTokenHandler();
    }
//    @Bean
//    public JwtTokenUtil jwtTokenUtil() {
//        return new JwtTokenUtil();
//    }
//
//    private String parseJwt(HttpServletRequest request) {
//        final String authHeader = request.getHeader("Authorization");
//        if (Objects.nonNull(authHeader) && authHeader.startsWith("Bearer ")) {
//            return authHeader.substring(7);
//        }
//        return null;
//    }


//    @Bean
//    @Primary
//    public JwtAuthenticationFilter jwtAuthenticationFilter() {
//        UserDetails user = this.appUserDetailsService().loadUserByUsername("ADMIN");
//        UsernamePasswordAuthenticationToken authToken =
//                UsernamePasswordAuthenticationToken.authenticated(user, null, user.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authToken);
//        return new JwtAuthenticationFilter(this.jwtTokenHandler(), this.appUserDetailsServiceImpl());
//    }

//    @Bean
//    public AppUserDetailsService appUserDetailsServiceImpl() {
//        return new AppUserDetailsServiceImpl();
//    }
}
