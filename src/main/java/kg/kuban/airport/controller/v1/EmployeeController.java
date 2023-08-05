package kg.kuban.airport.controller.v1;

import kg.kuban.airport.dto.FlightResponseDto;
import kg.kuban.airport.mapper.FlightMapper;
import kg.kuban.airport.repository.FlightRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private FlightRepository flightRepository;

    /*
        Главный диспетчер:
    1.	Просмотр доступных рейсов.
    2.	Подтверждение нового рейса.
    3.	Просмотр всех зарегистрированных самолетов.
    4.	Подтверждение регистрации нового самолета.
    5.	Подтверждение отправки рейса.
    6.	Подтверждение принятия рейса.
    */

    @GetMapping("/available-flights")
    public List<FlightResponseDto> getAvailableFlights() {
        return FlightMapper.mapGroupServiceEntityListToDto(this.flightRepository.findAvailableFlights());
    }
}
