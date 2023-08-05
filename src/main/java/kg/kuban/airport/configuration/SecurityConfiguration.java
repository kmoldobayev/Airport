package kg.kuban.airport.configuration;

import kg.kuban.airport.security.JwtAuthEntryPoint;
import kg.kuban.airport.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*
В данном примере, доступ к URL-адресам, начинающимся с "/system-settings/", "/user-roles/" и "/system-statistics/",
разрешен только для пользователей с ролью "ADMIN".
Аутентификация производится с использованием встроенного в памяти пользователя с именем "admin" и паролем "password".

Обратите внимание, что это только пример конфигурации Spring Security,
и в реальном проекте может потребоваться более сложная настройка, в зависимости от требований и контекста приложения.
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SecurityConfiguration(JwtAuthEntryPoint jwtAuthEntryPoint, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors();

        http
                .exceptionHandling()
                .authenticationEntryPoint(this.jwtAuthEntryPoint);

        http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .httpBasic().and()
                .authorizeRequests()
                .antMatchers("/**/login/**").permitAll()
                .antMatchers("/register/**").permitAll()
                .antMatchers("/swagger-ui.html",
                                        "/swagger-ui/**",
                                        "/swagger-resources/**",
                                        "/v3/api-docs/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/system-settings/**").hasRole("ADMIN")
                .antMatchers("/user-roles/**").hasRole("ADMIN")
                .antMatchers("/system-statistics/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
    @Bean
    public SecurityContext securityContext()  {
        return new SecurityContextImpl();
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .withUser("admin").password("{noop}password").roles("ADMIN");
//    }
//
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
