package co.edu.unicauca.asae.taller07.docente.infraestructura.output.persistencia.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unicauca.asae.taller07.docente.infraestructura.output.persistencia.entidades.OficinaEntity;

/**
 * Repositorio JPA para Oficina
 */
public interface OficinaRepository extends JpaRepository<OficinaEntity, Integer> {
}