package kg.kuban.airport.advice;

import io.jsonwebtoken.JwtException;
import kg.kuban.airport.controller.v1.AppUserController;
import kg.kuban.airport.exception.InvalidCredentialsException;
import kg.kuban.airport.exception.UnauthorizedException;
import kg.kuban.airport.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice(basePackageClasses = AppUserController.class)
public class AppUserControllerAdvice {
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ErrorResponse handleIllegalArgument(IllegalArgumentException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
    }
    @ExceptionHandler(value = NoSuchElementException.class)
    public ErrorResponse handleNoSuchElement(NoSuchElementException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
    }
    @ExceptionHandler(value = InvalidCredentialsException.class)
    public ErrorResponse handleInvalidCredentials(InvalidCredentialsException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ErrorResponse handleInvalidCredentials(UsernameNotFoundException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
    }

    @ExceptionHandler(value = {JwtException.class})
    public ErrorResponse handleJwtException(JwtException ex) {
        return new ErrorResponse(HttpStatus.UNAUTHORIZED, "Ошибка аутентификации: " + ex.getMessage());
    }

    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Ошибка аутентификации: " + ex.getMessage());
    }

    @ExceptionHandler(value = {UnauthorizedException.class})
    public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex) {
        // Обработка и возврат ResponseEntity с соответствующим кодом состояния и сообщением об ошибке
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

}
