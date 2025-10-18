package co.edu.unicauca.asae.taller07.docente.dominio.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Modelo de dominio para Docente (POJO, hereda de Persona)
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Docente extends Persona {
    private Oficina oficina;

    public Docente(int id, String nombre, String apellido, String correo, Oficina oficina) {
        super(id, nombre, apellido, correo);
        this.oficina = oficina;
    }
}