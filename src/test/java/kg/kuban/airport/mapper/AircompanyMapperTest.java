package kg.kuban.airport.mapper;

import kg.kuban.airport.dto.AircompanyResponseDto;
import kg.kuban.airport.entity.Aircompany;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class AircompanyMapperTest {
    @Test
    public void testMapper() {
        MapstructProviderMapper mapper = Mappers.getMapper(MapstructProviderMapper.class);
        Aircompany aircompany = new Aircompany();
        aircompany.setId(1L).setTitle("test");
        AircompanyResponseDto result = mapper.mapAircompanyToDto(aircompany);
        System.out.println(result.getId() + " " + result.getTitle());
    }
}