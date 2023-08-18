package kg.kuban.airport.dto;

import kg.kuban.airport.enums.AirplanePartType;

import java.util.List;

public class PartTypesResponseDto {
    private List<AirplanePartType> partTypeList;

    public PartTypesResponseDto() {
    }

    public List<AirplanePartType> getPartTypeList() {
        return partTypeList;
    }

    public PartTypesResponseDto setPartTypeList(List<AirplanePartType> partTypeList) {
        this.partTypeList = partTypeList;
        return this;
    }
}
