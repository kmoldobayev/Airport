package kg.kuban.airport.dto;

import kg.kuban.airport.enums.PartType;

import java.util.List;

public class PartTypesResponseDto {
    private List<PartType> partTypeList;

    public PartTypesResponseDto() {
    }

    public List<PartType> getPartTypeList() {
        return partTypeList;
    }

    public PartTypesResponseDto setPartTypeList(List<PartType> partTypeList) {
        this.partTypeList = partTypeList;
        return this;
    }
}
