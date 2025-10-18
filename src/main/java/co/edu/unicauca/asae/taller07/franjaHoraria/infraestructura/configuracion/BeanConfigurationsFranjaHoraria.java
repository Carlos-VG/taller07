package co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.FranjaHorariaFormateadorResultadosIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.FranjaHorariaGatewayIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.ValidacionesFranjaHorariaGatewayIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.casosDeUso.ConsultarFranjaHorariaCUAdapter;
import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.casosDeUso.GestionarFranjaHorariaCUAdapter;

@Configuration
public class BeanConfigurationsFranjaHoraria {

    @Bean
    public GestionarFranjaHorariaCUAdapter crearGestionarFranjaHorariaCU(
            FranjaHorariaGatewayIntPort gateway,
            ValidacionesFranjaHorariaGatewayIntPort validacionesGateway,
            FranjaHorariaFormateadorResultadosIntPort formateador) {
        return new GestionarFranjaHorariaCUAdapter(gateway, validacionesGateway, formateador);
    }

    @Bean
    public ConsultarFranjaHorariaCUAdapter crearConsultarFranjaHorariaCU(
            FranjaHorariaGatewayIntPort gateway,
            ValidacionesFranjaHorariaGatewayIntPort validacionesGateway,
            FranjaHorariaFormateadorResultadosIntPort formateador) {
        return new ConsultarFranjaHorariaCUAdapter(gateway, validacionesGateway, formateador);
    }
}