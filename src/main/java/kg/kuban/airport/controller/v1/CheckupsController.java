package kg.kuban.airport.controller.v1;

import io.swagger.v3.oas.annotations.tags.Tag;
import kg.kuban.airport.exception.AirplanePartCheckupNotFoundException;
import kg.kuban.airport.exception.AirplanePartCheckupsNotFoundException;
import kg.kuban.airport.mapper.AirplanePartCheckupMapper;
import kg.kuban.airport.service.AirplanePartCheckupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/checkups")
@Tag(
        name = "Контроллер просмотра истории тех осмотра самолетов",
        description = "Описывает точки доступа по истории тех осмотра"
)
public class CheckupsController {
    private final AirplanePartCheckupService partCheckupService;

    @Autowired
    public CheckupsController(AirplanePartCheckupService partCheckupService) {
        this.partCheckupService = partCheckupService;
    }

    @PreAuthorize(value = "hasRole('CHIEF_ENGINEER')")
    @GetMapping(value = "/history")
    public ResponseEntity<?> getAirplanePartCheckupsHistory(@RequestParam Long airplaneId)
            throws AirplanePartCheckupsNotFoundException
    {
        return ResponseEntity.ok(AirplanePartCheckupMapper.mapToPartCheckupResponseDtoList(this.partCheckupService.getPartCheckupsHistory(airplaneId)));
    }

    @PreAuthorize(value = "hasAnyRole('ENGINEER', 'CHIEF_ENGINEER')")
    @GetMapping(value = "/partStatus")
    public ResponseEntity<?> getPartStates() {

        return ResponseEntity.ok(this.partCheckupService.getAllPartStates());
    }
}
