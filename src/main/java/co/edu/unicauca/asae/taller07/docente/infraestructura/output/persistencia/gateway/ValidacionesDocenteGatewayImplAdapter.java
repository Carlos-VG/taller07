package co.edu.unicauca.asae.taller07.docente.infraestructura.output.persistencia.gateway;

import org.springframework.stereotype.Service;

import co.edu.unicauca.asae.taller07.docente.aplicacion.output.ValidacionesDocenteGatewayIntPort;
import co.edu.unicauca.asae.taller07.docente.infraestructura.output.persistencia.repositorios.DocenteRepository;

/**
 * Gateway para validaciones de reglas de negocio de Docente
 * Patrón Cadena de Responsabilidad
 */
@Service
public class ValidacionesDocenteGatewayImplAdapter implements ValidacionesDocenteGatewayIntPort {

    private final DocenteRepository repository;

    public ValidacionesDocenteGatewayImplAdapter(DocenteRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean existeDocentePorCorreo(String correo) {
        return repository.existsByCorreo(correo);
    }
}