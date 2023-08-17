package kg.kuban.airport.dto;

public class PositionRequestDto {
    private Long id;
    private String title;

    public PositionRequestDto() {
    }

    public Long getId() {
        return id;
    }

    public PositionRequestDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PositionRequestDto setTitle(String title) {
        this.title = title;
        return this;
    }
}
