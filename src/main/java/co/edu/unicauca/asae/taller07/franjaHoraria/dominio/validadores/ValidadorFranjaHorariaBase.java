package co.edu.unicauca.asae.taller07.franjaHoraria.dominio.validadores;

import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.FranjaHoraria;

/**
 * Clase base que implementa la lógica de encadenamiento
 */
public abstract class ValidadorFranjaHorariaBase implements ValidadorFranjaHoraria {

    protected ValidadorFranjaHoraria siguiente;

    @Override
    public void setSiguiente(ValidadorFranjaHoraria siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public void validar(FranjaHoraria franjaHoraria) {
        // Ejecuta la validación específica de esta clase
        ejecutarValidacion(franjaHoraria);

        // Si pasa, continúa con el siguiente validador
        if (siguiente != null) {
            siguiente.validar(franjaHoraria);
        }
    }

    /**
     * Método abstracto que cada validador concreto debe implementar
     */
    protected abstract void ejecutarValidacion(FranjaHoraria franjaHoraria);
}