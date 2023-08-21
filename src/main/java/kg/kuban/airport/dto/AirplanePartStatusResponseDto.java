package kg.kuban.airport.dto;

import kg.kuban.airport.enums.AirplanePartStatus;

import java.util.List;

public class AirplanePartStatusResponseDto {
    private List<AirplanePartStatus> partStatusList;

    public List<AirplanePartStatus> getPartStatusList() {
        return partStatusList;
    }

    public AirplanePartStatusResponseDto setPartStatusList(List<AirplanePartStatus> partStatusList) {
        this.partStatusList = partStatusList;
        return this;
    }
}
