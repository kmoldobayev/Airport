package kg.kuban.airport.dto;

import kg.kuban.airport.enums.Mark;

public class CustomerReviewRequestDto {
    private String review;
    private Long flightId;
    private Mark mark;

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

    public Mark getMark() {
        return mark;
    }

    public CustomerReviewRequestDto setMark(Mark mark) {
        this.mark = mark;
        return this;
    }
}
