package kg.kuban.airport.entity;

import java.util.List;
import java.util.Objects;

public class PositionTest {
    public static final Long CLIENT_POSITION_ID = 10L;
    public static final String CLIENT_POSITION_TITLE = "CLIENT";
    public static final Long STEWARD_POSITION_ID = 9L;
    public static final String STEWARD_POSITION_TITLE = "STEWARD";
    public static final Long CHIEF_STEWARD_POSITION_ID = 8L;
    public static final String CHIEF_STEWARD_POSITION_TITLE = "CHIEF_STEWARD";
    public static final Long PILOT_POSITION_ID = 7L;
    public static final String PILOT_POSITION_TITLE = "PILOT";
    public static final Long ENGINEER_POSITION_ID = 6L;
    public static final String ENGINEER_POSITION_TITLE = "ENGINEER";
    public static final Long CHIEF_ENGINEER_POSITION_ID = 5L;
    public static final String CHIEF_ENGINEER_POSITION_TITLE = "CHIEF_ENGINEER";
    public static final Long DISPATCHER_POSITION_ID = 4L;
    public static final String DISPATCHER_POSITION_TITLE = "DISPATCHER";
    public static final Long CHIEF_DISPATCHER_POSITION_ID = 3L;
    public static final String CHIEF_DISPATCHER_POSITION_TITLE = "CHIEF_DISPATCHER";
    public static final Long AIRPORT_MANAGER_POSITION_ID = 2L;
    public static final String AIRPORT_MANAGER_POSITION_TITLE = "AIRPORT_MANAGER";
    public static final Long SYSTEM_ADMINISTRATOR_POSITION_ID = 1L;
    public static final String SYSTEM_ADMINISTRATOR_POSITION_TITLE = "SYSTEM_ADMINISTRATOR";

    public static Position getTestPosition(Long positionId) {
        if (Objects.isNull(positionId)) {
            throw new IllegalArgumentException("ID позиции пользователя не может быть null или пустым!");
        }

        if (positionId.equals(CLIENT_POSITION_ID)) {
            return new Position()
                    .setId(CLIENT_POSITION_ID)
                    .setTitle(CLIENT_POSITION_TITLE);
        }
        if (positionId.equals(STEWARD_POSITION_ID)) {
            return new Position()
                    .setId(STEWARD_POSITION_ID)
                    .setTitle(STEWARD_POSITION_TITLE);
        }
        if (positionId.equals(CHIEF_STEWARD_POSITION_ID)) {
            return new Position()
                    .setId(CHIEF_STEWARD_POSITION_ID)
                    .setTitle(CHIEF_STEWARD_POSITION_TITLE);
        }
        if (positionId.equals(PILOT_POSITION_ID)) {
            return new Position()
                    .setId(PILOT_POSITION_ID)
                    .setTitle(PILOT_POSITION_TITLE);
        }
        if (positionId.equals(ENGINEER_POSITION_ID)) {
            return new Position()
                    .setId(ENGINEER_POSITION_ID)
                    .setTitle(ENGINEER_POSITION_TITLE);
        }
        if (positionId.equals(CHIEF_ENGINEER_POSITION_ID)) {
            return new Position()
                    .setId(CHIEF_ENGINEER_POSITION_ID)
                    .setTitle(CHIEF_ENGINEER_POSITION_TITLE);
        }
        if (positionId.equals(DISPATCHER_POSITION_ID)) {
            return new Position()
                    .setId(DISPATCHER_POSITION_ID)
                    .setTitle(DISPATCHER_POSITION_TITLE);
        }
        if (positionId.equals(CHIEF_DISPATCHER_POSITION_ID)) {
            return new Position()
                    .setId(CHIEF_DISPATCHER_POSITION_ID)
                    .setTitle(CHIEF_DISPATCHER_POSITION_TITLE);
        }
        if (positionId.equals(AIRPORT_MANAGER_POSITION_ID)) {
            return new Position()
                    .setId(AIRPORT_MANAGER_POSITION_ID)
                    .setTitle(AIRPORT_MANAGER_POSITION_TITLE);
        }
        if (positionId.equals(SYSTEM_ADMINISTRATOR_POSITION_ID)) {
            return new Position()
                    .setId(SYSTEM_ADMINISTRATOR_POSITION_ID)
                    .setTitle(SYSTEM_ADMINISTRATOR_POSITION_TITLE);
        }
        throw new RuntimeException("Позиций пользователей с таким ID не существует в системе!");
    }

    public static List<Position> getAllTestEmployeesPositionsEntities() {
        return List.of(
                new Position()
                        .setId(STEWARD_POSITION_ID)
                        .setTitle(STEWARD_POSITION_TITLE),
                new Position()
                        .setId(CHIEF_STEWARD_POSITION_ID)
                        .setTitle(CHIEF_STEWARD_POSITION_TITLE),
                new Position()
                        .setId(PILOT_POSITION_ID)
                        .setTitle(PILOT_POSITION_TITLE),
                new Position()
                        .setId(ENGINEER_POSITION_ID)
                        .setTitle(ENGINEER_POSITION_TITLE),
                new Position()
                        .setId(CHIEF_ENGINEER_POSITION_ID)
                        .setTitle(CHIEF_ENGINEER_POSITION_TITLE),
                new Position()
                        .setId(DISPATCHER_POSITION_ID)
                        .setTitle(DISPATCHER_POSITION_TITLE),
                new Position()
                        .setId(CHIEF_DISPATCHER_POSITION_ID)
                        .setTitle(CHIEF_DISPATCHER_POSITION_TITLE),
                new Position()
                        .setId(AIRPORT_MANAGER_POSITION_ID)
                        .setTitle(AIRPORT_MANAGER_POSITION_TITLE),
                new Position()
                        .setId(SYSTEM_ADMINISTRATOR_POSITION_ID)
                        .setTitle(SYSTEM_ADMINISTRATOR_POSITION_TITLE)
        );
    }
}
