package co.edu.unicauca.asae.taller07.docente.infraestructura.output.persistencia.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unicauca.asae.taller07.docente.infraestructura.output.persistencia.entidades.DocenteEntity;

/**
 * Repositorio JPA para Docente
 */
public interface DocenteRepository extends JpaRepository<DocenteEntity, Integer> {

    /**
     * Verifica si existe un docente con el correo dado
     */
    boolean existsByCorreo(String correo);
}