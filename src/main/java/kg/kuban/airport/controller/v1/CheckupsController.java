package kg.kuban.airport.controller.v1;

import kg.kuban.airport.entity.AirplanePartCheckup;
import kg.kuban.airport.exception.AirplanePartCheckupNotFoundException;
import kg.kuban.airport.mapper.PartCheckupMapper;
import kg.kuban.airport.service.PartCheckupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/checkups")
public class CheckupsController {
    private final PartCheckupService partCheckupService;

    @Autowired
    public CheckupsController(PartCheckupService partCheckupService) {
        this.partCheckupService = partCheckupService;
    }

    @PreAuthorize(value = "hasRole('CHIEF_ENGINEER')")
    @GetMapping(value = "/history")
    public ResponseEntity<?> getAirplanePartCheckupsHistory(
            @RequestParam Long airplaneId
    )
            throws AirplanePartCheckupNotFoundException
    {
        return ResponseEntity.ok(PartCheckupMapper.mapToPartCheckupResponseDtoList(this.partCheckupService.getPartCheckupsHistory(airplaneId)));
    }

    @PreAuthorize(value = "hasAnyRole('ENGINEER', 'CHIEF_ENGINEER')")
    @GetMapping(value = "/partStatus")
    public ResponseEntity<?> getPartStates() {

        return ResponseEntity.ok(this.partCheckupService.getAllPartStates());
    }
}
