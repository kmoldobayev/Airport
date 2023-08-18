package kg.kuban.airport.exception;

public class NotRegisteredFlightException extends Exception{
    public NotRegisteredFlightException(String message) {
        super(message);
    }
}
