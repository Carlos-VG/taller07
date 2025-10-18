package co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.entidades.CursoEntity;
import lombok.NonNull;

/**
 * Repositorio para Curso con queries del Taller 6
 */
public interface CursoRepository extends JpaRepository<CursoEntity, Integer> {

    /**
     * TALLER 6 - PUNTO 2: KEYWORD EN TABLAS RELACIONADAS
     * Obtener cursos por nombre de asignatura (ignore case)
     */
    List<CursoEntity> findByAsignatura_NombreIgnoreCase(String nombreAsignatura);

    /**
     * Carga un curso con sus franjas y espacios (EAGER puntual)
     */
    @Query("""
            SELECT DISTINCT c
            FROM CursoEntity c
            LEFT JOIN FETCH c.franjas f
            LEFT JOIN FETCH f.espacioFisico
            WHERE c.id = :cursoId
            """)
    Optional<CursoEntity> findDetalleCursoConFranjasYEspacios(@Param("cursoId") int cursoId);

    /**
     * Carga un curso con sus franjas y espacios (para eliminación en cascada)
     */
    @EntityGraph(attributePaths = { "franjas", "franjas.espacioFisico" })
    @NonNull
    Optional<CursoEntity> findWithFranjasById(@NonNull Integer id);

    /**
     * Cursos con franjas dictados por un docente concreto
     */
    @Query("""
            SELECT DISTINCT c
            FROM CursoEntity c
            JOIN c.docentes d
            LEFT JOIN FETCH c.franjas f
            LEFT JOIN FETCH c.asignatura a
            WHERE d.id = :docenteId
            """)
    List<CursoEntity> findCursosYFranjasByDocente(@Param("docenteId") int docenteId);
}
