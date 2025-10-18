package co.edu.unicauca.asae.taller07.curso.infraestructura.output.persistencia.gateway;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import co.edu.unicauca.asae.taller07.curso.aplicacion.output.CursoGatewayIntPort;
import co.edu.unicauca.asae.taller07.curso.dominio.modelos.Curso;
import co.edu.unicauca.asae.taller07.curso.infraestructura.output.persistencia.entidades.CursoEntity;
import co.edu.unicauca.asae.taller07.curso.infraestructura.output.persistencia.repositorios.CursoRepository;

@Service
public class CursoGatewayImplAdapter implements CursoGatewayIntPort {

    private static final Logger log = LoggerFactory.getLogger(CursoGatewayImplAdapter.class);

    private final CursoRepository repository;
    private final ModelMapper mapper;

    public CursoGatewayImplAdapter(
            CursoRepository repository,
            @Qualifier("cursoModelMapper") ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<Curso> listarPorNombreAsignatura(String nombreAsignatura) {
        log.debug("Buscando cursos en BD por asignatura: '{}'", nombreAsignatura);

        List<CursoEntity> entities = repository.findByAsignatura_NombreIgnoreCase(nombreAsignatura);

        log.debug("Entities encontradas: {}", entities.size());
        if (log.isTraceEnabled()) {
            entities.forEach(e -> log.trace("  - Entity ID: {}, Nombre: {}", e.getId(), e.getNombre()));
        }

        List<Curso> cursos = mapper.map(entities, new TypeToken<List<Curso>>() {
        }.getType());

        log.debug("Mapeadas {} entities a modelos de dominio", cursos.size());

        return cursos;
    }
}