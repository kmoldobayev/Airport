package kg.kuban.airport.controller.v1;

import kg.kuban.airport.entity.AppUser;
import kg.kuban.airport.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
Администратор системы:
1.	Просмотр и редактирование системных настроек проекта.
2.	Просмотр и редактирование ролей пользователей.
3.	Просмотр статистики работы системы и сбор метрик.
 */

@RestController
@RequestMapping(name = "/admin")
public class AdminController {
    private final AppUserService userService;

    @Autowired
    public AdminController(AppUserService userService) {

        System.out.println(" AdminController");
        this.userService = userService;
    }

    @GetMapping("/appusers")
    public ResponseEntity<List<AppUser>> getUsers(){
        System.out.println(" getUsers");
        return ResponseEntity.ok(this.userService.getUsers());
    }

    @GetMapping(name = "/user-roles")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String getUserRoles() {
        // Логика получения ролей пользователей
        return "User roles";
    }

    @PutMapping(name = "/update-user-role/{roleId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String updateUserRole(@PathVariable("roleId") Integer roleId) {
        // Логика обновления ролей пользователей
        return "User role updated";
    }

    @PostMapping(name = "/create-user-role")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String createUserRole() {
        // Логика обновления ролей пользователей
        System.out.println("createUserRole");

        return "User roles created";
    }

    @GetMapping("/system-settings")
    @PreAuthorize("hasRole('ADMIN')")
    public String getSystemSettings() {
        // Логика получения системных настроек проекта
        return "System settings";
    }

    @PostMapping("/update-system-settings")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String updateSystemSettings() {
        // Логика обновления системных настроек проекта
        return "System settings updated";
    }

    @GetMapping("/system-statistics")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String getSystemStatistics() {
        // Логика получения статистики работы системы и сбора метрик
        return "System statistics";
    }



}
