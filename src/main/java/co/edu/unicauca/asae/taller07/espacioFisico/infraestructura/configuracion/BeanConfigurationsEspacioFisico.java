package co.edu.unicauca.asae.taller07.espacioFisico.infraestructura.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.edu.unicauca.asae.taller07.espacioFisico.aplicacion.output.EspacioFisicoGatewayIntPort;
import co.edu.unicauca.asae.taller07.espacioFisico.aplicacion.output.EspacioFisicoFormateadorResultadosIntPort;
import co.edu.unicauca.asae.taller07.espacioFisico.dominio.casosDeUso.GestionarEspacioFisicoCUAdapter;
import co.edu.unicauca.asae.taller07.espacioFisico.dominio.casosDeUso.ConsultarEspacioFisicoCUAdapter;

@Configuration
public class BeanConfigurationsEspacioFisico {

    @Bean
    public GestionarEspacioFisicoCUAdapter crearGestionarEspacioFisicoCU(
            EspacioFisicoGatewayIntPort gateway,
            EspacioFisicoFormateadorResultadosIntPort formateador) {
        return new GestionarEspacioFisicoCUAdapter(gateway, formateador);
    }

    @Bean
    public ConsultarEspacioFisicoCUAdapter crearConsultarEspacioFisicoCU(
            EspacioFisicoGatewayIntPort gateway) {
        return new ConsultarEspacioFisicoCUAdapter(gateway);
    }
}