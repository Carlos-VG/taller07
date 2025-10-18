package co.edu.unicauca.asae.taller07.espacioFisico.infraestructura.output.persistencia.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unicauca.asae.taller07.espacioFisico.infraestructura.output.persistencia.entidades.EspacioFisicoEntity;

/**
 * Repositorio JPA para EspacioFisico
 * Reutiliza las queries del Taller 6
 */
public interface EspacioFisicoRepository extends JpaRepository<EspacioFisicoEntity, Integer> {

    /**
     * KEYWORD (TALLER 6 - PUNTO 1)
     * Listar espacios físicos por patrón de nombre y capacidad mínima
     * Ordenados ascendentemente por nombre
     */
    List<EspacioFisicoEntity> findByNombreStartingWithIgnoreCaseAndCapacidadGreaterThanEqualOrderByNombreAsc(
            String patron, Integer capacidadMinima);

    /**
     * QUERY UPDATE (TALLER 6 - PUNTO 7)
     * Actualizar solo el estado (activo/inactivo) de un espacio físico
     */
    @Modifying
    @Transactional
    @Query("UPDATE EspacioFisicoEntity e SET e.activo = :activo WHERE e.id = :id")
    int actualizarEstado(@Param("id") int id, @Param("activo") boolean activo);
}