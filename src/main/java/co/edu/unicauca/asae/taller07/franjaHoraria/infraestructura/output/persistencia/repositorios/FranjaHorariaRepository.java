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
 * Repositorio para FranjaHoraria
 * NO duplica repositorios de otros dominios, usa solo su propia entity
 */
public interface FranjaHorariaRepository extends JpaRepository<FranjaHorariaEntity, Integer> {

  /**
   * KEYWORD - Franjas de un curso con EAGER fetch de relaciones
   */
  @EntityGraph(attributePaths = { "curso", "curso.docentes", "espacioFisico" })
  List<FranjaHorariaEntity> findByCurso_Id(int cursoId);

  /**
   * JPQL - Franjas de un docente con EAGER fetch
   */
  @Query("""
      SELECT DISTINCT f
      FROM FranjaHorariaEntity f
      JOIN f.curso c2
      JOIN c2.docentes d
      LEFT JOIN FETCH f.curso c
      LEFT JOIN FETCH c.docentes
      LEFT JOIN FETCH f.espacioFisico
      WHERE d.id = :docenteId
      """)
  List<FranjaHorariaEntity> findByDocenteIdFetchCurso(@Param("docenteId") int docenteId);

  /**
   * JPQL - Verifica si espacio físico está ocupado en un horario
   * Retorna count de franjas que se solapan
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
  long countOcupacionEspacio(
      @Param("dia") DiaSemana dia,
      @Param("horaInicio") LocalTime horaInicio,
      @Param("horaFin") LocalTime horaFin,
      @Param("espacioId") int espacioId);

  /**
   * SQL NATIVA - Verifica si docente está ocupado en un horario
   * Retorna count de franjas que se solapan
   * 
   * Condición de solapamiento:
   * - f.hora_inicio < :horaFin (la franja existente empieza antes de que termine
   * la nueva)
   * - f.hora_fin > :horaInicio (la franja existente termina después de que
   * empiece la nueva)
   */
  @Query(value = """
      SELECT COUNT(f.id)
      FROM franja_horaria f
      INNER JOIN curso c ON f.curso_id = c.id
      INNER JOIN curso_docente cd ON cd.curso_id = c.id
      WHERE cd.docente_id = :docenteId
        AND f.dia = :dia
        AND f.hora_inicio < :horaFin
        AND f.hora_fin > :horaInicio
      """, nativeQuery = true)
  long countOcupacionDocente(
      @Param("dia") String dia,
      @Param("horaInicio") LocalTime horaInicio,
      @Param("horaFin") LocalTime horaFin,
      @Param("docenteId") int docenteId);

  /**
   * JPQL JOIN MÚLTIPLE - Detalle de franjas de un curso
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
   * QUERY DELETE - Eliminar todas las franjas de un curso
   */
  @Modifying
  @Transactional
  @Query("DELETE FROM FranjaHorariaEntity f WHERE f.curso.id = :cursoId")
  int eliminarFranjasPorCursoId(@Param("cursoId") int cursoId);
}