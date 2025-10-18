package co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.input.controllerGestionarFranjaHoraria.controladores;

import java.util.List;

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

    private final GestionarFranjaHorariaCUIntPort gestionarFranjaHorariaCU;
    private final ConsultarFranjaHorariaCUIntPort consultarFranjaHorariaCU;
    private final FranjaHorariaMapperInfraestructuraDominio mapper;

    /**
     * Crear franja horaria
     * POST /api/franjas-horarias
     */
    @PostMapping
    public ResponseEntity<FranjaHorariaDTORespuesta> crear(@RequestBody @Valid FranjaHorariaDTOPeticion peticion) {

        FranjaHoraria franjaACrear = mapper.mappearDePeticionAFranjaHoraria(peticion);
        FranjaHoraria franjaCreada = gestionarFranjaHorariaCU.crear(franjaACrear);

        return new ResponseEntity<>(
                mapper.mappearDeFranjaHorariaARespuesta(franjaCreada),
                HttpStatus.CREATED);
    }

    /**
     * Listar franjas horarias por docente (EAGER: cursos y espacios)
     * GET /api/franjas-horarias/docente/{docenteId}
     */
    @GetMapping("/docente/{docenteId}")
    public ResponseEntity<List<FranjaHorariaDeDocenteDTORespuesta>> listarPorDocente(
            @PathVariable @Min(value = 1, message = "{franjaHoraria.docenteId.min}") int docenteId) {

        List<FranjaHoraria> franjas = consultarFranjaHorariaCU.listarPorDocente(docenteId);

        return new ResponseEntity<>(
                mapper.mappearListaDeFranjaHorariaADocenteRespuesta(franjas),
                HttpStatus.OK);
    }

    /**
     * Listar franjas horarias por curso (LAZY: curso, EAGER: espacios y docentes)
     * GET /api/franjas-horarias/curso/{cursoId}
     */
    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<FranjaHorariaDeCursoDTORespuesta>> listarPorCurso(
            @PathVariable @Min(value = 1, message = "{franjaHoraria.cursoId.min}") int cursoId) {

        List<FranjaHoraria> franjas = consultarFranjaHorariaCU.listarPorCurso(cursoId);

        return new ResponseEntity<>(
                mapper.mappearListaDeFranjaHorariaACursoRespuesta(franjas),
                HttpStatus.OK);
    }

    /**
     * QUERY JOIN MÚLTIPLE - Obtener detalle de franjas de un curso
     * GET /api/franjas-horarias/curso/{cursoId}/detalle
     */
    @GetMapping("/curso/{cursoId}/detalle")
    public ResponseEntity<List<Object[]>> obtenerDetalleFranjasCurso(
            @PathVariable @Min(value = 1, message = "{franjaHoraria.cursoId.min}") int cursoId) {

        List<Object[]> detalle = consultarFranjaHorariaCU.obtenerDetalleFranjasCurso(cursoId);

        return new ResponseEntity<>(detalle, HttpStatus.OK);
    }

    /**
     * QUERY DELETE - Eliminar franjas de un curso
     * DELETE /api/franjas-horarias/curso/{cursoId}
     */
    @DeleteMapping("/curso/{cursoId}")
    public ResponseEntity<String> eliminarFranjasPorCurso(
            @PathVariable @Min(value = 1, message = "{franjaHoraria.cursoId.min}") int cursoId) {

        int eliminadas = gestionarFranjaHorariaCU.eliminarFranjasPorCurso(cursoId);

        return new ResponseEntity<>(
                String.format("Se eliminaron %d franja(s) horaria(s) del curso", eliminadas),
                HttpStatus.OK);
    }
}