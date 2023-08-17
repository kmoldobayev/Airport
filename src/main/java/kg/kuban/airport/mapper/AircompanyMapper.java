package kg.kuban.airport.mapper;

import kg.kuban.airport.dto.AircompanyRequestDto;
import kg.kuban.airport.dto.AircompanyResponseDto;
import kg.kuban.airport.dto.FlightResponseDto;
import kg.kuban.airport.entity.Aircompany;
import kg.kuban.airport.entity.Flight;

import java.util.ArrayList;
import java.util.List;

public class AircompanyMapper {
    public static AircompanyResponseDto mapAircompanyEntityToDto(Aircompany aircompany) {
        AircompanyResponseDto aircompanyResponseDto = new AircompanyResponseDto();
        aircompanyResponseDto.setId(aircompany.getId());
        aircompanyResponseDto.setTitle(aircompany.getTitle());
        return aircompanyResponseDto;
    }

    public static Aircompany mapAircompanyDtoToEntity(AircompanyResponseDto aircompanyResponseDto) {
        Aircompany aircompany = new Aircompany();
        aircompany.setId(aircompanyResponseDto.getId());
        aircompany.setTitle(aircompanyResponseDto.getTitle());
        return aircompany;
    }

    public static List<AircompanyResponseDto> mapAircompanyEntityListToDto(List<Aircompany> aircompanyList) {
        List<AircompanyResponseDto> result = new ArrayList<>();

        for ( Aircompany aircompany : aircompanyList) {
            AircompanyResponseDto aircompanyResponseDto = new AircompanyResponseDto();
            aircompanyResponseDto.setId(aircompany.getId());
            aircompanyResponseDto.setTitle(aircompany.getTitle());
            result.add(aircompanyResponseDto);
        }

        return result;
    }
}
