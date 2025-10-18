package co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Curso {
    private int id;
    private String nombre;
    private Asignatura asignatura;
    private Set<Docente> docentes = new HashSet<>();
    private Integer matriculaEstimada; // Para validación personalizada
}