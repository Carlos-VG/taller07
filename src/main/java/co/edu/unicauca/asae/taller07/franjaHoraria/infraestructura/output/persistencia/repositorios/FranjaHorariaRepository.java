package co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.repositorios;

import co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.entidades.DiaSemana;
import co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.entidades.FranjaHorariaEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

/**
 * Repositorio para FranjaHoraria con queries del Taller 6
 * Reutiliza todas las queries implementadas
 */
public interface FranjaHorariaRepository extends JpaRepository<FranjaHorariaEntity, Integer> {

    /**
     * TALLER 6 - PUNTO 3: KEYWORD EN TABLAS RELACIONADAS
     * Franjas de un curso con curso+espacio en fetch (EAGER puntual)
     */
    @EntityGraph(attributePaths = { "curso", "curso.docentes", "espacioFisico" })
    List<FranjaHorariaEntity> findByCurso_Id(int cursoId);

    /**
     * Franjas de un docente, con curso en fetch y espacio LAZY
     */
    @Query("""
            SELECT DISTINCT f
            FROM FranjaHorariaEntity f
            JOIN f.curso c2
            JOIN c2.docentes d
            LEFT JOIN FETCH f.curso c
            LEFT JOIN FETCH c.docentes
            WHERE d.id = :docenteId
            """)
    List<FranjaHorariaEntity> findByDocenteIdFetchCurso(@Param("docenteId") int docenteId);

    /**
     * TALLER 6 - PUNTO 4: JPQL (VALIDACIÓN)
     * Verifica si espacio físico está ocupado en un horario
     * Retorna el count de franjas que se solapan
     */
    @Query("""
            SELECT COUNT(f)
            FROM FranjaHorariaEntity f
            JOIN f.espacioFisico e
            WHERE e.id = :espacioId
              AND f.dia = :dia
              AND f.horaInicio < :horaFin
              AND f.horaFin > :horaInicio
            """)
    long countOcupacionEspacio(@Param("dia") DiaSemana dia,
            @Param("horaInicio") LocalTime horaInicio,
            @Param("horaFin") LocalTime horaFin,
            @Param("espacioId") int espacioId);

    /**
     * TALLER 6 - PUNTO 5: SQL NATIVA (VALIDACIÓN)
     * Verifica si docente está ocupado en un horario
     * Retorna el count de franjas que se solapan
     */
    @Query(value = """
            SELECT COUNT(f.id)
            FROM franja_horaria f
            JOIN curso c ON f.curso_id = c.id
            JOIN curso_docente cd ON cd.curso_id = c.id
            WHERE cd.docente_id = :docenteId
              AND f.dia = :dia
              AND f.hora_inicio < :horaFin
              AND f.hora_fin > :horaInicio
            """, nativeQuery = true)
    long countOcupacionDocente(@Param("dia") String dia,
            @Param("horaInicio") LocalTime horaInicio,
            @Param("horaFin") LocalTime horaFin,
            @Param("docenteId") int docenteId);

    /**
     * TALLER 6 - PUNTO 6: JOIN MÚLTIPLE (CONSULTA)
     * Detalle de franjas de un curso con curso y espacio
     * Retorna: f.id, f.dia, f.horaInicio, f.horaFin, c.id, c.nombre, e.id,
     * e.nombre, e.capacidad
     */
    @Query("""
            SELECT f.id, f.dia, f.horaInicio, f.horaFin,
                   c.id, c.nombre,
                   e.id, e.nombre, e.capacidad
            FROM FranjaHorariaEntity f
            JOIN f.curso c
            JOIN f.espacioFisico e
            WHERE c.id = :cursoId
            ORDER BY f.dia, f.horaInicio
            """)
    List<Object[]> obtenerDetalleFranjasCurso(@Param("cursoId") int cursoId);

    /**
     * TALLER 6 - PUNTO 8: QUERY DELETE
     * Eliminar todas las franjas de un curso
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM FranjaHorariaEntity f WHERE f.curso.id = :cursoId")
    int eliminarFranjasPorCursoId(@Param("cursoId") int cursoId);
}