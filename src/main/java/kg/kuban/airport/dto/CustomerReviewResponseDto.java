package kg.kuban.airport.dto;

import java.time.LocalDateTime;

public class CustomerReviewResponseDto {
    private Long id;
    private String review;
    private LocalDateTime dateRegister;
    private Long clientId;
    private Long flightId;

    public CustomerReviewResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public CustomerReviewResponseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getReview() {
        return review;
    }

    public CustomerReviewResponseDto setReview(String review) {
        this.review = review;
        return this;
    }

    public LocalDateTime getDateRegister() {
        return dateRegister;
    }

    public CustomerReviewResponseDto setDateRegister(LocalDateTime dateRegister) {
        this.dateRegister = dateRegister;
        return this;
    }

    public Long getClientId() {
        return clientId;
    }

    public CustomerReviewResponseDto setClientId(Long clientId) {
        this.clientId = clientId;
        return this;
    }

    public Long getFlightId() {
        return flightId;
    }

    public CustomerReviewResponseDto setFlightId(Long flightId) {
        this.flightId = flightId;
        return this;
    }
}
