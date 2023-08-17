package kg.kuban.airport.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.kuban.airport.dto.AppRoleResponseDto;
import kg.kuban.airport.dto.AppUserRequestDto;
import kg.kuban.airport.dto.AppUserResponseDto;
import kg.kuban.airport.exception.InvalidCredentialsException;
import kg.kuban.airport.mapper.AppRoleMapper;
import kg.kuban.airport.mapper.AppUserMapper;
import kg.kuban.airport.service.AppRoleService;
import kg.kuban.airport.service.AppUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
@Tag(
        name = "Контроллер для Администратор, Управляющий аэропортом",
        description = "1.\tПросмотр всех доступных данных в системе (Управляющий аэропортом).\n" +
                "2.\tДобавление (Администратор, Управляющий аэропортом) , изменение (Администратор, Управляющий аэропортом) и увольнение работника (Управляющий аэропортом).\n" +
                "3.\tФормирование отчетов (Управляющий аэропортом)."
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
            summary = "маппинг Просмотр всех пользователей системы",
            description = "Просмотр всех пользователей системы!"
    )
    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('ADMIN', 'CHIEF')")
    public ResponseEntity<List<AppUserResponseDto>> getUsers(){
        logger.info("!!!!!!!!!!!!!!getUsers");
        return ResponseEntity.ok(AppUserMapper.mapAppUserEntityListToDto(this.userService.getUsers()));
    }

    @Operation(
            summary = "маппинг Просмотр пользователя системы по ID",
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
            summary = "маппинг Добавление нового пользователя системы (работника)",
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
            summary = "маппинг Изменение пользователя системы (работника)",
            description = "Изменение пользователя системы (работника)",
            parameters = {
                    @Parameter(name = "AppUser", description = "Данные пользователя")
            }
    )
    @PutMapping(value = "/updateUser")
    @PreAuthorize("hasAnyRole('ADMIN', 'CHIEF')")
    public ResponseEntity<AppUserResponseDto> updateUser(@RequestParam(value = "id") Long userId,
                           @RequestBody AppUserRequestDto user
    ){
        return ResponseEntity.ok(AppUserMapper.mapAppUserEntityToDto(this.userService.updateUser(user, userId)));
    }

    @Operation(
            summary = "маппинг Увольнение пользователя системы (работника)",
            description = "Увольнение пользователя системы (работника)",
            parameters = {
                    @Parameter(name = "AppUser", description = "Данные пользователя")
            }
    )
    @PostMapping(value = "/dismissUser/{id}")
    @PreAuthorize("hasAnyRole('CHIEF')")
    public Boolean dismissUser(@PathVariable(value = "id") Long userId){
        return userService.dismissUser(userId);
    }

    @GetMapping(value = "/userRoles")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<AppRoleResponseDto>> getUserRoles() {
        logger.info("!!!!!!!!!!!!!!getUserRoles");
        // Логика получения ролей пользователей
        return ResponseEntity.ok(AppRoleMapper.mapAppRoleEntityListToDto(this.roleService.getRoles()));
    }

    @PutMapping(name = "/updateUserRole/{roleId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String updateUserRole(@PathVariable("roleId") Integer roleId) {
        // Логика обновления ролей пользователей
        return "User role updated";
    }

    @PostMapping(name = "/createUserRole")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String createUserRole() {
        // Логика обновления ролей пользователей
        logger.info("createUserRole");

        return "User roles created";
    }

    @GetMapping("/data")
    @PreAuthorize("hasRole('CHIEF')")
    public String getAllData() {
        // Логика получения всех доступных данных в системе
        return "All data";
    }

    @GetMapping("/reports")
    @PreAuthorize("hasRole('CHIEF')")
    public String generateReports() {
        // Логика формирования отчетов
        return "Reports generated";
    }
}
