package co.edu.unicauca.asae.taller07.curso.infraestructura.input.controllerConsultarCurso.DTORespuesta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CursoDTORespuesta {
    private int id;
    private String nombre;
    private AsignaturaDTORespuesta asignatura;
}