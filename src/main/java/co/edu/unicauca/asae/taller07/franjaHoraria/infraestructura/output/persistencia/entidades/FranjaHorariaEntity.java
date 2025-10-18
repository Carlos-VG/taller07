package co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

// IMPORTS DE ENTIDADES DE OTROS DOMINIOS (path completo)
import co.edu.unicauca.asae.taller07.curso.infraestructura.output.persistencia.entidades.CursoEntity;
import co.edu.unicauca.asae.taller07.espacioFisico.infraestructura.output.persistencia.entidades.EspacioFisicoEntity;

/**
 * Entidad JPA para Franja Horaria
 * Usa referencias a entidades de otros dominios (NO las duplica)
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "franja_horaria")
public class FranjaHorariaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia", nullable = false, length = 20)
    private DiaSemana dia;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    /**
     * REFERENCIA A ENTITY DEL DOMINIO CURSO
     * Usa la entidad original, NO una copia
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "curso_id")
    private CursoEntity curso;

    /**
     * REFERENCIA A ENTITY DEL DOMINIO ESPACIOFISICO
     * Usa la entidad original, NO una copia
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "espacio_fisico_id")
    private EspacioFisicoEntity espacioFisico;
}