package co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "docente")
@PrimaryKeyJoinColumn(name = "id")
public class DocenteEntity extends PersonaEntity {

    @OneToOne(optional = true, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "oficina_id", unique = true)
    private OficinaEntity oficina;

    public DocenteEntity(int id, String nombre, String apellido, String correo, OficinaEntity oficina) {
        super(id, nombre, apellido, correo);
        this.oficina = oficina;
    }
}