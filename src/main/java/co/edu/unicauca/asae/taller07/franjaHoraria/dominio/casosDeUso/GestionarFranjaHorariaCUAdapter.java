package co.edu.unicauca.asae.taller07.franjaHoraria.dominio.casosDeUso;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.input.GestionarFranjaHorariaCUIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.FranjaHorariaFormateadorResultadosIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.FranjaHorariaGatewayIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.ValidacionesFranjaHorariaGatewayIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.FranjaHoraria;
import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.validadores.CadenaValidacionFranjaHorariaFactory;
import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.validadores.ValidadorFranjaHoraria;

/**
 * Caso de uso: Crear y eliminar franjas horarias
 * Implementa Cadena de Responsabilidad para validaciones
 */
public class GestionarFranjaHorariaCUAdapter implements GestionarFranjaHorariaCUIntPort {

    private static final Logger log = LoggerFactory.getLogger(GestionarFranjaHorariaCUAdapter.class);

    private final FranjaHorariaGatewayIntPort gateway;
    private final ValidadorFranjaHoraria cadenaValidacion;

    public GestionarFranjaHorariaCUAdapter(
            FranjaHorariaGatewayIntPort gateway,
            ValidacionesFranjaHorariaGatewayIntPort validacionesGateway,
            FranjaHorariaFormateadorResultadosIntPort formateador) {
        this.gateway = gateway;

        log.debug("Construyendo cadena de validadores...");
        // Construir la cadena de validadores
        this.cadenaValidacion = CadenaValidacionFranjaHorariaFactory.crear(
                validacionesGateway,
                formateador);
        log.debug("✓ Cadena de validadores construida correctamente");
    }

    @Override
    public FranjaHoraria crear(FranjaHoraria franjaHoraria) {
        log.info("▶▶▶ INICIANDO CREACIÓN DE FRANJA HORARIA ◀◀◀");
        log.info("Curso ID: {} | Espacio ID: {} | Día: {} | Horario: {} - {}",
                franjaHoraria.getCurso().getId(),
                franjaHoraria.getEspacioFisico().getId(),
                franjaHoraria.getDia(),
                franjaHoraria.getHoraInicio(),
                franjaHoraria.getHoraFin());

        if (log.isDebugEnabled()) {
            log.debug("Datos completos de la franja: {}", franjaHoraria);
        }

        log.info("Ejecutando CADENA DE VALIDACIONES...");
        log.info("════════════════════════════════════════════════════════════");

        try {
            cadenaValidacion.validar(franjaHoraria);
            log.info("TODAS LAS VALIDACIONES PASARON CORRECTAMENTE");
        } catch (Exception e) {
            log.error("VALIDACIÓN FALLIDA: {}", e.getMessage());
            throw e; // Re-lanzar para que el controlador de excepciones lo maneje
        }

        log.info("════════════════════════════════════════════════════════════");

        log.info("Guardando franja horaria en BD...");
        FranjaHoraria resultado = gateway.guardar(franjaHoraria);

        log.info("FRANJA HORARIA CREADA EXITOSAMENTE");
        log.info("ID asignado: {}", resultado.getId());

        if (log.isDebugEnabled()) {
            log.debug("Franja guardada: {}", resultado);
        }

        return resultado;
    }

    @Override
    public int eliminarFranjasPorCurso(int cursoId) {
        log.info("▶ Eliminando franjas horarias del curso ID: {}", cursoId);

        int filasEliminadas = gateway.eliminarFranjasPorCurso(cursoId);

        log.info("Eliminadas {} franja(s) horaria(s) del curso ID: {}", filasEliminadas, cursoId);

        return filasEliminadas;
    }
}