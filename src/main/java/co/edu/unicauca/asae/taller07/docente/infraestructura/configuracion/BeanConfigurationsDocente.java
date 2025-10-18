package co.edu.unicauca.asae.taller07.docente.infraestructura.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.edu.unicauca.asae.taller07.docente.aplicacion.output.DocenteFormateadorResultadosIntPort;
import co.edu.unicauca.asae.taller07.docente.aplicacion.output.DocenteGatewayIntPort;
import co.edu.unicauca.asae.taller07.docente.aplicacion.output.ValidacionesDocenteGatewayIntPort;
import co.edu.unicauca.asae.taller07.docente.dominio.casosDeUso.GestionarDocenteCUAdapter;

@Configuration
public class BeanConfigurationsDocente {

    @Bean
    public GestionarDocenteCUAdapter crearGestionarDocenteCU(
            DocenteGatewayIntPort gateway,
            ValidacionesDocenteGatewayIntPort validacionesGateway,
            DocenteFormateadorResultadosIntPort formateador) {
        return new GestionarDocenteCUAdapter(gateway, validacionesGateway, formateador);
    }
}