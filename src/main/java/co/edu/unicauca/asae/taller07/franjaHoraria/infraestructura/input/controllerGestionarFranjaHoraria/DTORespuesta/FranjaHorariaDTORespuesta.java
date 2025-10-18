package co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.input.controllerGestionarFranjaHoraria.DTORespuesta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FranjaHorariaDTORespuesta {
    private int id;
    private String dia;
    private String horaInicio;
    private String horaFin;
    private Integer cursoId;
    private Integer espacioFisicoId;
}
