package kg.kuban.airport.dto;

public class AuthDto {
    private String login;
    private String password;

    private TokenResponseDto tokenResponseDto;

    public AuthDto() {
    }

    public String getLogin() {
        return login;
    }

    public AuthDto setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AuthDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public TokenResponseDto getTokenResponseDto() {
        return tokenResponseDto;
    }

    public AuthDto setTokenResponseDto(TokenResponseDto tokenResponseDto) {
        this.tokenResponseDto = tokenResponseDto;
        return this;
    }
}
