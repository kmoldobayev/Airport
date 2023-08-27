package kg.kuban.airport.controller.v1;

import kg.kuban.airport.configuration.TestSecurityConfiguration;
import kg.kuban.airport.dto.AirplaneRequestDto;
import kg.kuban.airport.dto.AirplaneResponseDto;
import kg.kuban.airport.entity.*;
import kg.kuban.airport.enums.AirplaneStatus;
import kg.kuban.airport.enums.AirplaneType;
import kg.kuban.airport.repository.AirplanePartRepository;
import kg.kuban.airport.repository.AirplaneRepository;
import kg.kuban.airport.security.JwtTokenHandler;
import kg.kuban.airport.service.AirplanePartService;
import kg.kuban.airport.service.AppUserDetailsService;
import kg.kuban.airport.service.SeatService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.main.allow-bean-definition-overriding=true")
@ContextConfiguration(classes = TestSecurityConfiguration.class)
@TestPropertySource(value = "classpath:app-test.properties")
@Import(TestSecurityConfiguration.class)
class AirplaneControllerTest {

    private static final Long AirplaneId = 1L;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static final AirplaneType searchedAirplaneType = AirplaneType.BOEING;
    private static final AirplaneStatus searchedAirplaneStatus = AirplaneStatus.SERVICEABLE;
    private static final LocalDateTime startDate = LocalDateTime.parse("2020-02-12T23:40:00", formatter);
    private static final LocalDateTime endDate = LocalDateTime.parse("2021-02-12T23:40:00", formatter);

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private AirplaneRepository airplaneRepository;

    @Autowired
    private AirplanePartRepository airplanePartRepository;

    @MockBean
    private AirplanePartService airplanePartService;

    @MockBean
    private SeatService seatService;

    @Autowired
    UserDetailsService appUserDetailsService;

    @Autowired
    JwtTokenHandler jwtTokenHandler;

    @MockBean
    private Authentication authentication;
    @MockBean
    private SecurityContext securityContext;

    HttpHeaders headers;
    public PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(8);



    @BeforeEach
    public void setup() {
        UserDetails userDetails = this.appUserDetailsService.loadUserByUsername("DISPATCHER");
        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(userDetails, null, userDetails.getAuthorities());
        String token = this.jwtTokenHandler.generateToken(authentication);

        this.headers = new HttpHeaders();
        this.headers.setContentType(MediaType.APPLICATION_JSON);
        this.headers.set("Authorization", "Bearer " + token);
    }

    private List<AirplanePart> createPartsList(Airplane airplane) {
        List<AirplanePart> airplaneParts = List.of(
                new AirplanePart()
                        .setId(1L)
                        .setTitle("part1")
                        .setAirplaneType(AirplaneType.BOEING)
                        .setDateRegister(LocalDateTime.now()),
                new AirplanePart()
                        .setId(2L)
                        .setTitle("part2")
                        .setAirplaneType(AirplaneType.BOEING)
                        .setDateRegister(LocalDateTime.now()),
                new AirplanePart()
                        .setId(3L)
                        .setTitle("part3")
                        .setAirplaneType(AirplaneType.BOEING)
                        .setDateRegister(LocalDateTime.now())
        );

        return airplaneParts
                .stream()
                .peek(airplanePart -> airplanePart.getAirplanes().add(airplane))
                .collect(Collectors.toList());
    }

    private List<Seat> createAirplaneSeats(Airplane Airplane) {
        return List.of(
                new Seat()
                        .setSeatNumber(1)
                        .setOccupied(Boolean.FALSE)
                        .setAirplane(Airplane)
        );
    }
    private Airplane createAirplane() {
        Airplane airplane = new Airplane();
        return airplane
                .setId(1L)
                .setBoardNumber("airplane1")
                .setParts(this.createPartsList(airplane))
                .setAirplaneSeats(this.createAirplaneSeats(airplane))
                .setMarka(searchedAirplaneType)
                .setDateRegister(LocalDateTime.now());
    }

    public AppUser getAppUserTest() {
        return new AppUser()
                .setId(1L)
                .setUserLogin("DISPATCHER")
                .setUserPassword(passwordEncoder.encode("DISPATCHER"))
                .setFullName("Тестовый диспетчер")
                .setPosition(PositionTest.getTestPosition(
                        PositionTest.DISPATCHER_POSITION_ID
                ))
                .setEnabled(Boolean.TRUE)
                .setDateBegin(LocalDate.now());
    }

    @Test
    public void testRegisterNewAirplane_OK() throws URISyntaxException {
//        try {
            Airplane airplane = this.createAirplane();
            Mockito
                    .when(this.seatService.generateSeats(Mockito.eq(1)))
                    .thenReturn(airplane.getAirplaneSeats());
//            Mockito
//                    .when(
//                            this.airplanePartService.getPartByPartsIdListAndAirplaneType(
////                                    Mockito.eq(List.of(1L, 2L, 3L)),
////                                    Mockito.eq(AirplaneType.BOEING)
//                                    List.of(1L, 2L, 3L),
//                                    AirplaneType.BOEING
//                            )
//                    )
//                    .thenReturn(airplane.getParts());
            Mockito
                    .when(this.airplaneRepository.save(Mockito.eq(airplane)))
                    .thenReturn(airplane.setStatus(AirplaneStatus.TO_CHECKUP));

            String login = "DISPATCHER";
//            Mockito
//                    .when(this.appUserDetailsService.loadUserByUsername(login))
//                    .thenAnswer(invocationOnMock -> this.getAppUserTest());


        AppUserDetailsService appUserDetailsServiceMock = Mockito.mock(AppUserDetailsService.class);
        Mockito
                .when(appUserDetailsServiceMock.loadUserByUsername(login))
                .thenAnswer(invocationOnMock -> this.getAppUserTest());

            URI uri = new URI( "http://localhost:" + port + "/airplanes/register");

            AirplaneRequestDto airplaneRequestDto = new AirplaneRequestDto();
            airplaneRequestDto
                    .setMarka(AirplaneType.BOEING)
                    .setPartIdList(List.of(1L, 2L, 3L))
                    .setNumberSeats(1)
                    .setBoardNumber("airplane1");

            ResponseEntity<AirplaneResponseDto> response =
                    testRestTemplate.exchange(
                            uri,
                            HttpMethod.POST,
                            new HttpEntity<>(airplaneRequestDto, this.headers),
                            AirplaneResponseDto.class
                    );
            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

            AirplaneResponseDto responseDto = response.getBody();
            Assertions.assertEquals(1L, responseDto.getId());
            Assertions.assertEquals(airplaneRequestDto.getBoardNumber(), responseDto.getBoardNumber());
            Assertions.assertEquals(airplaneRequestDto.getMarka(), responseDto.getMarka());
            Assertions.assertEquals(AirplaneStatus.TO_CHECKUP, responseDto.getAirplaneStatus());
//        } catch (Exception e) {
//            Assertions.fail(e.getMessage());
//        }
    }
}