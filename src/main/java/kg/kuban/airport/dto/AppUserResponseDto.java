package kg.kuban.airport.dto;

public class AppUserResponseDto {
    private Long id;                     // Уникальный идентификатор
    private String fullName;
    private String userLogin;

    private PositionResponseDto position;

    public AppUserResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public AppUserResponseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public AppUserResponseDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public AppUserResponseDto setUserLogin(String userLogin) {
        this.userLogin = userLogin;
        return this;
    }

    public PositionResponseDto getPosition() {
        return position;
    }

    public AppUserResponseDto setPosition(PositionResponseDto position) {
        this.position = position;
        return this;
    }

}
