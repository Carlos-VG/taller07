package co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.input.controllerGestionarFranjaHoraria.validaciones;

import java.lang.annotation.*;

import jakarta.validation.Payload;
import jakarta.validation.Constraint;

/**
 * Validación personalizada: Capacidad del espacio >= matrícula estimada del
 * curso
 */
@Documented
@Constraint(validatedBy = CapacidadSuficienteValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CapacidadSuficiente {
    String message() default "La capacidad del espacio físico es insuficiente para la matrícula estimada del curso";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}