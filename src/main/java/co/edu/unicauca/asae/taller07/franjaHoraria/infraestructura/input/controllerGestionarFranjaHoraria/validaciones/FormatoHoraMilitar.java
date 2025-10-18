package co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.input.controllerGestionarFranjaHoraria.validaciones;

import java.lang.annotation.*;

import jakarta.validation.Payload;
import jakarta.validation.Constraint;

/**
 * Validación personalizada: Formato militar HH:mm (00:00 a 23:59)
 */
@Documented
@Constraint(validatedBy = FormatoHoraMilitarValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface FormatoHoraMilitar {
    String message() default "La hora debe estar en formato militar HH:mm (00:00 a 23:59)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}