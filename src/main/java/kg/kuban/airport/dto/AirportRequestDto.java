package kg.kuban.airport.dto;

public class AirportRequestDto {
    private String title;

    public AirportRequestDto() {
    }

    public String getTitle() {
        return title;
    }

    public AirportRequestDto setTitle(String title) {
        this.title = title;
        return this;
    }
}
