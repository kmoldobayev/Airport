package kg.kuban.airport.controller.v1;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/*
Управляющий аэропортом:
1.	Просмотр всех доступных данных в системе.
2.	Добавление, изменение и увольнение работника.
3.	Формирование отчетов.
 */

@RestController
@RequestMapping("/airport-manager")
public class AirportManagerController {
    @GetMapping("/data")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAllData() {
        // Логика получения всех доступных данных в системе
        return "All data";
    }

    @PostMapping("/employee")
    @PreAuthorize("hasRole('ADMIN')")
    public String addEmployee() {
        // Логика добавления работника
        return "Employee added";
    }

    @PutMapping("/employee/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateEmployee(@PathVariable String id) {
        // Логика изменения работника с указанным идентификатором
        return "Employee updated";
    }

    @DeleteMapping("/employee/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteEmployee(@PathVariable String id) {
        // Логика увольнения работника с указанным идентификатором
        return "Employee deleted";
    }

    @GetMapping("/reports")
    @PreAuthorize("hasRole('ADMIN')")
    public String generateReports() {
        // Логика формирования отчетов
        return "Reports generated";
    }
}
