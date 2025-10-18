package co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "curso")
public class CursoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "asignatura_id")
    private AsignaturaEntity asignatura;

    @ManyToMany
    @JoinTable(name = "curso_docente", joinColumns = @JoinColumn(name = "curso_id"), inverseJoinColumns = @JoinColumn(name = "docente_id"))
    private Set<DocenteEntity> docentes = new HashSet<>();

    @OneToMany(mappedBy = "curso", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<FranjaHorariaEntity> franjas = new ArrayList<>();

    // Nueva columna para validación personalizada
    @Column(name = "matricula_estimada")
    private Integer matriculaEstimada;
}