package kg.kuban.airport.dto;

import kg.kuban.airport.entity.AppUser;

import java.util.List;

public class EmployeeReportResponseDto {

    //private Long id;

    private String fullName;
    private PositionResponseDto position;

    private Integer countAll;
    private Integer countEnabledUsers;
    private Integer countDismissedUsers;

    private List<AppUser> appUserAll;
    private List<AppUser> appUserEnabled;
    private List<AppUser> appUserDismissed;



    public EmployeeReportResponseDto() {
    }

//    public Long getId() {
//        return id;
//    }
//
//    public EmployeeReportResponseDto setId(Long id) {
//        this.id = id;
//        return this;
//    }

    public String getFullName() {
        return fullName;
    }

    public EmployeeReportResponseDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public PositionResponseDto getPosition() {
        return position;
    }

    public EmployeeReportResponseDto setPosition(PositionResponseDto position) {
        this.position = position;
        return this;
    }

    public Integer getCountAll() {
        return countAll;
    }

    public EmployeeReportResponseDto setCountAll(Integer countAll) {
        this.countAll = countAll;
        return this;
    }

    public Integer getCountEnabledUsers() {
        return countEnabledUsers;
    }

    public EmployeeReportResponseDto setCountEnabledUsers(Integer countEnabledUsers) {
        this.countEnabledUsers = countEnabledUsers;
        return this;
    }

    public Integer getCountDismissedUsers() {
        return countDismissedUsers;
    }

    public EmployeeReportResponseDto setCountDismissedUsers(Integer countDismissedUsers) {
        this.countDismissedUsers = countDismissedUsers;
        return this;
    }

    public List<AppUser> getAppUserAll() {
        return appUserAll;
    }

    public EmployeeReportResponseDto setAppUserAll(List<AppUser> appUserAll) {
        this.appUserAll = appUserAll;
        return this;
    }

    public List<AppUser> getAppUserEnabled() {
        return appUserEnabled;
    }

    public EmployeeReportResponseDto setAppUserEnabled(List<AppUser> appUserEnabled) {
        this.appUserEnabled = appUserEnabled;
        return this;
    }

    public List<AppUser> getAppUserDismissed() {
        return appUserDismissed;
    }

    public EmployeeReportResponseDto setAppUserDismissed(List<AppUser> appUserDismissed) {
        this.appUserDismissed = appUserDismissed;
        return this;
    }
}
