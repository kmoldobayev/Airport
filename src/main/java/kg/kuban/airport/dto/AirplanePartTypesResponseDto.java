package kg.kuban.airport.dto;

import kg.kuban.airport.enums.AirplanePartType;

import java.util.List;

public class AirplanePartTypesResponseDto {
    private List<AirplanePartType> partTypeList;

    public AirplanePartTypesResponseDto() {
    }

    public List<AirplanePartType> getPartTypeList() {
        return partTypeList;
    }

    public AirplanePartTypesResponseDto setPartTypeList(List<AirplanePartType> partTypeList) {
        this.partTypeList = partTypeList;
        return this;
    }
}
