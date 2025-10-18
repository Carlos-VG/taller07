package co.edu.unicauca.asae.taller07.espacioFisico.dominio.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Modelo de dominio para Espacio Físico (POJO Sin anotaciones JPA)
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