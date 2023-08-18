package kg.kuban.airport.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kg.kuban.airport.enums.UserFlightsStatus;

import java.time.LocalDateTime;

public class UserFlightRegistrationResponseDto {
    private Long id;
    private UserFlightsStatus userStatus;
    private LocalDateTime registeredAt;
    private Long employeeId;
    private String employeeFullName;
    private String employeePositionTitle;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer seatNumber;
    private Long flightId;
    private AirportResponseDto flightDestination;

    public UserFlightRegistrationResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public UserFlightRegistrationResponseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public UserFlightsStatus getUserStatus() {
        return userStatus;
    }

    public UserFlightRegistrationResponseDto setUserStatus(UserFlightsStatus userStatus) {
        this.userStatus = userStatus;
        return this;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public UserFlightRegistrationResponseDto setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
        return this;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public UserFlightRegistrationResponseDto setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public String getEmployeeFullName() {
        return employeeFullName;
    }

    public UserFlightRegistrationResponseDto setEmployeeFullName(String employeeFullName) {
        this.employeeFullName = employeeFullName;
        return this;
    }

    public String getEmployeePositionTitle() {
        return employeePositionTitle;
    }

    public UserFlightRegistrationResponseDto setEmployeePositionTitle(String employeePositionTitle) {
        this.employeePositionTitle = employeePositionTitle;
        return this;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public UserFlightRegistrationResponseDto setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
        return this;
    }

    public Long getFlightId() {
        return flightId;
    }

    public UserFlightRegistrationResponseDto setFlightId(Long flightId) {
        this.flightId = flightId;
        return this;
    }

    public AirportResponseDto getFlightDestination() {
        return flightDestination;
    }

    public UserFlightRegistrationResponseDto setFlightDestination(AirportResponseDto flightDestination) {
        this.flightDestination = flightDestination;
        return this;
    }
}
