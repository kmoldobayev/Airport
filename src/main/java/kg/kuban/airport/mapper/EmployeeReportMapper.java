package kg.kuban.airport.mapper;

import kg.kuban.airport.dto.CustomerResponseDto;
import kg.kuban.airport.dto.EmployeeReportResponseDto;
import kg.kuban.airport.dto.PositionRequestDto;
import kg.kuban.airport.entity.AppUser;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EmployeeReportMapper {
//    public static EmployeeReportResponseDto mapAppUserEntityToDto(AppUser appUser) {
//        MapstructProviderMapper mapper = Mappers.getMapper(MapstructProviderMapper.class);
//        CustomerResponseDto result = mapper.mapCustomerToDto(appUser);
//        return result;
//    }

    public static EmployeeReportResponseDto mapReportFilterToDto(
                                                                    AppUser appUser,
                                                                    Integer countEnabledUsers,
                                                                    Integer countDismissedUsers,
                                                                    Integer countAll) {
        EmployeeReportResponseDto employeeReportResponseDto = new EmployeeReportResponseDto();

//        employeeReportResponseDto.setFullName(fullName);
//        employeeReportResponseDto.setPosition(position);
//        employeeReportResponseDto.setId().setProviderServiceId(providerId);
//        paymentHistoryReportDto.setServiceId(serviceId);
//        paymentHistoryReportDto.setCredentials(credentials);
//
//        paymentHistoryReportDto.setCountAcceptedPayments(countAccepted);
//        paymentHistoryReportDto.setCountDeclinedPayments(countDeclined);
//        paymentHistoryReportDto.setCountAllPayments(countAll);
//
//        paymentHistoryReportDto.setSumAcceptedPayments(sumAccepted);
//        paymentHistoryReportDto.setSumDeclinedPayments(sumDeclined);
//        paymentHistoryReportDto.setSumAllPayments(sumAll);
//
//        paymentHistoryReportDto.setPaymentHistoryResponseDtoList(paymentHistoryResponseDtoList);
//
//
        return employeeReportResponseDto;
    }
}
