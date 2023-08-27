package kg.kuban.airport.dto;

import java.time.LocalDateTime;

import static kg.kuban.airport.entity.AppUserTest.*;

public class CustomerTestDto {
    public static CustomerRequestDto getTestCustomerRequestDto() {
        return new CustomerRequestDto()
                .setUserLogin(CLIENT_USERNAME)
                .setUserPassword(CLIENT_RAW_PASSWORD)
                .setFullName(CLIENT_FULL_NAME);
    }
}
