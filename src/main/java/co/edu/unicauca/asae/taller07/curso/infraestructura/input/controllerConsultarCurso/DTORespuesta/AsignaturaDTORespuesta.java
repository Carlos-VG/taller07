package co.edu.unicauca.asae.taller07.curso.infraestructura.input.controllerConsultarCurso.DTORespuesta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AsignaturaDTORespuesta {
    private int id;
    private String nombre;
    private String codigo;
}