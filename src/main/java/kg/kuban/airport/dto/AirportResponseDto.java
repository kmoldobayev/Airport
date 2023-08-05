package kg.kuban.airport.dto;

public class AirportResponseDto {
    private Long id;                     // Уникальный идентификатор рейса
    private String title;
    private String city;

    public Long getId() {
        return id;
    }

    public AirportResponseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public AirportResponseDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getCity() {
        return city;
    }

    public AirportResponseDto setCity(String city) {
        this.city = city;
        return this;
    }
}
