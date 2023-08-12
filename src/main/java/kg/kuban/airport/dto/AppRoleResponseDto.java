package kg.kuban.airport.dto;

public class AppRoleResponseDto {
    private Long id;                     // Уникальный идентификатор
    private String title;

    private PositionResponseDto position;

    public AppRoleResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public AppRoleResponseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public AppRoleResponseDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public PositionResponseDto getPosition() {
        return position;
    }

    public AppRoleResponseDto setPosition(PositionResponseDto position) {
        this.position = position;
        return this;
    }
}
