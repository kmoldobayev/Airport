package kg.kuban.airport.dto;

public class AppUserRequestDto {
    private String userLogin;

    private PositionResponseDto position;

    public AppUserRequestDto() {
    }

    public String getUserLogin() {
        return userLogin;
    }

    public AppUserRequestDto setUserLogin(String userLogin) {
        this.userLogin = userLogin;
        return this;
    }

    public PositionResponseDto getPosition() {
        return position;
    }

    public AppUserRequestDto setPosition(PositionResponseDto position) {
        this.position = position;
        return this;
    }
}
