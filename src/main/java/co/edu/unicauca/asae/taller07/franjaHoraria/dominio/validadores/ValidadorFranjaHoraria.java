package co.edu.unicauca.asae.taller07.franjaHoraria.dominio.validadores;

import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.FranjaHoraria;

/**
 * Interfaz base para el patrón Cadena de Responsabilidad
 * Cada validador decide si procesa la validación o la pasa al siguiente
 */
public interface ValidadorFranjaHoraria {

    /**
     * Establece el siguiente validador en la cadena
     */
    void setSiguiente(ValidadorFranjaHoraria siguiente);

    /**
     * Valida la franja horaria y pasa al siguiente si todo está OK
     * 
     * @throws RuntimeException si la validación falla
     */
    void validar(FranjaHoraria franjaHoraria);
}