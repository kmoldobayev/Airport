package kg.kuban.airport.dto;

public class CustomerResponseDto {
    private Long id;                     // Уникальный идентификатор
    private String userLogin;
    private String fullName;

    public Long getId() {
        return id;
    }

    public CustomerResponseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public CustomerResponseDto setUserLogin(String userLogin) {
        this.userLogin = userLogin;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public CustomerResponseDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }
}
