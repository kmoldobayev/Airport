package kg.kuban.airport.advice;

import kg.kuban.airport.controller.v1.UserFlightController;
import kg.kuban.airport.exception.NotEnoughRolesForCrewRegistrationException;
import kg.kuban.airport.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = UserFlightController.class)
public class UserFlightControllerAdvice {
    @ExceptionHandler(value = NotEnoughRolesForCrewRegistrationException.class)
    public ErrorResponse handleNotEnoughRolesForCrewRegistrationException(
            NotEnoughRolesForCrewRegistrationException e
    ) {
        return new ErrorResponse().setHttpStatus(HttpStatus.BAD_REQUEST).setMessage(e.getMessage());
    }
}
