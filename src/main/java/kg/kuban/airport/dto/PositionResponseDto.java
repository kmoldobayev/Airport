package kg.kuban.airport.dto;

public class PositionResponseDto {
    private Long id;                     // Уникальный идентификатор
    private String title;

    public PositionResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public PositionResponseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PositionResponseDto setTitle(String title) {
        this.title = title;
        return this;
    }
}
