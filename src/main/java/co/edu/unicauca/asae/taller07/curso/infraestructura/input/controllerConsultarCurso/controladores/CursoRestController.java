package co.edu.unicauca.asae.taller07.curso.infraestructura.input.controllerConsultarCurso.controladores;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unicauca.asae.taller07.curso.aplicacion.input.ConsultarCursoCUIntPort;
import co.edu.unicauca.asae.taller07.curso.dominio.modelos.Curso;
import co.edu.unicauca.asae.taller07.curso.infraestructura.input.controllerConsultarCurso.DTORespuesta.CursoDTORespuesta;
import co.edu.unicauca.asae.taller07.curso.infraestructura.input.controllerConsultarCurso.mappers.CursoMapperInfraestructuraDominio;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST para consultas de cursos
 */
@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
public class CursoRestController {

    private final ConsultarCursoCUIntPort consultarCursoCU;
    private final CursoMapperInfraestructuraDominio mapper;

    /**
     * KEYWORD - Listar cursos por nombre de asignatura
     * GET /api/cursos?asignatura=Bases de Datos II
     */
    @GetMapping
    public ResponseEntity<List<CursoDTORespuesta>> listarPorAsignatura(
            @RequestParam String asignatura) {

        List<Curso> cursos = consultarCursoCU.listarPorNombreAsignatura(asignatura);

        return new ResponseEntity<>(
                mapper.mappearDeCursosARespuesta(cursos),
                HttpStatus.OK);
    }
}