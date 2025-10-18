package co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "administrativo")
@PrimaryKeyJoinColumn(name = "id")
public class AdministrativoEntity extends PersonaEntity {

    @Column(name = "rol", nullable = false, length = 255)
    private String rol;
}