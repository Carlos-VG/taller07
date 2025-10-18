package co.edu.unicauca.asae.taller07.espacioFisico.dominio.casosDeUso;

import java.util.List;
import co.edu.unicauca.asae.taller07.espacioFisico.aplicacion.input.ConsultarEspacioFisicoCUIntPort;
import co.edu.unicauca.asae.taller07.espacioFisico.aplicacion.output.EspacioFisicoGatewayIntPort;
import co.edu.unicauca.asae.taller07.espacioFisico.dominio.modelos.EspacioFisico;

/**
 * Caso de uso para consultar espacios físicos
 */
public class ConsultarEspacioFisicoCUAdapter implements ConsultarEspacioFisicoCUIntPort {

    private final EspacioFisicoGatewayIntPort gateway;

    public ConsultarEspacioFisicoCUAdapter(EspacioFisicoGatewayIntPort gateway) {
        this.gateway = gateway;
    }

    @Override
    public List<EspacioFisico> listarPorPatronYCapacidad(String patron, Integer capacidadMinima) {
        return gateway.listarPorPatronYCapacidad(patron, capacidadMinima);
    }
}