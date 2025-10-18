package co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representación de Asignatura para el dominio FranjaHoraria (POJO puro)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Asignatura {
    private int id;
    private String nombre;
    private String codigo;
}