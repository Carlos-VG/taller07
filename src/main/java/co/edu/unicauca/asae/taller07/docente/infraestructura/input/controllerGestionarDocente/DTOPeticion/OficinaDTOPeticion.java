package co.edu.unicauca.asae.taller07.docente.infraestructura.input.controllerGestionarDocente.DTOPeticion;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OficinaDTOPeticion {

    @NotEmpty(message = "{oficina.nombre.notEmpty}")
    @Size(min = 3, max = 20, message = "{oficina.nombre.size}")
    private String nombre;

    @NotEmpty(message = "{oficina.ubicacion.notEmpty}")
    @Size(min = 5, max = 20, message = "{oficina.ubicacion.size}")
    private String ubicacion;
}