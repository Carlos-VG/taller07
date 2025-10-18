package co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representación de Curso para el dominio FranjaHoraria (POJO puro)
 * NO es la misma clase que curso.dominio.modelos.Curso
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Curso {
    private int id;
    private String nombre;
    private Asignatura asignatura;
    private Set<Docente> docentes = new HashSet<>();
    private Integer matriculaEstimada;
}