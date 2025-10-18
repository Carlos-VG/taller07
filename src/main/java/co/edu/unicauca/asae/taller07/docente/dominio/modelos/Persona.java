package co.edu.unicauca.asae.taller07.docente.dominio.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Modelo de dominio para Persona (POJO)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Persona {
    private int id;
    private String nombre;
    private String apellido;
    private String correo;
}