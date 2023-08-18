package kg.kuban.airport.service;

import kg.kuban.airport.dto.TokenResponseDto;
import kg.kuban.airport.exception.InvalidCredentialsException;
import org.springframework.security.core.context.SecurityContext;

public interface AuthService {
    TokenResponseDto loginUser(String username, String password) throws InvalidCredentialsException;
    void login(String username, String password) throws InvalidCredentialsException;

    SecurityContext getSecurityContext();
}
