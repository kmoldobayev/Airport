package kg.kuban.airport.controller.v1;

import io.swagger.v3.oas.annotations.tags.Tag;
import kg.kuban.airport.dto.AirplanePartRequestDto;
import kg.kuban.airport.enums.AirplanePartType;
import kg.kuban.airport.enums.AirplaneType;
import kg.kuban.airport.exception.AirplanePartNotFoundException;
import kg.kuban.airport.mapper.AirplanePartMapper;
import kg.kuban.airport.service.AirplanePartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/airplaneParts")
@Tag(
        name = "Контроллер для регистрации частей Самолета",
        description = "Описывает точки доступа по регистрации частей самолета для тех осмотра работниками аэропорта"
)
public class AirplanePartController {
    private final AirplanePartService partService;

    @Autowired
    public AirplanePartController(AirplanePartService partService) {
        this.partService = partService;
    }

    @PreAuthorize(value = "hasRole('DISPATCHER')")
    @PostMapping(value = "/register")
    public ResponseEntity<?> registerNewPart(@RequestBody AirplanePartRequestDto requestDto) {
        return ResponseEntity.ok(AirplanePartMapper.mapToAirplanePartResponseDto(this.partService.registerNewPart(requestDto)));  //AirplanePartResponseDto
    }

    @PreAuthorize(value = "hasRole('DISPATCHER')")
    @PostMapping(value = "/registerAll")
    public ResponseEntity<?> registerNewParts(@RequestBody List<AirplanePartRequestDto> requestDto) {
        return ResponseEntity.ok(AirplanePartMapper.mapToAirplanePartResponseDtoList(this.partService.registerNewParts(requestDto)));  //List<AirplanePartResponseDto>
    }

    @PreAuthorize(value = "hasAnyRole('DISPATCHER', 'MANAGER')")
    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllParts(
            @RequestParam(required = false) AirplaneType airplaneType,
            @RequestParam(required = false) AirplanePartType partType,
            @RequestParam(required = false) Long airplaneId,
            @RequestParam(required = false) Long partId,
            @RequestParam(required = false) LocalDateTime registeredBefore,
            @RequestParam(required = false) LocalDateTime registeredAfter
    )
            throws AirplanePartNotFoundException
    {
        return ResponseEntity.ok(AirplanePartMapper.mapToAirplanePartResponseDtoList(this.partService.getAllParts(
                airplaneType,
                partType,
                airplaneId,
                partId,
                registeredBefore,
                registeredAfter
        )));  // List<AirplanePartResponseDto>
    }

    @PreAuthorize(value = "hasAnyRole('DISPATCHER', 'MANAGER', 'ENGINEER', 'CHIEF_ENGINEER', 'CHIEF_DISPATCHER')")
    @GetMapping(value = "/partTypes")
    public ResponseEntity<?> getPartTypes() {
        return ResponseEntity.ok(this.partService.getAllPartTypes());  //AirplanePartTypesResponseDto
    }
}
