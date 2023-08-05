package kg.kuban.airport.dto;

public class TokenResponseDto {
    private String accessToken;

    public TokenResponseDto() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public TokenResponseDto setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }
}
