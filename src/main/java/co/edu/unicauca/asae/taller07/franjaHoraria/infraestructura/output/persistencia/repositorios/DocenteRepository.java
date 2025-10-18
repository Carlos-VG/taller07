package co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.entidades.DocenteEntity;

public interface DocenteRepository extends JpaRepository<DocenteEntity, Integer> {
    boolean existsByCorreo(String correo);
}