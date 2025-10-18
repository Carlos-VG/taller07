package co.edu.unicauca.asae.taller07.docente.infraestructura.output.persistencia.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.edu.unicauca.asae.taller07.docente.aplicacion.output.ValidacionesDocenteGatewayIntPort;
import co.edu.unicauca.asae.taller07.docente.infraestructura.output.persistencia.repositorios.DocenteRepository;

/**
 * Gateway para validaciones de reglas de negocio de Docente
 * Patrón Cadena de Responsabilidad
 */
@Service
public class ValidacionesDocenteGatewayImplAdapter implements ValidacionesDocenteGatewayIntPort {

    private static final Logger log = LoggerFactory.getLogger(ValidacionesDocenteGatewayImplAdapter.class);

    private final DocenteRepository repository;

    public ValidacionesDocenteGatewayImplAdapter(DocenteRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean existeDocentePorCorreo(String correo) {
        log.debug("Verificando existencia de docente con correo: {}", correo);
        boolean existe = repository.existsByCorreo(correo);
        log.debug("Correo '{}' - Ya existe: {}", correo, existe);
        return existe;
    }
}