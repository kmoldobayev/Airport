package kg.kuban.airport.service.impl;

import kg.kuban.airport.entity.Aircompany;
import kg.kuban.airport.entity.Airport;
import kg.kuban.airport.entity.Position;
import kg.kuban.airport.repository.AircompanyRepository;
import kg.kuban.airport.repository.AirportRepository;
import kg.kuban.airport.repository.PositionRepository;
import kg.kuban.airport.service.DictionaryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictionaryServiceImpl implements DictionaryService {

    private AirportRepository airportRepository;
    private AircompanyRepository aircompanyRepository;
    private PositionRepository positionRepository;

    @Override
    public List<Airport> getAirports() {
        return this.airportRepository.findAll();
    }

    @Override
    public List<Aircompany> getAircompanies() {
        return this.aircompanyRepository.findAll();
    }

    @Override
    public List<Position> getPositions() {
        return this.positionRepository.findAll();
    }
}
