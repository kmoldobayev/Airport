package kg.kuban.airport.dto;

import java.time.LocalDateTime;

public class EmployeeReportRequestDto {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private PositionRequestDto position;

    private String credentials;

    private Integer countAll;
    private Integer countEnabledUsers;
    private Integer countDismissedUsers;
}
