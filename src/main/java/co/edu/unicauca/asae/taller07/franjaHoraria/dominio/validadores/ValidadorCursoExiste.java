package co.edu.unicauca.asae.taller07.franjaHoraria.dominio.validadores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.FranjaHorariaFormateadorResultadosIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.ValidacionesFranjaHorariaGatewayIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.FranjaHoraria;

/**
 * Validador 1: Verifica que el curso exista
 */
public class ValidadorCursoExiste extends ValidadorFranjaHorariaBase {

    private static final Logger log = LoggerFactory.getLogger(ValidadorCursoExiste.class);

    private final ValidacionesFranjaHorariaGatewayIntPort validacionesGateway;
    private final FranjaHorariaFormateadorResultadosIntPort formateador;

    public ValidadorCursoExiste(
            ValidacionesFranjaHorariaGatewayIntPort validacionesGateway,
            FranjaHorariaFormateadorResultadosIntPort formateador) {
        this.validacionesGateway = validacionesGateway;
        this.formateador = formateador;
    }

    @Override
    protected void ejecutarValidacion(FranjaHoraria franjaHoraria) {
        int cursoId = franjaHoraria.getCurso().getId();
        log.debug("Verificando curso ID: {}", cursoId);

        if (!validacionesGateway.cursoExiste(cursoId)) {
            log.warn("Curso no encontrado: ID {}", cursoId);
            formateador.retornarRespuestaErrorEntidadNoExiste(
                    "No existe el curso con id: " + cursoId);
        }

        log.debug("✓ Curso existe");
    }
}