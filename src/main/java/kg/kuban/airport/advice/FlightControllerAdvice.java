package kg.kuban.airport.advice;

import kg.kuban.airport.controller.v1.EmployeeController;
import kg.kuban.airport.controller.v1.FlightController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = FlightController.class)
public class FlightControllerAdvice {
}
