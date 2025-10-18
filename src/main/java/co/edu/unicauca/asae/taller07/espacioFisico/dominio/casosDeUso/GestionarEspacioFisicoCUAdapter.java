package co.edu.unicauca.asae.taller07.espacioFisico.dominio.casosDeUso;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.unicauca.asae.taller07.espacioFisico.aplicacion.input.GestionarEspacioFisicoCUIntPort;
import co.edu.unicauca.asae.taller07.espacioFisico.aplicacion.output.EspacioFisicoGatewayIntPort;
import co.edu.unicauca.asae.taller07.espacioFisico.aplicacion.output.EspacioFisicoFormateadorResultadosIntPort;

public class GestionarEspacioFisicoCUAdapter implements GestionarEspacioFisicoCUIntPort {

    private static final Logger log = LoggerFactory.getLogger(GestionarEspacioFisicoCUAdapter.class);

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
        log.info("▶ Actualizando estado del espacio físico ID: {} → activo: {}", id, activo);

        // Validar que el espacio físico exista
        log.debug("Validando existencia del espacio físico ID: {}", id);

        if (!gateway.existePorId(id)) {
            log.error("Error: No existe espacio físico con ID: {}", id);
            formateador.retornarRespuestaErrorEntidadNoExiste(
                    "No existe un espacio físico con id: " + id);
        }

        log.debug("✓ Espacio físico existe");

        log.debug("Ejecutando actualización en BD");
        int filasActualizadas = gateway.actualizarEstado(id, activo);

        log.info("Estado actualizado - Filas afectadas: {}", filasActualizadas);

        return filasActualizadas;
    }
}