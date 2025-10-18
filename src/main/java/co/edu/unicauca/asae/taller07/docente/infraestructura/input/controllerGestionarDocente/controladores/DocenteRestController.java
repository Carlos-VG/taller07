package co.edu.unicauca.asae.taller07.docente.infraestructura.input.controllerGestionarDocente.controladores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unicauca.asae.taller07.docente.aplicacion.input.GestionarDocenteCUIntPort;
import co.edu.unicauca.asae.taller07.docente.dominio.modelos.Docente;
import co.edu.unicauca.asae.taller07.docente.infraestructura.input.controllerGestionarDocente.DTOPeticion.DocenteDTOPeticion;
import co.edu.unicauca.asae.taller07.docente.infraestructura.input.controllerGestionarDocente.DTORespuesta.DocenteDTORespuesta;
import co.edu.unicauca.asae.taller07.docente.infraestructura.input.controllerGestionarDocente.mappers.DocenteMapperInfraestructuraDominio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST para gestión de docentes
 */
@RestController
@RequestMapping("/api/docentes")
@RequiredArgsConstructor
public class DocenteRestController {

    private static final Logger log = LoggerFactory.getLogger(DocenteRestController.class);

    private final GestionarDocenteCUIntPort gestionarDocenteCU;
    private final DocenteMapperInfraestructuraDominio mapper;

    /**
     * Crear docente con persona y oficina (cascade PERSIST)
     * POST /api/docentes
     */
    @PostMapping
    public ResponseEntity<DocenteDTORespuesta> crear(@RequestBody @Valid DocenteDTOPeticion peticion) {

        log.info("POST /api/docentes - Crear docente: {} {}",
                peticion.getNombre(), peticion.getApellido());
        log.debug("Payload recibido: {}", peticion);

        Docente docenteACrear = mapper.mappearDePeticionADocente(peticion);
        log.debug("DTO → Modelo de dominio realizado");

        Docente docenteCreado = gestionarDocenteCU.crear(docenteACrear);

        DocenteDTORespuesta respuesta = mapper.mappearDeDocenteARespuesta(docenteCreado);
        log.debug("Modelo de dominio → DTO respuesta realizado");

        log.info("Respuesta HTTP 201 CREATED - Docente ID: {}", respuesta.getId());
        log.debug("DTO de respuesta: {}", respuesta);

        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }
}