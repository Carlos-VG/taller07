package co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.input;

import java.util.List;
import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.FranjaHoraria;

public interface ConsultarFranjaHorariaCUIntPort {
    /**
     * Lista franjas horarias de un docente (EAGER: cursos y espacios)
     */
    List<FranjaHoraria> listarPorDocente(int docenteId);

    /**
     * Lista franjas horarias de un curso (LAZY: curso, EAGER: espacios y docentes)
     */
    List<FranjaHoraria> listarPorCurso(int cursoId);

    /**
     * Obtiene detalle completo de franjas de un curso (JOIN múltiple)
     */
    List<Object[]> obtenerDetalleFranjasCurso(int cursoId);
}