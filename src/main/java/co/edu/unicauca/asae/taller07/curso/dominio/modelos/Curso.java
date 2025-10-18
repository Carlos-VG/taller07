package co.edu.unicauca.asae.taller07.curso.dominio.modelos;

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
}
