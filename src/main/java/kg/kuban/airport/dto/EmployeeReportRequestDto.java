package kg.kuban.airport.dto;

import java.time.LocalDateTime;

public class EmployeeReportRequestDto {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String fullName;
    private PositionRequestDto position;

    public EmployeeReportRequestDto() {
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public EmployeeReportRequestDto setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public EmployeeReportRequestDto setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public EmployeeReportRequestDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public PositionRequestDto getPosition() {
        return position;
    }

    public EmployeeReportRequestDto setPosition(PositionRequestDto position) {
        this.position = position;
        return this;
    }
}
