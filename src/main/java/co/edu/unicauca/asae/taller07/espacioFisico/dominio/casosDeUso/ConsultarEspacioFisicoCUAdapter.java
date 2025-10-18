package co.edu.unicauca.asae.taller07.espacioFisico.dominio.casosDeUso;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.unicauca.asae.taller07.espacioFisico.aplicacion.input.ConsultarEspacioFisicoCUIntPort;
import co.edu.unicauca.asae.taller07.espacioFisico.aplicacion.output.EspacioFisicoGatewayIntPort;
import co.edu.unicauca.asae.taller07.espacioFisico.dominio.modelos.EspacioFisico;

public class ConsultarEspacioFisicoCUAdapter implements ConsultarEspacioFisicoCUIntPort {

    private static final Logger log = LoggerFactory.getLogger(ConsultarEspacioFisicoCUAdapter.class);

    private final EspacioFisicoGatewayIntPort gateway;

    public ConsultarEspacioFisicoCUAdapter(EspacioFisicoGatewayIntPort gateway) {
        this.gateway = gateway;
    }

    @Override
    public List<EspacioFisico> listarPorPatronYCapacidad(String patron, Integer capacidadMinima) {
        log.info("▶ Consultando espacios físicos - Patrón: '{}', Capacidad mínima: {}",
                patron, capacidadMinima);

        List<EspacioFisico> espacios = gateway.listarPorPatronYCapacidad(patron, capacidadMinima);

        log.info("✓ Se encontraron {} espacio(s) físico(s)", espacios.size());
        log.debug("Espacios encontrados: {}", espacios);

        return espacios;
    }
}