package co.edu.unicauca.asae.taller07.docente.infraestructura.output.persistencia.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocenteMapper {

    @Bean(name = "docenteModelMapper")
    public ModelMapper crearDocenteMapper() {
        return new ModelMapper();
    }
}