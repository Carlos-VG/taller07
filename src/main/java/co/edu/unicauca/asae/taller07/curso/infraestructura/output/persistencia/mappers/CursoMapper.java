package co.edu.unicauca.asae.taller07.curso.infraestructura.output.persistencia.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CursoMapper {

    @Bean(name = "cursoModelMapper")
    public ModelMapper crearCursoMapper() {
        return new ModelMapper();
    }
}