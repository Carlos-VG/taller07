package co.edu.unicauca.asae.taller07.franjaHoraria.dominio.validadores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.FranjaHorariaFormateadorResultadosIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.ValidacionesFranjaHorariaGatewayIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.FranjaHoraria;

/**
 * Validador 2: Verifica que el espacio físico exista
 */
public class ValidadorEspacioFisicoExiste extends ValidadorFranjaHorariaBase {

    private static final Logger log = LoggerFactory.getLogger(ValidadorEspacioFisicoExiste.class);

    private final ValidacionesFranjaHorariaGatewayIntPort validacionesGateway;
    private final FranjaHorariaFormateadorResultadosIntPort formateador;

    public ValidadorEspacioFisicoExiste(
            ValidacionesFranjaHorariaGatewayIntPort validacionesGateway,
            FranjaHorariaFormateadorResultadosIntPort formateador) {
        this.validacionesGateway = validacionesGateway;
        this.formateador = formateador;
    }

    @Override
    protected void ejecutarValidacion(FranjaHoraria franjaHoraria) {
        int espacioId = franjaHoraria.getEspacioFisico().getId();
        log.debug("Verificando espacio físico ID: {}", espacioId);

        if (!validacionesGateway.espacioFisicoExiste(espacioId)) {
            log.warn("Espacio físico no encontrado: ID {}", espacioId);
            formateador.retornarRespuestaErrorEntidadNoExiste(
                    "No existe el espacio físico con id: " + espacioId);
        }

        log.debug("✓ Espacio físico existe");
    }
}