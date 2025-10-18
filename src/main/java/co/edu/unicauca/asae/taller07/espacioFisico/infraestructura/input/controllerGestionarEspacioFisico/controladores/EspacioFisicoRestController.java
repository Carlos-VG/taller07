package co.edu.unicauca.asae.taller07.espacioFisico.infraestructura.input.controllerGestionarEspacioFisico.controladores;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import co.edu.unicauca.asae.taller07.espacioFisico.aplicacion.input.ConsultarEspacioFisicoCUIntPort;
import co.edu.unicauca.asae.taller07.espacioFisico.aplicacion.input.GestionarEspacioFisicoCUIntPort;
import co.edu.unicauca.asae.taller07.espacioFisico.dominio.modelos.EspacioFisico;
import co.edu.unicauca.asae.taller07.espacioFisico.infraestructura.input.controllerGestionarEspacioFisico.DTOPeticion.EspacioFisicoDTOPeticion;
import co.edu.unicauca.asae.taller07.espacioFisico.infraestructura.input.controllerGestionarEspacioFisico.DTORespuesta.EspacioFisicoDTORespuesta;
import co.edu.unicauca.asae.taller07.espacioFisico.infraestructura.input.controllerGestionarEspacioFisico.mappers.EspacioFisicoMapperInfraestructuraDominio;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST para gestión de espacios físicos
 */
@RestController
@RequestMapping("/api/espacios-fisicos")
@RequiredArgsConstructor
@Validated
public class EspacioFisicoRestController {

    private static final Logger log = LoggerFactory.getLogger(EspacioFisicoRestController.class);

    private final GestionarEspacioFisicoCUIntPort gestionarEspacioFisicoCU;
    private final ConsultarEspacioFisicoCUIntPort consultarEspacioFisicoCU;
    private final EspacioFisicoMapperInfraestructuraDominio mapper;

    /**
     * KEYWORD - Listar espacios físicos por patrón y capacidad
     * GET /api/espacios-fisicos?patron=Lab&capacidad=20
     */
    @GetMapping
    public ResponseEntity<List<EspacioFisicoDTORespuesta>> listarPorPatronYCapacidad(
            @RequestParam(required = false, defaultValue = "") String patron,
            @RequestParam(required = false, defaultValue = "0") @Min(value = 0, message = "{espacioFisico.capacidad.min}") Integer capacidad) {

        log.info("GET /api/espacios-fisicos - Patrón: '{}', Capacidad >= {}", patron, capacidad);

        List<EspacioFisico> espacios = consultarEspacioFisicoCU.listarPorPatronYCapacidad(patron, capacidad);
        List<EspacioFisicoDTORespuesta> respuesta = mapper.mappearDeEspaciosFisicosARespuesta(espacios);

        log.info("Respuesta: {} espacio(s) encontrado(s)", respuesta.size());

        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    /**
     * QUERY UPDATE - Actualizar estado de espacio físico
     * PATCH /api/espacios-fisicos/{id}/estado
     */
    @PatchMapping("/{id}/estado")
    public ResponseEntity<String> actualizarEstado(
            @PathVariable @Min(value = 1, message = "{espacioFisico.id.min}") int id,
            @RequestBody @Valid EspacioFisicoDTOPeticion peticion) {

        log.info("PATCH /api/espacios-fisicos/{}/estado - Nuevo estado: {}", id, peticion.getActivo());

        int filasActualizadas = gestionarEspacioFisicoCU.actualizarEstado(id, peticion.getActivo());

        if (filasActualizadas > 0) {
            String mensaje = String.format("Estado actualizado a '%s'",
                    peticion.getActivo() ? "ACTIVO" : "INACTIVO");
            log.info("Éxito: {} fila(s) actualizada(s)", filasActualizadas);
            return new ResponseEntity<>(mensaje, HttpStatus.OK);
        } else {
            log.warn("Sin cambios: 0 filas actualizadas");
            return new ResponseEntity<>("No se pudo actualizar el estado", HttpStatus.BAD_REQUEST);
        }
    }
}