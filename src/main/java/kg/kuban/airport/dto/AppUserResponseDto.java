package kg.kuban.airport.dto;

import java.time.LocalDateTime;

public class AppUserResponseDto {
    private Long id;                     // Уникальный идентификатор
    private String userLogin;

    private PositionResponseDto position;

    private LocalDateTime dateLastLogin;

    public AppUserResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public AppUserResponseDto setId(Long id) {
        this.id = id;
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

    public LocalDateTime getDateLastLogin() {
        return dateLastLogin;
    }

    public AppUserResponseDto setDateLastLogin(LocalDateTime dateLastLogin) {
        this.dateLastLogin = dateLastLogin;
        return this;
    }
}
