package kg.kuban.airport.advice;

import io.jsonwebtoken.JwtException;
import kg.kuban.airport.controller.v1.UserFlightController;
import kg.kuban.airport.exception.UnauthorizedException;
import kg.kuban.airport.response.ErrorResponse;
import kg.kuban.airport.security.JwtAuthenticationFilter;
import kg.kuban.airport.security.JwtTokenHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//@RestControllerAdvice(basePackageClasses = {JwtAuthenticationFilter.class,  JwtTokenHandler.class})
@ControllerAdvice
public class JwtExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {JwtException.class})
    public ErrorResponse handleJwtException(JwtException ex) {
        return new ErrorResponse(HttpStatus.UNAUTHORIZED, "Ошибка аутентификации: " + ex.getMessage());
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ErrorResponse handleIllegalArgument(IllegalArgumentException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
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
