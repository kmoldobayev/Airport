package kg.kuban.airport.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.kuban.airport.dto.AppUserRequestDto;
import kg.kuban.airport.dto.AppUserResponseDto;
import kg.kuban.airport.entity.AppUser;
import kg.kuban.airport.mapper.AppUserMapper;
import kg.kuban.airport.service.AppUserService;
import kg.kuban.airport.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
Управляющий аэропортом:
1.	Просмотр всех доступных данных в системе.
2.	Добавление, изменение и увольнение работника.
3.	Формирование отчетов.
 */

@RestController
@RequestMapping(value = "/users")
@Tag(
        name = "Контроллер Управляющий аэропортом",
        description = "1.\tПросмотр всех доступных данных в системе.\n" +
                "2.\tДобавление, изменение и увольнение работника.\n" +
                "3.\tФормирование отчетов."
)
public class AppUserController {
    private final AppUserService userService;
    private final Logger logger = LoggerFactory.getLogger(AppUserController.class);

    @Autowired
    public AppUserController(AppUserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "маппинг Просмотр всех пользователей системы",
            description = "Просмотр всех пользователей системы!"
    )
    @GetMapping("/users")
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
    public ResponseEntity<AppUserResponseDto> createUser(@RequestBody AppUserRequestDto user){
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
    public Boolean dismissUser(@PathVariable(value = "id") Long userId){
        return userService.dismissUser(userId);

    }
}
