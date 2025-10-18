package co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.input.controllerGestionarFranjaHoraria.DTOPeticion;

import co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.input.controllerGestionarFranjaHoraria.validaciones.FormatoHoraMilitar;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.input.controllerGestionarFranjaHoraria.validaciones.CapacidadSuficiente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@CapacidadSuficiente(message = "{franjaHoraria.capacidadInsuficiente}")
public class FranjaHorariaDTOPeticion {

    @NotNull(message = "{franjaHoraria.dia.notNull}")
    private String dia;

    @NotNull(message = "{franjaHoraria.horaInicio.notNull}")
    @FormatoHoraMilitar(message = "{franjaHoraria.horaInicio.formatoMilitar}")
    private String horaInicio; // Formato "HH:mm"

    @NotNull(message = "{franjaHoraria.horaFin.notNull}")
    @FormatoHoraMilitar(message = "{franjaHoraria.horaFin.formatoMilitar}")
    private String horaFin; // Formato "HH:mm"

    @NotNull(message = "{franjaHoraria.cursoId.notNull}")
    @Min(value = 1, message = "{franjaHoraria.cursoId.min}")
    private Integer cursoId;

    @NotNull(message = "{franjaHoraria.espacioFisicoId.notNull}")
    @Min(value = 1, message = "{franjaHoraria.espacioFisicoId.min}")
    private Integer espacioFisicoId;
}
