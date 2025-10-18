package co.edu.unicauca.asae.taller07.docente.infraestructura.output.persistencia.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad JPA para Docente (hereda de PersonaEntity)
 * Mantiene cascade PERSIST para oficina
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "docente")
@PrimaryKeyJoinColumn(name = "id") // PK=FK hacia persona.id
public class DocenteEntity extends PersonaEntity {

    /**
     * Oficina asignada (opcional).
     * CascadeType.PERSIST permite persistir la oficina al guardar el docente
     */
    @OneToOne(optional = true, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "oficina_id", unique = true)
    private OficinaEntity oficina;

    public DocenteEntity(int id, String nombre, String apellido, String correo, OficinaEntity oficina) {
        super(id, nombre, apellido, correo);
        this.oficina = oficina;
    }
}