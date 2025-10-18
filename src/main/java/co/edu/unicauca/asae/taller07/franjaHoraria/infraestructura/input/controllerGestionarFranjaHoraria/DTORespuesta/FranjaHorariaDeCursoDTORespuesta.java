package co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.input.controllerGestionarFranjaHoraria.DTORespuesta;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para listar franjas de un curso (LAZY: curso, EAGER: espacios y docentes)
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FranjaHorariaDeCursoDTORespuesta {
    private int id;
    private String dia;
    private String horaInicio;
    private String horaFin;
    private EspacioFisicoDTORespuesta espacioFisico;
    private Set<DocenteDTORespuesta> docentes;
}