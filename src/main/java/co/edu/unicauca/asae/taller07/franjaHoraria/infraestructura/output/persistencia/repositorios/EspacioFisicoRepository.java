package co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.entidades.EspacioFisicoEntity;

/**
 * Repositorio para EspacioFisico con queries del Taller 6
 */
public interface EspacioFisicoRepository extends JpaRepository<EspacioFisicoEntity, Integer> {

    /**
     * TALLER 6 - PUNTO 1: KEYWORD
     * Listar espacios físicos por patrón de nombre y capacidad mínima
     */
    List<EspacioFisicoEntity> findByNombreStartingWithIgnoreCaseAndCapacidadGreaterThanEqualOrderByNombreAsc(
            String patron, Integer capacidadMinima);

    /**
     * TALLER 6 - PUNTO 7: QUERY UPDATE
     * Actualizar solo el estado de un espacio físico
     */
    @Modifying
    @Transactional
    @Query("UPDATE EspacioFisicoEntity e SET e.activo = :activo WHERE e.id = :id")
    int actualizarEstado(@Param("id") int id, @Param("activo") boolean activo);
}