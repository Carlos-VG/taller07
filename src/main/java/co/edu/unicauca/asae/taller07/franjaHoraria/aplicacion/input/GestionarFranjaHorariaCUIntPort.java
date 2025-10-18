package co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.input;

import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.FranjaHoraria;

public interface GestionarFranjaHorariaCUIntPort {
    /**
     * Crea una franja horaria validando:
     * - Espacio físico no ocupado
     * - Docente no ocupado
     * - Curso, espacio y docente existen
     */
    FranjaHoraria crear(FranjaHoraria franjaHoraria);

    /**
     * Elimina todas las franjas horarias de un curso
     */
    int eliminarFranjasPorCurso(int cursoId);
}