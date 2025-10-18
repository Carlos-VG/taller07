package co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.input.controllerGestionarFranjaHoraria.validaciones;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validador de formato militar HH:mm
 */
public class FormatoHoraMilitarValidator implements ConstraintValidator<FormatoHoraMilitar, String> {

    private static final Pattern PATTERN = Pattern.compile("^([01]\\d|2[0-3]):([0-5]\\d)$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        return PATTERN.matcher(value).matches();
    }
}
