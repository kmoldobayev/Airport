package kg.kuban.airport.response;

import org.springframework.http.HttpStatus;

public class SuccessResponse {
    private HttpStatus status;
    private String message;

    public SuccessResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public SuccessResponse setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public SuccessResponse setMessage(String message) {
        this.message = message;
        return this;
    }
}
