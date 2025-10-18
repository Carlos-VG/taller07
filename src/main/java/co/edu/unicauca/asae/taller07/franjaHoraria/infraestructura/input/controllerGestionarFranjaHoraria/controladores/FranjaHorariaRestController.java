package co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.input.controllerGestionarFranjaHoraria.controladores;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.input.ConsultarFranjaHorariaCUIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.input.GestionarFranjaHorariaCUIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.FranjaHoraria;
import co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.input.controllerGestionarFranjaHoraria.DTOPeticion.FranjaHorariaDTOPeticion;
import co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.input.controllerGestionarFranjaHoraria.DTORespuesta.*;
import co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.input.controllerGestionarFranjaHoraria.mappers.FranjaHorariaMapperInfraestructuraDominio;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST para gestión de franjas horarias
 */
@RestController
@RequestMapping("/api/franjas-horarias")
@RequiredArgsConstructor
@Validated
public class FranjaHorariaRestController {

    private static final Logger log = LoggerFactory.getLogger(FranjaHorariaRestController.class);

    private final GestionarFranjaHorariaCUIntPort gestionarFranjaHorariaCU;
    private final ConsultarFranjaHorariaCUIntPort consultarFranjaHorariaCU;
    private final FranjaHorariaMapperInfraestructuraDominio mapper;

    /**
     * Crear franja horaria
     * POST /api/franjas-horarias
     */
    @PostMapping
    public ResponseEntity<FranjaHorariaDTORespuesta> crear(@RequestBody @Valid FranjaHorariaDTOPeticion peticion) {

        log.info("╔════════════════════════════════════════════════════════════════════════╗");
        log.info("║              POST /api/franjas-horarias - CREAR FRANJA HORARIA         ║");
        log.info("╚════════════════════════════════════════════════════════════════════════╝");
        log.info("Payload: Día: {} | {} - {} | Curso: {} | Espacio: {}",
                peticion.getDia(),
                peticion.getHoraInicio(),
                peticion.getHoraFin(),
                peticion.getCursoId(),
                peticion.getEspacioFisicoId());

        if (log.isDebugEnabled()) {
            log.debug("DTO Petición completo: {}", peticion);
        }

        log.debug("Mapeando DTO petición → modelo de dominio");
        FranjaHoraria franjaACrear = mapper.mappearDePeticionAFranjaHoraria(peticion);

        if (log.isTraceEnabled()) {
            log.trace("Modelo de dominio creado: {}", franjaACrear);
        }

        log.info("Iniciando proceso de creación...");
        FranjaHoraria franjaCreada = gestionarFranjaHorariaCU.crear(franjaACrear);
        log.info("Proceso de creación finalizado exitosamente");

        log.debug("Mapeando modelo de dominio → DTO respuesta");
        FranjaHorariaDTORespuesta respuesta = mapper.mappearDeFranjaHorariaARespuesta(franjaCreada);

        log.info("╔════════════════════════════════════════════════════════════════════════╗");
        log.info("║                 Respuesta HTTP 201 CREATED - ID: {}                  ║",
                String.format("%-4d", respuesta.getId()));
        log.info("╚════════════════════════════════════════════════════════════════════════╝");

        if (log.isDebugEnabled()) {
            log.debug("DTO Respuesta: {}", respuesta);
        }

        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    /**
     * Listar franjas horarias por docente (EAGER: cursos y espacios)
     * GET /api/franjas-horarias/docente/{docenteId}
     */
    @GetMapping("/docente/{docenteId}")
    public ResponseEntity<List<FranjaHorariaDeDocenteDTORespuesta>> listarPorDocente(
            @PathVariable @Min(value = 1, message = "{franjaHoraria.docenteId.min}") int docenteId) {

        log.info("GET /api/franjas-horarias/docente/{}", docenteId);

        List<FranjaHoraria> franjas = consultarFranjaHorariaCU.listarPorDocente(docenteId);

        log.debug("Mapeando {} franja(s) → DTOs de respuesta", franjas.size());
        List<FranjaHorariaDeDocenteDTORespuesta> respuesta = mapper
                .mappearListaDeFranjaHorariaADocenteRespuesta(franjas);

        log.info("Respuesta HTTP 200 OK con {} franja(s)", respuesta.size());

        if (log.isDebugEnabled()) {
            respuesta.forEach(f -> log.debug("  - Franja ID: {} | {} | {} - {}",
                    f.getId(), f.getDia(), f.getHoraInicio(), f.getHoraFin()));
        }

        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    /**
     * Listar franjas horarias por curso (LAZY: curso, EAGER: espacios y docentes)
     * GET /api/franjas-horarias/curso/{cursoId}
     */
    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<FranjaHorariaDeCursoDTORespuesta>> listarPorCurso(
            @PathVariable @Min(value = 1, message = "{franjaHoraria.cursoId.min}") int cursoId) {

        log.info("GET /api/franjas-horarias/curso/{}", cursoId);

        List<FranjaHoraria> franjas = consultarFranjaHorariaCU.listarPorCurso(cursoId);

        log.debug("Mapeando {} franja(s) → DTOs de respuesta", franjas.size());
        List<FranjaHorariaDeCursoDTORespuesta> respuesta = mapper.mappearListaDeFranjaHorariaACursoRespuesta(franjas);

        log.info("Respuesta HTTP 200 OK con {} franja(s)", respuesta.size());

        if (log.isDebugEnabled()) {
            respuesta.forEach(f -> log.debug("  - Franja ID: {} | {} | {} - {}",
                    f.getId(), f.getDia(), f.getHoraInicio(), f.getHoraFin()));
        }

        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    /**
     * QUERY JOIN MÚLTIPLE - Obtener detalle de franjas de un curso
     * GET /api/franjas-horarias/curso/{cursoId}/detalle
     */
    @GetMapping("/curso/{cursoId}/detalle")
    public ResponseEntity<List<Object[]>> obtenerDetalleFranjasCurso(
            @PathVariable @Min(value = 1, message = "{franjaHoraria.cursoId.min}") int cursoId) {

        log.info("GET /api/franjas-horarias/curso/{}/detalle (JOIN múltiple)", cursoId);

        List<Object[]> detalle = consultarFranjaHorariaCU.obtenerDetalleFranjasCurso(cursoId);

        log.info("Respuesta HTTP 200 OK con {} fila(s) de detalle", detalle.size());

        if (log.isDebugEnabled()) {
            detalle.forEach(fila -> log.debug("  - Fila: {}", java.util.Arrays.toString(fila)));
        }

        return new ResponseEntity<>(detalle, HttpStatus.OK);
    }

    /**
     * QUERY DELETE - Eliminar franjas de un curso
     * DELETE /api/franjas-horarias/curso/{cursoId}
     */
    @DeleteMapping("/curso/{cursoId}")
    public ResponseEntity<String> eliminarFranjasPorCurso(
            @PathVariable @Min(value = 1, message = "{franjaHoraria.cursoId.min}") int cursoId) {

        log.info("DELETE /api/franjas-horarias/curso/{}", cursoId);

        int eliminadas = gestionarFranjaHorariaCU.eliminarFranjasPorCurso(cursoId);

        String mensaje = String.format("Se eliminaron %d franja(s) horaria(s) del curso", eliminadas);

        log.info("Respuesta HTTP 200 OK: {}", mensaje);

        return new ResponseEntity<>(mensaje, HttpStatus.OK);
    }
}