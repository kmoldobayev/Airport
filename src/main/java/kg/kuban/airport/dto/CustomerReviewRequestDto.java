package kg.kuban.airport.dto;

public class CustomerReviewRequestDto {
    private String review;
    private Long flightId;

    public CustomerReviewRequestDto() {
    }

    public String getReview() {
        return review;
    }

    public CustomerReviewRequestDto setReview(String review) {
        this.review = review;
        return this;
    }

    public Long getFlightId() {
        return flightId;
    }

    public CustomerReviewRequestDto setFlightId(Long flightId) {
        this.flightId = flightId;
        return this;
    }
}
