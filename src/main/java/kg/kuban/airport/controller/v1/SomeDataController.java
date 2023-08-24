package kg.kuban.airport.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.kuban.airport.mapper.AircompanyMapper;
import kg.kuban.airport.mapper.AirportMapper;
import kg.kuban.airport.mapper.PositionMapper;
import kg.kuban.airport.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/dictionaries")
@Tag(
        name = "Контроллер для получения справочных данных Управляющим аэропортом",
        description = "1.Просмотр всех доступных данных в системе (Управляющий аэропортом)."
)
public class SomeDataController {

    private final DictionaryService dictionaryService;

    @Autowired
    public SomeDataController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @Operation(
            summary = "Получение всех доступных аэропортов в системе",
            description = "Получение всех доступных аэропортов в системе"
    )
    @GetMapping("/airports")
    @PreAuthorize("hasRole('CHIEF')")
    public ResponseEntity<?> getAllAirports() {
        // Логика получения всех доступных данных в системе
        return ResponseEntity.ok(AirportMapper.mapAirportEntityListToDto(this.dictionaryService.getAirports()));
    }

    @Operation(
            summary = "Получение всех доступных авиакомпаний в системе",
            description = "Получение всех доступных авиакомпаний в системе"
    )
    @GetMapping("/aircompanies")
    @PreAuthorize("hasRole('CHIEF')")
    public ResponseEntity<?> getAllAircompanies() {
        // Логика получения всех доступных данных в системе
        return ResponseEntity.ok(AircompanyMapper.mapAircompanyEntityListToDto(this.dictionaryService.getAircompanies()));
    }

    @Operation(
            summary = "Получение всех доступных должностей в системе",
            description = "Получение всех доступных должностей в системе"
    )
    @GetMapping("/positions")
    @PreAuthorize("hasRole('CHIEF')")
    public ResponseEntity<?> getAllPositions() {
        // Логика получения всех доступных данных в системе
        return ResponseEntity.ok(PositionMapper.mapAppUserEntityListToDto(this.dictionaryService.getPositions()));
    }
}
