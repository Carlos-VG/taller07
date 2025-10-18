package co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.mappers;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FranjaHorariaMapper {

    @Bean(name = "franjaHorariaModelMapper")
    public ModelMapper crearFranjaHorariaMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper;
    }
}