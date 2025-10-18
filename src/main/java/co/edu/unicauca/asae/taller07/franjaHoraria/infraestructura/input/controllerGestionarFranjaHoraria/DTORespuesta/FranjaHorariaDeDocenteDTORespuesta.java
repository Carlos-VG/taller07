package co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.input.controllerGestionarFranjaHoraria.DTORespuesta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para listar franjas de un docente (EAGER: cursos y espacios)
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FranjaHorariaDeDocenteDTORespuesta {
    private int id;
    private String dia;
    private String horaInicio;
    private String horaFin;
    private CursoDTORespuesta curso;
    private EspacioFisicoDTORespuesta espacioFisico;
}
