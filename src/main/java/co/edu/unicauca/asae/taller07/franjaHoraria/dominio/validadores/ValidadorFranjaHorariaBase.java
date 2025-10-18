package co.edu.unicauca.asae.taller07.franjaHoraria.dominio.validadores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.FranjaHoraria;

/**
 * Clase base que implementa la lógica de encadenamiento
 */
public abstract class ValidadorFranjaHorariaBase implements ValidadorFranjaHoraria {

    private static final Logger log = LoggerFactory.getLogger(ValidadorFranjaHorariaBase.class);

    protected ValidadorFranjaHoraria siguiente;

    @Override
    public void setSiguiente(ValidadorFranjaHoraria siguiente) {
        this.siguiente = siguiente;
        log.trace("Encadenado: {} → {}", this.getClass().getSimpleName(),
                siguiente != null ? siguiente.getClass().getSimpleName() : "null");
    }

    @Override
    public void validar(FranjaHoraria franjaHoraria) {
        String nombreValidador = this.getClass().getSimpleName();

        log.info("[{}] Iniciando validación...", nombreValidador);

        try {
            // Ejecuta la validación específica de esta clase
            ejecutarValidacion(franjaHoraria);

            log.info("[{}] Validación EXITOSA", nombreValidador);

        } catch (Exception e) {
            log.error("[{}] Validación FALLIDA: {}", nombreValidador, e.getMessage());
            throw e; // Re-lanzar la excepción
        }

        // Si pasa, continúa con el siguiente validador
        if (siguiente != null) {
            log.debug("Pasando al siguiente validador: {}", siguiente.getClass().getSimpleName());
            siguiente.validar(franjaHoraria);
        } else {
            log.debug("Fin de la cadena de validación");
        }
    }

    /**
     * Método abstracto que cada validador concreto debe implementar
     */
    protected abstract void ejecutarValidacion(FranjaHoraria franjaHoraria);
}