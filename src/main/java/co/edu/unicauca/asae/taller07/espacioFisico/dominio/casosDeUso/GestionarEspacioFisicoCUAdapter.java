package co.edu.unicauca.asae.taller07.espacioFisico.dominio.casosDeUso;

import co.edu.unicauca.asae.taller07.espacioFisico.aplicacion.input.GestionarEspacioFisicoCUIntPort;
import co.edu.unicauca.asae.taller07.espacioFisico.aplicacion.output.EspacioFisicoGatewayIntPort;
import co.edu.unicauca.asae.taller07.espacioFisico.aplicacion.output.EspacioFisicoFormateadorResultadosIntPort;

/**
 * Caso de uso para actualizar estado de espacio físico
 */
public class GestionarEspacioFisicoCUAdapter implements GestionarEspacioFisicoCUIntPort {

    private final EspacioFisicoGatewayIntPort gateway;
    private final EspacioFisicoFormateadorResultadosIntPort formateador;

    public GestionarEspacioFisicoCUAdapter(
            EspacioFisicoGatewayIntPort gateway,
            EspacioFisicoFormateadorResultadosIntPort formateador) {
        this.gateway = gateway;
        this.formateador = formateador;
    }

    @Override
    public int actualizarEstado(int id, boolean activo) {
        // Validar que el espacio físico exista
        if (!gateway.existePorId(id)) {
            formateador.retornarRespuestaErrorEntidadNoExiste(
                    "No existe un espacio físico con id: " + id);
        }

        return gateway.actualizarEstado(id, activo);
    }
}
