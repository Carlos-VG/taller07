package co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output;

import java.time.LocalTime;
import java.util.Set;

import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.DiaSemana;
import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.Docente;

/**
 * Puerto de salida para validaciones de reglas de negocio
 * Patrón Cadena de Responsabilidad
 */
public interface ValidacionesFranjaHorariaGatewayIntPort {

    /**
     * Verifica si un espacio físico está ocupado en un horario (JPQL)
     */
    boolean espacioFisicoOcupado(DiaSemana dia, LocalTime horaInicio, LocalTime horaFin, int espacioId);

    /**
     * Verifica si un docente está ocupado en un horario (SQL Nativo)
     */
    boolean docenteOcupado(String dia, LocalTime horaInicio, LocalTime horaFin, int docenteId);

    /**
     * Verifica si existe un curso por ID
     */
    boolean cursoExiste(int cursoId);

    /**
     * Verifica si existe un espacio físico por ID
     */
    boolean espacioFisicoExiste(int espacioId);

    /**
     * Verifica si existe un docente por ID
     */
    boolean docenteExiste(int docenteId);

    Set<Docente> obtenerDocentesDeCurso(int cursoId);
}