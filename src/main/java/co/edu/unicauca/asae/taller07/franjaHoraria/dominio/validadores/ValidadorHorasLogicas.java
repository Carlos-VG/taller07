package co.edu.unicauca.asae.taller07.franjaHoraria.dominio.validadores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.FranjaHorariaFormateadorResultadosIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.FranjaHoraria;

/**
 * Validador 0: Verifica que hora inicio < hora fin
 */
public class ValidadorHorasLogicas extends ValidadorFranjaHorariaBase {

    private static final Logger log = LoggerFactory.getLogger(ValidadorHorasLogicas.class);

    private final FranjaHorariaFormateadorResultadosIntPort formateador;

    public ValidadorHorasLogicas(FranjaHorariaFormateadorResultadosIntPort formateador) {
        this.formateador = formateador;
    }

    @Override
    protected void ejecutarValidacion(FranjaHoraria franjaHoraria) {
        log.debug("Hora inicio: {} | Hora fin: {}",
                franjaHoraria.getHoraInicio(), franjaHoraria.getHoraFin());

        if (franjaHoraria.getHoraInicio().isAfter(franjaHoraria.getHoraFin()) ||
                franjaHoraria.getHoraInicio().equals(franjaHoraria.getHoraFin())) {

            log.warn("Horas lógicas incorrectas: {} >= {}",
                    franjaHoraria.getHoraInicio(), franjaHoraria.getHoraFin());

            formateador.retornarRespuestaErrorReglaDeNegocio(
                    "La hora de inicio debe ser menor que la hora de fin");
        }

        log.debug("✓ Horas lógicas correctas");
    }
}