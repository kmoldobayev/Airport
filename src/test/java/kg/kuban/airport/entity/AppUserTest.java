package kg.kuban.airport.entity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

public class AppUserTest {
    public static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(8);

    public static final Long CLIENT_USER_ID = 1L;
    public static final String CLIENT_USERNAME = "CUSTOMER";
    public static final String CLIENT_RAW_PASSWORD = "CUSTOMER";
    public static final String CLIENT_FULL_NAME = "Test Customer Full Name";

    public static final String ADMIN_FULL_NAME = "Test Admin Full Name";

    public static AppUser getAppUserTest() {
        return new AppUser()
                .setId(CLIENT_USER_ID)
                .setUserLogin(CLIENT_USERNAME)
                .setUserPassword(passwordEncoder.encode(CLIENT_RAW_PASSWORD))
                .setFullName(CLIENT_FULL_NAME)
                //.setAuthorities(AppRoleTest.getTestClientRoleTestEntityByRoleTitle(
                //       AppRoleTest.CLIENT_ROLE_TITLE
                //))
                .setPosition(PositionTest.getTestPosition(
                        PositionTest.CLIENT_POSITION_ID
                ))
                .setEnabled(Boolean.TRUE)
                .setDateBegin(LocalDate.now());
    }

//    public static AppUser getTestAdminsEntity() {
//        return new AppUser()
//                .setId(TestCredentialsProvider.ADMIN_ID)
//                .setUsername(TestCredentialsProvider.ADMIN_USERNAME)
//                .setPassword(passwordEncoder.encode(TestCredentialsProvider.ADMIN_RAW_PASSWORD))
//                .setFullName(ADMIN_FULL_NAME)
//                .setUserRolesEntityList(UserRolesTestEntityProvider.getTestClientRoleTestEntityByRoleTitle(
//                        UserRolesTestEntityProvider.ADMIN_ROLE_TITLE
//                ))
//                .setUserPosition(UserPositionsTestEntityProvider.getTestUserPositionsEntity(
//                        UserPositionsTestEntityProvider.SYSTEM_ADMINISTRATOR_POSITION_ID
//                ))
//                .setEnabled(Boolean.TRUE)
//                .setRegisteredAt(LocalDate.now());
//    }
}
