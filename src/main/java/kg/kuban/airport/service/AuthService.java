package kg.kuban.airport.service;

import kg.kuban.airport.dto.TokenResponseDto;
import kg.kuban.airport.exception.InvalidCredentialsException;
import kg.kuban.airport.exception.LogoutException;
import kg.kuban.airport.security.JwtTokenHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class AuthService {
    private final SecurityContext securityContext;
    private final AppUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenHandler jwtTokenHandler;

    private final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    public AuthService(SecurityContext securityContext,
                       AppUserDetailsService userDetailsService,
                       PasswordEncoder passwordEncoder,
                       JwtTokenHandler jwtTokenHandler) {
        this.securityContext = securityContext;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenHandler = jwtTokenHandler;
    }

    public TokenResponseDto loginUser(String username, String password) throws InvalidCredentialsException {
        TokenResponseDto result = new TokenResponseDto();
        UserDetails user = this.userDetailsService.loadUserByUsername(username);
        if (this.passwordEncoder.matches(password, user.getPassword())) {
            Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(user, null, user.getAuthorities());
            String jwtToken = this.jwtTokenHandler.generateToken(authentication);
            result.setAccessToken(jwtToken);
            return result;
        }
        return null;
    }

    public void login(String username, String password) throws InvalidCredentialsException {
        logger.info("username=" + username);
        logger.info("password=" + password);


        // Проверить валидность логина и пароля
        if (Objects.isNull(username) || username.isEmpty()) {
            throw new InvalidCredentialsException("Логин не должен быть пустым!");
        }
        if (Objects.isNull(password) || password.isEmpty()) {
            throw new InvalidCredentialsException("Пароль не должен быть пустым!");
        }

        // Првоерить есть ли пользватель по такому логину
        UserDetails user = this.userDetailsService.loadUserByUsername(username);

        // Проверить пароль пользователя
        //if (!user.getPassword().equals(password)) {
        if (!this.passwordEncoder.matches(password, user.getPassword())) {
            System.out.println("Неверно введен пароль!!!!!!!!");
            throw new InvalidCredentialsException("Неверно введен пароль");
        }
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", user.getUsername());
        credentials.put("password", user.getPassword());

        logger.info("credentials username=" + user.getUsername());
        logger.info("credentials password=" + user.getPassword());

        try {
            // Создать из пользваоетля ситсемы объект Authentication
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, credentials);
            // Положить этот объект в SecurityContext
            this.securityContext.setAuthentication(token);
        } catch(BadCredentialsException e) {
            logger.info(e.getMessage());
        }

    }

    public void logout() throws Exception {
        // Есть ли авторизированный пользователь
        // Обнулить Authentification SecurityContext

        if (Objects.isNull(this.securityContext.getAuthentication())) {
            throw new LogoutException("Нет залогированного пользователя!");
        }
    }

    public void doSmth() {
        System.out.println("doSmth");
        logger.info("doSmth");
    }
    public String saySmth() {

        return "hello world";
    }
}
