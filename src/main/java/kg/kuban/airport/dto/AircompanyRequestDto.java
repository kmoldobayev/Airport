package kg.kuban.airport.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DTO запроса Авиакомпания",
        title = "AircompanyRequestDto",
        description = "Описывает Data Transfer Object авиакомпании в аэропорту",
        implementation = AircompanyRequestDto.class
)
public class AircompanyRequestDto {
    @Schema(description = "Наименование авиакомпании", example = "ОАО Аэрофлот", required = true)
    private String title;

    public AircompanyRequestDto() {
    }

    public String getTitle() {
        return title;
    }

    public AircompanyRequestDto setTitle(String title) {
        this.title = title;
        return this;
    }
}
