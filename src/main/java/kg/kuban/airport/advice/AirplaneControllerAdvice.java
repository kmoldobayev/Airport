package kg.kuban.airport.advice;

import kg.kuban.airport.controller.v1.AirplaneController;
import kg.kuban.airport.controller.v1.CustomerController;
import kg.kuban.airport.exception.*;
import kg.kuban.airport.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = AirplaneController.class)
public class AirplaneControllerAdvice {
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException e) {
        return new ErrorResponse().setHttpStatus(HttpStatus.BAD_REQUEST).setMessage(e.getMessage());
    }

    @ExceptionHandler(value = AirplaneNotFoundException.class)
    public ErrorResponse handleAircraftNotFoundException(AirplaneNotFoundException e) {
        return new ErrorResponse().setHttpStatus(HttpStatus.BAD_REQUEST).setMessage(e.getMessage());
    }

    @ExceptionHandler(value = EngineerIsBusyException.class)
    public ErrorResponse handleEngineerIsBusyException(EngineerIsBusyException e) {
        return new ErrorResponse().setHttpStatus(HttpStatus.BAD_REQUEST).setMessage(e.getMessage());
    }

    @ExceptionHandler(value = AppUserNotFoundException.class)
    public ErrorResponse handleApplicationUserNotFoundException(AppUserNotFoundException e) {
        return new ErrorResponse().setHttpStatus(HttpStatus.BAD_REQUEST).setMessage(e.getMessage());
    }

    @ExceptionHandler(value = StatusChangeException.class)
    public ErrorResponse handleStatusChangeException(StatusChangeException e) {
        return new ErrorResponse().setHttpStatus(HttpStatus.BAD_REQUEST).setMessage(e.getMessage());
    }

    @ExceptionHandler(value = IncorrectFiltersException.class)
    public ErrorResponse handleIncorrectDataFiltersException(IncorrectFiltersException e) {
        return new ErrorResponse().setHttpStatus(HttpStatus.BAD_REQUEST).setMessage(e.getMessage());
    }

    @ExceptionHandler(value = AirplanePartCheckupsNotFoundException.class)
    public ErrorResponse handlePartInspectionsNotFoundException(AirplanePartCheckupsNotFoundException e) {
        return new ErrorResponse().setHttpStatus(HttpStatus.BAD_REQUEST).setMessage(e.getMessage());
    }

    @ExceptionHandler(value = WrongEngineerException.class)
    public ErrorResponse handleWrongEngineerException(WrongEngineerException e) {
        return new ErrorResponse().setHttpStatus(HttpStatus.BAD_REQUEST).setMessage(e.getMessage());
    }

    @ExceptionHandler(value = IncompatiblePartException.class)
    public ErrorResponse handleIncompatiblePartException(IncompatiblePartException e) {
        return new ErrorResponse().setHttpStatus(HttpStatus.BAD_REQUEST).setMessage(e.getMessage());
    }

    @ExceptionHandler(value = IllegalAirplaneException.class)
    public ErrorResponse handleWrongAircraftException(IllegalAirplaneException e) {
        return new ErrorResponse().setHttpStatus(HttpStatus.BAD_REQUEST).setMessage(e.getMessage());
    }
}
