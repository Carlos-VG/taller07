package co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representación de EspacioFisico para el dominio FranjaHoraria (POJO puro)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EspacioFisico {
    private int id;
    private String nombre;
    private Integer capacidad;
    private boolean activo;
}