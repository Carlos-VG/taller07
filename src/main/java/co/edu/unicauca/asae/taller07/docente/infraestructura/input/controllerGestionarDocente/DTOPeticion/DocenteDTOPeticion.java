package co.edu.unicauca.asae.taller07.docente.infraestructura.input.controllerGestionarDocente.DTOPeticion;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocenteDTOPeticion {

    @NotEmpty(message = "{docente.nombre.notEmpty}")
    @Size(min = 2, max = 50, message = "{docente.nombre.size}")
    private String nombre;

    @NotEmpty(message = "{docente.apellido.notEmpty}")
    @Size(min = 2, max = 50, message = "{docente.apellido.size}")
    private String apellido;

    @NotEmpty(message = "{docente.correo.notEmpty}")
    @Email(message = "{docente.correo.email}")
    @Size(max = 50, message = "{docente.correo.size}")
    private String correo;

    @Valid
    @NotNull(message = "{docente.oficina.notNull}")
    private OficinaDTOPeticion oficina;
}