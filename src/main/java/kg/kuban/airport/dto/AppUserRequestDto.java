package kg.kuban.airport.dto;

import kg.kuban.airport.enums.UserStatus;

public class AppUserRequestDto {
    private String userLogin;

    private PositionRequestDto position;

    private UserStatus status;

    public AppUserRequestDto() {
    }

    public String getUserLogin() {
        return userLogin;
    }

    public AppUserRequestDto setUserLogin(String userLogin) {
        this.userLogin = userLogin;
        return this;
    }

    public PositionRequestDto getPosition() {
        return position;
    }

    public AppUserRequestDto setPosition(PositionRequestDto position) {
        this.position = position;
        return this;
    }

    public UserStatus getStatus() {
        return status;
    }

    public AppUserRequestDto setStatus(UserStatus status) {
        this.status = status;
        return this;
    }
}
