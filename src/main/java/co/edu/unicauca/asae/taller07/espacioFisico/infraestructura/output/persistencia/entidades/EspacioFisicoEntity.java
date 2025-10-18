package co.edu.unicauca.asae.taller07.espacioFisico.infraestructura.output.persistencia.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad JPA para Espacio Físico
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "espacio_fisico", uniqueConstraints = @UniqueConstraint(columnNames = "nombre"))
public class EspacioFisicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nombre", nullable = false, unique = true, length = 255)
    private String nombre;

    @Column(name = "capacidad")
    private Integer capacidad;

    @Column(name = "activo", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    private boolean activo = true;
}