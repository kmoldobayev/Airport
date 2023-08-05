package kg.kuban.airport.dto;

import java.time.LocalDateTime;

public class AppUserResponseDto {
    private Long id;                     // Уникальный идентификатор
    private String userLogin;

    private PositionResponseDto position;

    private LocalDateTime dateLastLogin;

}
