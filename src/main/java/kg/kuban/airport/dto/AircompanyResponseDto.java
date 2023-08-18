package kg.kuban.airport.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DTO ответа Авиакомпания",
        title = "AircompanyResponseDto",
        description = "Описывает Data Transfer Object ответа авиакомпании в аэропорту",
        implementation = AircompanyResponseDto.class
)
public class AircompanyResponseDto {
    @Schema(description = "ID авиакомпании", required = true)
    private Long id;                     // Уникальный Идентификатор самолета числового типа
    @Schema(description = "Наименование авиакомпании", example = "ОАО Аэрофлот", required = true)
    private String title;

    public AircompanyResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public AircompanyResponseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public AircompanyResponseDto setTitle(String title) {
        this.title = title;
        return this;
    }
}
