package kg.kuban.airport.controller.v1;

import kg.kuban.airport.configuration.TestSecurityConfiguration;
import kg.kuban.airport.dto.CustomerRequestDto;
import kg.kuban.airport.dto.CustomerResponseDto;
import kg.kuban.airport.dto.CustomerTestDto;
import kg.kuban.airport.entity.AppUser;
import kg.kuban.airport.entity.AppUserTest;
import kg.kuban.airport.repository.AppRoleRepository;
import kg.kuban.airport.repository.AppUserRepository;
import kg.kuban.airport.security.JwtTokenHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManagerFactory;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "spring.main.allow-bean-definition-overriding=true"
)
@ContextConfiguration(classes = TestSecurityConfiguration.class)
@TestPropertySource(value = "classpath:app-test.properties")
@Import(TestSecurityConfiguration.class)
class CustomerControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    JwtTokenHandler jwtTokenHandler;

    @MockBean
    private AppUserRepository appUserRepository;




    @LocalServerPort
    private int port;

    @Test
    void testRegisterCustomer_OK() {
        CustomerRequestDto requestDto = CustomerTestDto.getTestCustomerRequestDto();

        Mockito
                .when(this.appUserRepository.save(Mockito.any(AppUser.class)))
                .thenAnswer(invocationOnMock -> {
                    AppUser appUser = (AppUser) invocationOnMock.getArguments()[0];
                    appUser.setDateBegin(LocalDate.now());
                    return appUser.setId(AppUserTest.CLIENT_USER_ID);
                });

        try {
//            Mockito
//                    .doNothing()
//                    .when(this.applicationUserValidator)
//                    .validateUserRequestDto(Mockito.any(ApplicationUserRequestDto.class));

            URI uri = new URI( "http://localhost:" + port + "/customers/register");

            CustomerResponseDto responseDto = this.testRestTemplate.postForObject(
                    uri,
                    requestDto,
                    CustomerResponseDto.class
            );

            Assertions.assertEquals(requestDto.getUserLogin(), responseDto.getUserLogin());
            Assertions.assertEquals(requestDto.getFullName(), responseDto.getFullName());

        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }
}