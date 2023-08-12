package kg.kuban.airport.dto;

public class CustomerRequestDto {
    private String fullName;
    private String userLogin;
    private String userPassword;

    public CustomerRequestDto() {
    }

    public String getFullName() {
        return fullName;
    }

    public CustomerRequestDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public CustomerRequestDto setUserLogin(String userLogin) {
        this.userLogin = userLogin;
        return this;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public CustomerRequestDto setUserPassword(String userPassword) {
        this.userPassword = userPassword;
        return this;
    }
}
