package kg.kuban.airport.util;

import kg.kuban.airport.entity.AppRole;
import kg.kuban.airport.entity.AppUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AppUserRoleUtils {
    /**
     * Метод проверяющий содержит ли пользователь роль с опеределенным названием
     * @param AppRoleList список ролей пользователей типа {@link AppRole}
     * @param userRoleTitle название роли пользователя
     * @return false если у пользователя нет роли с таким названием и true, если есть
     */
    public static boolean checkIfUserRolesListContainsSuchUserRoleTitle(
            List<AppRole> AppRoleList,
            String userRoleTitle
    ) {
        if(Objects.isNull(AppRoleList) || AppRoleList.isEmpty()) {
            throw new IllegalArgumentException("Список ролей пользователя не может быть null или пустым!");
        }
        if(Objects.isNull(userRoleTitle) || userRoleTitle.isEmpty()) {
            throw new IllegalArgumentException("Проверяемая роль пользователя не может быть null или пустой!");
        }

        for (AppRole role : AppRoleList) {
            if (role.getTitle().equals(userRoleTitle)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkIfApplicationUsersListContainsSuchUserRolesTitles(
            List<AppUser> AppUserList,
            String... searchedUserRolesTitles
    ) {
        if(Objects.isNull(AppUserList) || AppUserList.isEmpty()) {
            throw new IllegalArgumentException("Список пользователей не может быть null или пустым!");
        }
        if(Objects.isNull(searchedUserRolesTitles)) {
            throw new IllegalArgumentException("Список названий ролей не может быть пустым!");
        }

        List<String> usersRoleTitlesList = convertApplicationUsersListToRoleTitlesList(AppUserList);
        List<String> searchedRoleTitlesList = new ArrayList<>(List.of(searchedUserRolesTitles));
        searchedRoleTitlesList.removeIf(usersRoleTitlesList::contains);

        return searchedRoleTitlesList.isEmpty();
    }

    public static boolean checkIfEachApplicationUserInListContainsRequiredRoles(
            List<AppUser> AppUserList,
            String... requiredUserRolesTitles
    ) {
        List<List<AppRole>> listOfUsersRoleList =
                AppUserList
                        .stream()
                        .map(AppUser::getAppRoles)
                        .collect(Collectors.toList());

        for (List<AppRole> userRolesList : listOfUsersRoleList) {
            List<String> userRolesTitles = userRolesList
                    .stream()
                    .map(AppRole::getTitle)
                    .collect(Collectors.toList());

            int i = 0;
            for (; i < requiredUserRolesTitles.length; i++) {
                if(userRolesTitles.contains(requiredUserRolesTitles[i])) {
                    break;
                }
            }
            if(i == requiredUserRolesTitles.length) {
                return false;
            }
        }
        return true;
    }

    private static List<String> convertApplicationUsersListToRoleTitlesList(
            List<AppUser> AppUserList
    ) {
        return AppUserList
                .stream()
                .map(AppUser::getAppRoles)
                .flatMap(List::stream)
                .map(AppRole::getTitle)
                .collect(Collectors.toList());
    }
}
