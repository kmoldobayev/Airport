package kg.kuban.airport.mapper;

import kg.kuban.airport.dto.AppRoleResponseDto;
import kg.kuban.airport.dto.CustomerReviewRequestDto;
import kg.kuban.airport.dto.CustomerReviewResponseDto;
import kg.kuban.airport.entity.AppRole;
import kg.kuban.airport.entity.CustomerReview;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

public class CustomerReviewMapper {
    public static CustomerReviewResponseDto mapCustomerReviewToDto(CustomerReview customerReview) {
        MapstructProviderMapper mapper = Mappers.getMapper(MapstructProviderMapper.class);
        CustomerReviewResponseDto result = mapper.mapCustomerReviewToDto(customerReview);

        result.setClientId(customerReview.getAppUser().getId());
        result.setFlightId(customerReview.getFlight().getId());
        result.setMark(customerReview.getMark());

        return result;
    }

    public static CustomerReview mapCustomerDtoToEntity(CustomerReviewRequestDto customerReviewRequestDto) {
        MapstructProviderMapper mapper = Mappers.getMapper(MapstructProviderMapper.class);
        CustomerReview result = mapper.mapCustomerReviewDtoToEntity(customerReviewRequestDto);
        return result;
    }

    public static List<CustomerReviewResponseDto> mapCustomerReviewListToDto(List<CustomerReview> customerReviews) {
        MapstructProviderMapper mapper = Mappers.getMapper(MapstructProviderMapper.class);
        List<CustomerReviewResponseDto> result = mapper.mapListCustomerReviewToListDto(customerReviews);
        return result;
    }
}
