package co.edu.unicauca.asae.taller07.franjaHoraria.dominio.validadores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.FranjaHorariaFormateadorResultadosIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.ValidacionesFranjaHorariaGatewayIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.FranjaHoraria;

/**
 * Validador 3: Verifica que el espacio físico NO esté ocupado (JPQL)
 */
public class ValidadorEspacioFisicoDisponible extends ValidadorFranjaHorariaBase {

    private static final Logger log = LoggerFactory.getLogger(ValidadorEspacioFisicoDisponible.class);

    private final ValidacionesFranjaHorariaGatewayIntPort validacionesGateway;
    private final FranjaHorariaFormateadorResultadosIntPort formateador;

    public ValidadorEspacioFisicoDisponible(
            ValidacionesFranjaHorariaGatewayIntPort validacionesGateway,
            FranjaHorariaFormateadorResultadosIntPort formateador) {
        this.validacionesGateway = validacionesGateway;
        this.formateador = formateador;
    }

    @Override
    protected void ejecutarValidacion(FranjaHoraria franjaHoraria) {
        int espacioId = franjaHoraria.getEspacioFisico().getId();

        log.debug("Verificando disponibilidad del espacio físico ID: {} el {} de {} a {}",
                espacioId,
                franjaHoraria.getDia(),
                franjaHoraria.getHoraInicio(),
                franjaHoraria.getHoraFin());

        boolean ocupado = validacionesGateway.espacioFisicoOcupado(
                franjaHoraria.getDia(),
                franjaHoraria.getHoraInicio(),
                franjaHoraria.getHoraFin(),
                espacioId);

        log.debug("Espacio físico ID: {} - Ocupado: {}", espacioId, ocupado);

        if (ocupado) {
            String mensaje = String.format(
                    "El espacio físico '%s' (ID: %d) ya está ocupado el %s de %s a %s",
                    franjaHoraria.getEspacioFisico().getNombre(),
                    espacioId,
                    franjaHoraria.getDia(),
                    franjaHoraria.getHoraInicio(),
                    franjaHoraria.getHoraFin());

            log.warn("{}", mensaje);
            formateador.retornarRespuestaErrorReglaDeNegocio(mensaje);
        }

        log.debug("✓ Espacio físico disponible");
    }
}