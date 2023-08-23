package kg.kuban.airport.advice;

import kg.kuban.airport.controller.v1.AppUserController;
import kg.kuban.airport.exception.InvalidCredentialsException;
import kg.kuban.airport.response.ErrorResponse;
import org.springframework.http.HttpStatus;
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
}
