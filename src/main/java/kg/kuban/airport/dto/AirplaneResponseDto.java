package kg.kuban.airport.dto;

import kg.kuban.airport.entity.AirplaneType;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

public class AirplaneResponseDto {
    private Long id;                     // Уникальный Идентификатор

    private String model;                   // Модель самолета

    private AirplaneType marka;              // Тип самолета (например, "боинг", "эйрбас", "суперджет" и т.д.) - тип самолета определяет его грузоподъемность, дальность полета, скорость и другие характеристики, которые важны для диспетчерской службы.

    private Integer boardNumber;

    private LocalDateTime dateRegister;

    public Long getId() {
        return id;
    }

    public AirplaneResponseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getModel() {
        return model;
    }

    public AirplaneResponseDto setModel(String model) {
        this.model = model;
        return this;
    }

    public AirplaneType getMarka() {
        return marka;
    }

    public AirplaneResponseDto setMarka(AirplaneType marka) {
        this.marka = marka;
        return this;
    }

    public Integer getBoardNumber() {
        return boardNumber;
    }

    public AirplaneResponseDto setBoardNumber(Integer boardNumber) {
        this.boardNumber = boardNumber;
        return this;
    }

    public LocalDateTime getDateRegister() {
        return dateRegister;
    }

    public AirplaneResponseDto setDateRegister(LocalDateTime dateRegister) {
        this.dateRegister = dateRegister;
        return this;
    }
}
