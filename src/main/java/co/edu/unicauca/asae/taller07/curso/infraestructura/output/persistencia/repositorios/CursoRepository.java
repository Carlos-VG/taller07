package co.edu.unicauca.asae.taller07.curso.infraestructura.output.persistencia.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unicauca.asae.taller07.curso.infraestructura.output.persistencia.entidades.CursoEntity;

/**
 * Repositorio para Curso - KEYWORD del Taller 6 Punto 2
 */
public interface CursoRepository extends JpaRepository<CursoEntity, Integer> {

    /**
     * TALLER 6 - PUNTO 2: KEYWORD EN TABLAS RELACIONADAS
     */
    List<CursoEntity> findByAsignatura_NombreIgnoreCase(String nombreAsignatura);
}
