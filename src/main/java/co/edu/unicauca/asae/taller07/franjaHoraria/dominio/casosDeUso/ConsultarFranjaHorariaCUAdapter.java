package co.edu.unicauca.asae.taller07.franjaHoraria.dominio.casosDeUso;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.input.ConsultarFranjaHorariaCUIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.FranjaHorariaFormateadorResultadosIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.FranjaHorariaGatewayIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.ValidacionesFranjaHorariaGatewayIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.FranjaHoraria;

/**
 * Caso de uso: Consultar franjas horarias
 */
public class ConsultarFranjaHorariaCUAdapter implements ConsultarFranjaHorariaCUIntPort {

    private static final Logger log = LoggerFactory.getLogger(ConsultarFranjaHorariaCUAdapter.class);

    private final FranjaHorariaGatewayIntPort gateway;
    private final ValidacionesFranjaHorariaGatewayIntPort validacionesGateway;
    private final FranjaHorariaFormateadorResultadosIntPort formateador;

    public ConsultarFranjaHorariaCUAdapter(
            FranjaHorariaGatewayIntPort gateway,
            ValidacionesFranjaHorariaGatewayIntPort validacionesGateway,
            FranjaHorariaFormateadorResultadosIntPort formateador) {
        this.gateway = gateway;
        this.validacionesGateway = validacionesGateway;
        this.formateador = formateador;
    }

    @Override
    public List<FranjaHoraria> listarPorDocente(int docenteId) {
        log.info("▶ Consultando franjas horarias del docente ID: {}", docenteId);

        log.debug("🔍 Validando existencia del docente");
        if (!validacionesGateway.docenteExiste(docenteId)) {
            log.error("Error: No existe el docente con ID: {}", docenteId);
            formateador.retornarRespuestaErrorEntidadNoExiste(
                    "No existe el docente con id: " + docenteId);
        }
        log.debug("✓ Docente existe");

        List<FranjaHoraria> franjas = gateway.listarPorDocente(docenteId);

        log.info("✓ Se encontraron {} franja(s) para el docente ID: {}", franjas.size(), docenteId);

        if (log.isDebugEnabled()) {
            franjas.forEach(f -> log.debug("  - Franja ID: {} | {} | {} - {}",
                    f.getId(), f.getDia(), f.getHoraInicio(), f.getHoraFin()));
        }

        return franjas;
    }

    @Override
    public List<FranjaHoraria> listarPorCurso(int cursoId) {
        log.info("▶ Consultando franjas horarias del curso ID: {}", cursoId);

        log.debug("🔍 Validando existencia del curso");
        if (!validacionesGateway.cursoExiste(cursoId)) {
            log.error("Error: No existe el curso con ID: {}", cursoId);
            formateador.retornarRespuestaErrorEntidadNoExiste(
                    "No existe el curso con id: " + cursoId);
        }
        log.debug("✓ Curso existe");

        List<FranjaHoraria> franjas = gateway.listarPorCurso(cursoId);

        log.info("✓ Se encontraron {} franja(s) para el curso ID: {}", franjas.size(), cursoId);

        if (log.isDebugEnabled()) {
            franjas.forEach(f -> log.debug("  - Franja ID: {} | {} | {} - {}",
                    f.getId(), f.getDia(), f.getHoraInicio(), f.getHoraFin()));
        }

        return franjas;
    }

    @Override
    public List<Object[]> obtenerDetalleFranjasCurso(int cursoId) {
        log.info("▶ Obteniendo detalle completo de franjas del curso ID: {}", cursoId);

        log.debug("Validando existencia del curso");
        if (!validacionesGateway.cursoExiste(cursoId)) {
            log.error("Error: No existe el curso con ID: {}", cursoId);
            formateador.retornarRespuestaErrorEntidadNoExiste(
                    "No existe el curso con id: " + cursoId);
        }
        log.debug("✓ Curso existe");

        List<Object[]> detalle = gateway.obtenerDetalleFranjasCurso(cursoId);

        log.info("✓ Se obtuvo detalle de {} franja(s) del curso ID: {}", detalle.size(), cursoId);

        if (log.isTraceEnabled()) {
            detalle.forEach(fila -> log.trace("  - Fila: {}", java.util.Arrays.toString(fila)));
        }

        return detalle;
    }
}