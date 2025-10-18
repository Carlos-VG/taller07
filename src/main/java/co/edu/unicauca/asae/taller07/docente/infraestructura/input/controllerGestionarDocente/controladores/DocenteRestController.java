package co.edu.unicauca.asae.taller07.docente.infraestructura.input.controllerGestionarDocente.controladores;

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

    private final GestionarDocenteCUIntPort gestionarDocenteCU;
    private final DocenteMapperInfraestructuraDominio mapper;

    /**
     * Crear docente con persona y oficina (cascade PERSIST)
     * POST /api/docentes
     */
    @PostMapping
    public ResponseEntity<DocenteDTORespuesta> crear(@RequestBody @Valid DocenteDTOPeticion peticion) {

        Docente docenteACrear = mapper.mappearDePeticionADocente(peticion);
        Docente docenteCreado = gestionarDocenteCU.crear(docenteACrear);

        return new ResponseEntity<>(
                mapper.mappearDeDocenteARespuesta(docenteCreado),
                HttpStatus.CREATED);
    }
}