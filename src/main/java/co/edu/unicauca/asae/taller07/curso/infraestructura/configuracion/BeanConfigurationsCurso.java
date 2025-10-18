package co.edu.unicauca.asae.taller07.curso.infraestructura.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.edu.unicauca.asae.taller07.curso.aplicacion.output.CursoGatewayIntPort;
import co.edu.unicauca.asae.taller07.curso.dominio.casosDeUso.ConsultarCursoCUAdapter;

@Configuration
public class BeanConfigurationsCurso {

    @Bean
    public ConsultarCursoCUAdapter crearConsultarCursoCU(CursoGatewayIntPort gateway) {
        return new ConsultarCursoCUAdapter(gateway);
    }
}