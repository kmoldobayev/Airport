package kg.kuban.airport.dto;

public class AppUserRequestDto {
    private String userLogin;
    private String userPassword;

    private PositionRequestDto position;


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

    public String getUserPassword() {
        return userPassword;
    }

    public AppUserRequestDto setUserPassword(String userPassword) {
        this.userPassword = userPassword;
        return this;
    }
}
