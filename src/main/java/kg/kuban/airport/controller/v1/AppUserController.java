package kg.kuban.airport.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.kuban.airport.dto.AppRoleRequestDto;
import kg.kuban.airport.dto.AppRoleResponseDto;
import kg.kuban.airport.dto.AppUserRequestDto;
import kg.kuban.airport.dto.AppUserResponseDto;
import kg.kuban.airport.exception.InvalidCredentialsException;
import kg.kuban.airport.mapper.AppRoleMapper;
import kg.kuban.airport.mapper.AppUserMapper;
import kg.kuban.airport.response.SuccessResponse;
import kg.kuban.airport.service.AppRoleService;
import kg.kuban.airport.service.AppUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
@Tag(
        name = "Контроллер для управления пользователями системы",
        description = "Роли (Администратор, Управляющий аэропортом) могут управлять пользователями системы"
)
public class AppUserController {
    private final AppUserService userService;
    private final AppRoleService roleService;
    private final Logger logger = LoggerFactory.getLogger(AppUserController.class);

    @Autowired
    public AppUserController(AppUserService userService, AppRoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Operation(
            summary = "Просмотр всех пользователей системы",
            description = "Просмотр всех пользователей системы!"
    )
    @GetMapping(value = "/users")
    @PreAuthorize("hasAnyRole('ADMIN', 'CHIEF')")
    public ResponseEntity<List<AppUserResponseDto>> getUsers(){
        logger.info("!!!!!!!!!!!!!!getUsers");
        return ResponseEntity.ok(AppUserMapper.mapAppUserEntityListToDto(this.userService.getUsers()));
    }

    @Operation(
            summary = "Просмотр пользователя системы по ID",
            description = "Просмотр пользователя системы по ID",
            parameters = {
                    @Parameter(name = "userId", description = "ID пользователя")
            }
    )
    @GetMapping(value = "/users/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CHIEF')")
    public ResponseEntity<AppUserResponseDto> getUserById(@PathVariable(value = "id") Long userId){
        return ResponseEntity.ok(AppUserMapper.mapAppUserEntityToDto(userService.getUserById(userId)));
    }

    @Operation(
            summary = "Добавление нового пользователя системы (работника)",
            description = "Добавление нового пользователя системы (работника)",
            parameters = {
                    @Parameter(name = "AppUser", description = "Данные пользователя")
            }
    )
    @PostMapping(value = "/createUser")
    @PreAuthorize("hasAnyRole('ADMIN', 'CHIEF')")
    public ResponseEntity<AppUserResponseDto> createUser(@RequestBody AppUserRequestDto user) throws InvalidCredentialsException{
        return  ResponseEntity.ok(AppUserMapper.mapAppUserEntityToDto(this.userService.createUser(user)));
    }

    @Operation(
            summary = "Изменение пользователя системы (работника)",
            description = "Изменение пользователя системы (работника)",
            parameters = {
                    @Parameter(name = "AppUser", description = "Данные пользователя")
            }
    )
    @PutMapping(value = "/updateUser")
    @PreAuthorize("hasAnyRole('ADMIN', 'CHIEF')")
    public ResponseEntity<AppUserResponseDto> updateUser(@RequestParam(value = "id") Long userId,
                           @RequestBody AppUserRequestDto user
    ) throws IllegalArgumentException, InvalidCredentialsException {
        return ResponseEntity.ok(AppUserMapper.mapAppUserEntityToDto(this.userService.updateUser(user, userId)));
    }

    @Operation(
            summary = "Увольнение пользователя системы (работника)",
            description = "Увольнение пользователя системы (работника)",
            parameters = {
                    @Parameter(name = "AppUser", description = "Данные пользователя")
            }
    )
    @PostMapping(value = "/dismissUser/{id}")
    @PreAuthorize("hasAnyRole('CHIEF')")
    public ResponseEntity<?> dismissUser(@PathVariable Long userId){
        boolean isDismissed = userService.dismissUser(userId);
        if (isDismissed) {
            return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "Работник успешно уволен!."));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Просмотр всех ролей пользователей системы",
            description = "Просмотр всех ролей пользователей системы!"
    )
    @GetMapping(value = "/userRoles")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> getUserRoles() {
        logger.info("!!!!!!!!!!!!!!getUserRoles");
        // Логика получения ролей пользователей
        return ResponseEntity.ok(AppRoleMapper.mapAppRoleEntityListToDto(this.roleService.getRoles()));
    }

    @Operation(
            summary = "Редактирование роли системы",
            description = "Редактирование роли системы!"
    )
    @PutMapping(value = "/updateUserRole/{roleId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> updateUserRole(@RequestBody AppRoleRequestDto roleDto, @PathVariable("roleId") Long roleId) {
        // Логика обновления ролей пользователей
        return ResponseEntity.ok(AppRoleMapper.mapAppUserEntityToDto(this.roleService.updateRole(roleDto, roleId)));
    }

    @Operation(
            summary = "Создание новой роли системы",
            description = "Создание новой роли системы!"
    )
    @PostMapping(value = "/createUserRole")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> createUserRole(@RequestBody AppRoleRequestDto roleDto) {
        // Логика создания роли пользователя
        logger.info("createUserRole");

        return ResponseEntity.ok(AppRoleMapper.mapAppUserEntityToDto(this.roleService.createRole(roleDto)));
    }

    @Operation(
            summary = "Удаление роли системы",
            description = "Удаление роли системы!"
    )
    @DeleteMapping(value = "/deleteUserRole/{roleId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> deleteUserRole(@PathVariable("roleId") Long roleId) {
        return ResponseEntity.ok(AppRoleMapper.mapAppUserEntityToDto(this.roleService.deleteRole(roleId)));
    }

}
