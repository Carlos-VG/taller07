package co.edu.unicauca.asae.taller07.docente.infraestructura.output.persistencia.gateway;

import org.modelmapper.ModelMapper;
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
        // Mapear de dominio a entity
        DocenteEntity entity = mapper.map(docente, DocenteEntity.class);

        // Guardar (cascade PERSIST maneja oficina automáticamente)
        DocenteEntity entityGuardado = repository.save(entity);

        // Mapear de entity a dominio
        return mapper.map(entityGuardado, Docente.class);
    }

    @Override
    public boolean existePorId(int id) {
        return repository.existsById(id);
    }
}
