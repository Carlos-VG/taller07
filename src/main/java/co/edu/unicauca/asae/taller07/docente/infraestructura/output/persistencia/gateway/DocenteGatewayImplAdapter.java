package co.edu.unicauca.asae.taller07.docente.infraestructura.output.persistencia.gateway;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import co.edu.unicauca.asae.taller07.docente.aplicacion.output.DocenteGatewayIntPort;
import co.edu.unicauca.asae.taller07.docente.dominio.modelos.Docente;
import co.edu.unicauca.asae.taller07.docente.infraestructura.output.persistencia.entidades.DocenteEntity;
import co.edu.unicauca.asae.taller07.docente.infraestructura.output.persistencia.repositorios.DocenteRepository;

/**
 * Implementación del Gateway para Docente
 * Maneja la persistencia con cascade PERSIST
 */
@Service
public class DocenteGatewayImplAdapter implements DocenteGatewayIntPort {

    private static final Logger log = LoggerFactory.getLogger(DocenteGatewayImplAdapter.class);

    private final DocenteRepository repository;
    private final ModelMapper mapper;

    public DocenteGatewayImplAdapter(
            DocenteRepository repository,
            @Qualifier("docenteModelMapper") ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Docente guardar(Docente docente) {
        log.debug("Mapeando modelo de dominio → entity");

        // Mapear de dominio a entity
        DocenteEntity entity = mapper.map(docente, DocenteEntity.class);

        if (log.isTraceEnabled()) {
            log.trace("Entity antes de guardar: {}", entity);
            if (entity.getOficina() != null) {
                log.trace("Oficina asociada: {}", entity.getOficina());
            }
        }

        log.debug("Ejecutando save() en repositorio (cascade PERSIST)");

        // Guardar (cascade PERSIST maneja oficina automáticamente)
        DocenteEntity entityGuardado = repository.save(entity);

        log.info("✓ Docente guardado con ID: {}", entityGuardado.getId());
        if (entityGuardado.getOficina() != null) {
            log.debug("✓ Oficina guardada con ID: {}", entityGuardado.getOficina().getId());
        }

        log.debug("Mapeando entity → modelo de dominio");

        // Mapear de entity a dominio
        Docente docenteGuardado = mapper.map(entityGuardado, Docente.class);

        if (log.isTraceEnabled()) {
            log.trace("Modelo de dominio resultante: {}", docenteGuardado);
        }

        return docenteGuardado;
    }

    @Override
    public boolean existePorId(int id) {
        log.debug("Verificando existencia de docente con ID: {}", id);
        boolean existe = repository.existsById(id);
        log.debug("Docente ID {} - Existe: {}", id, existe);
        return existe;
    }
}