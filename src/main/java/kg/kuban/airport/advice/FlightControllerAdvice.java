package kg.kuban.airport.advice;

import kg.kuban.airport.controller.v1.EmployeeController;
import kg.kuban.airport.controller.v1.FlightController;
import kg.kuban.airport.exception.*;
import kg.kuban.airport.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = FlightController.class)
public class FlightControllerAdvice {
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException e) {
        return new ErrorResponse().setHttpStatus(HttpStatus.BAD_REQUEST).setMessage(e.getMessage());
    }

    @ExceptionHandler(value = AirplaneNotFoundException.class)
    public ErrorResponse handleAirplaneNotFoundException(AirplaneNotFoundException e) {
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
    public ErrorResponse handleWrongAirplaneException(IllegalAirplaneException e) {
        return new ErrorResponse().setHttpStatus(HttpStatus.BAD_REQUEST).setMessage(e.getMessage());
    }

    @ExceptionHandler(value =  FlightNotFoundException.class)
    public ErrorResponse handleWrongAirplaneException( FlightNotFoundException e) {
        return new ErrorResponse().setHttpStatus(HttpStatus.BAD_REQUEST).setMessage(e.getMessage());
    }

    @ExceptionHandler(value =  UnavailableAirplaneException.class)
    public ErrorResponse handleUnavailableAirplaneException( UnavailableAirplaneException e) {
        return new ErrorResponse().setHttpStatus(HttpStatus.BAD_REQUEST).setMessage(e.getMessage());
    }


}
