package co.edu.unicauca.asae.taller07.docente.dominio.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Modelo de dominio para Oficina (POJO)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Oficina {
    private int id;
    private String nombre;
    private String ubicacion;
}