package co.edu.unicauca.asae.taller07.docente.dominio.casosDeUso;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.unicauca.asae.taller07.docente.aplicacion.input.GestionarDocenteCUIntPort;
import co.edu.unicauca.asae.taller07.docente.aplicacion.output.DocenteFormateadorResultadosIntPort;
import co.edu.unicauca.asae.taller07.docente.aplicacion.output.DocenteGatewayIntPort;
import co.edu.unicauca.asae.taller07.docente.aplicacion.output.ValidacionesDocenteGatewayIntPort;
import co.edu.unicauca.asae.taller07.docente.dominio.modelos.Docente;

/**
 * Caso de uso: Crear docente
 * Aplica patrón Cadena de Responsabilidad para validaciones
 */
public class GestionarDocenteCUAdapter implements GestionarDocenteCUIntPort {

    private static final Logger log = LoggerFactory.getLogger(GestionarDocenteCUAdapter.class);

    private final DocenteGatewayIntPort gateway;
    private final ValidacionesDocenteGatewayIntPort validacionesGateway;
    private final DocenteFormateadorResultadosIntPort formateador;

    public GestionarDocenteCUAdapter(
            DocenteGatewayIntPort gateway,
            ValidacionesDocenteGatewayIntPort validacionesGateway,
            DocenteFormateadorResultadosIntPort formateador) {
        this.gateway = gateway;
        this.validacionesGateway = validacionesGateway;
        this.formateador = formateador;
    }

    @Override
    public Docente crear(Docente docente) {
        log.info("▶ Iniciando creación de docente: {} {}",
                docente.getNombre(), docente.getApellido());
        log.debug("Datos del docente: {}", docente);

        // VALIDACIÓN 1: Correo único (Cadena de Responsabilidad)
        log.debug("Validando correo único: {}", docente.getCorreo());

        if (validacionesGateway.existeDocentePorCorreo(docente.getCorreo())) {
            log.error("Error: Ya existe un docente con el correo: {}", docente.getCorreo());
            formateador.retornarRespuestaErrorEntidadExiste(
                    "Ya existe un docente con el correo: " + docente.getCorreo());
        }

        log.debug("✓ Correo único validado correctamente");

        // Si pasa validaciones, guardar (cascade PERSIST maneja oficina)
        log.debug("Guardando docente en BD (con cascade PERSIST para oficina)");
        Docente docenteGuardado = gateway.guardar(docente);

        log.info("Docente creado exitosamente con ID: {}", docenteGuardado.getId());
        log.debug("Docente guardado: {}", docenteGuardado);

        return docenteGuardado;
    }
}