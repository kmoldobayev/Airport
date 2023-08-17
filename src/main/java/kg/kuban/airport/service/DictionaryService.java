package kg.kuban.airport.service;

import kg.kuban.airport.entity.Aircompany;
import kg.kuban.airport.entity.Airport;
import kg.kuban.airport.entity.Position;

import java.util.List;

public interface DictionaryService {
    List<Airport> getAirports();
    List<Aircompany> getAircompanies();
    List<Position> getPositions();
}
