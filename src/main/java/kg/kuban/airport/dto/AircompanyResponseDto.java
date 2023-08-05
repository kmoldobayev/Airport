package kg.kuban.airport.dto;

import javax.persistence.Column;

public class AircompanyResponseDto {
    private Long id;                     // Уникальный Идентификатор самолета числового типа
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
