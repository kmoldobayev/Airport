package kg.kuban.airport.entity;

import java.util.ArrayList;
import java.util.List;

public class AppRoleTest {
    public static final Long CLIENT_ROLE_ID = 1L;
    public static final String CLIENT_ROLE_TITLE = "CLIENT";
    public static final Long ADMIN_ROLE_ID = 2L;
    public static final String ADMIN_ROLE_TITLE = "ADMIN";
    public static final Long STEWARD_ROLE_ID = 10L;
    public static final String STEWARD_ROLE_TITLE = "STEWARD";
    public static List<AppRole> getTestClientRoleTestEntityByRoleTitle(String roleTitle) {
        if(roleTitle.equals(CLIENT_ROLE_TITLE)) {
            return new ArrayList<>(
                    List.of(new AppRole().setId(CLIENT_ROLE_ID).setTitle(CLIENT_ROLE_TITLE))
            );
        }
        if(roleTitle.equals(ADMIN_ROLE_TITLE)) {
            return new ArrayList<>(
                    List.of(new AppRole().setId(ADMIN_ROLE_ID).setTitle(ADMIN_ROLE_TITLE))
            );
        }
        if(roleTitle.equals(STEWARD_ROLE_TITLE)) {
            return new ArrayList<>(
                    List.of(new AppRole().setId(STEWARD_ROLE_ID).setTitle(STEWARD_ROLE_TITLE))
            );
        }
        throw new RuntimeException("Роли с указанным названием не существует в системе!");
    }

    public static List<AppRole> getAllUserRolesTestEntities() {
        return List.of(
                new AppRole().setId(CLIENT_ROLE_ID).setTitle(CLIENT_ROLE_TITLE),
                new AppRole().setId(STEWARD_ROLE_ID).setTitle(STEWARD_ROLE_TITLE)
        );
    }
}
