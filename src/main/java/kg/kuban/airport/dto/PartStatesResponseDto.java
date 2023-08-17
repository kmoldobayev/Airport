package kg.kuban.airport.dto;

import kg.kuban.airport.enums.AirplanePartStatus;

import java.util.List;

public class PartStatesResponseDto {
    private List<AirplanePartStatus> partStatuses;

    public PartStatesResponseDto() {
    }

    public PartStatesResponseDto(List<AirplanePartStatus> partStatuses) {
        this.partStatuses = partStatuses;
    }

    public List<AirplanePartStatus> getPartStates() {
        return partStatuses;
    }

    public PartStatesResponseDto setPartStates(List<AirplanePartStatus> partStatuses) {
        this.partStatuses = partStatuses;
        return this;
    }
}
