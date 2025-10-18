package co.edu.unicauca.asae.taller07.curso.aplicacion.input;

import java.util.List;
import co.edu.unicauca.asae.taller07.curso.dominio.modelos.Curso;

public interface ConsultarCursoCUIntPort {
    /**
     * KEYWORD - Obtener cursos por nombre de asignatura
     */
    List<Curso> listarPorNombreAsignatura(String nombreAsignatura);
}