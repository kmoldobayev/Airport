package kg.kuban.airport.dto;

public class AppRoleRequestDto {
    private String title;
    private PositionRequestDto position;

    public AppRoleRequestDto() {
    }

    public String getTitle() {
        return title;
    }

    public AppRoleRequestDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public PositionRequestDto getPosition() {
        return position;
    }

    public AppRoleRequestDto setPosition(PositionRequestDto position) {
        this.position = position;
        return this;
    }
}
