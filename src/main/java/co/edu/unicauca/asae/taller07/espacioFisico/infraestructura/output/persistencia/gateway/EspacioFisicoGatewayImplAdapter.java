package co.edu.unicauca.asae.taller07.espacioFisico.infraestructura.output.persistencia.gateway;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unicauca.asae.taller07.espacioFisico.aplicacion.output.EspacioFisicoGatewayIntPort;
import co.edu.unicauca.asae.taller07.espacioFisico.dominio.modelos.EspacioFisico;
import co.edu.unicauca.asae.taller07.espacioFisico.infraestructura.output.persistencia.entidades.EspacioFisicoEntity;
import co.edu.unicauca.asae.taller07.espacioFisico.infraestructura.output.persistencia.repositorios.EspacioFisicoRepository;

/**
 * Gateway para Espacio Físico - Adaptador de persistencia
 */
@Service
public class EspacioFisicoGatewayImplAdapter implements EspacioFisicoGatewayIntPort {

    private static final Logger log = LoggerFactory.getLogger(EspacioFisicoGatewayImplAdapter.class);

    private final EspacioFisicoRepository repository;
    private final ModelMapper mapper;

    public EspacioFisicoGatewayImplAdapter(
            EspacioFisicoRepository repository,
            @Qualifier("espacioFisicoModelMapper") ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EspacioFisico> listarPorPatronYCapacidad(String patron, Integer capacidadMinima) {
        log.debug("Buscando espacios: patrón='{}', capacidad>={}", patron, capacidadMinima);

        List<EspacioFisicoEntity> entidades = repository
                .findByNombreStartingWithIgnoreCaseAndCapacidadGreaterThanEqualOrderByNombreAsc(
                        patron, capacidadMinima);

        log.debug("Encontrados {} espacio(s) en BD", entidades.size());

        return mapper.map(entidades, new TypeToken<List<EspacioFisico>>() {
        }.getType());
    }

    @Override
    @Transactional
    public int actualizarEstado(int id, boolean activo) {
        log.debug("Actualizando estado espacio ID: {} → {}", id, activo);

        int filasActualizadas = repository.actualizarEstado(id, activo);

        if (filasActualizadas > 0) {
            log.debug("Estado actualizado correctamente");
        } else {
            log.warn("No se actualizó ninguna fila");
        }

        return filasActualizadas;
    }

    @Override
    public boolean existePorId(int id) {
        boolean existe = repository.existsById(id);
        log.debug("Espacio ID: {} - Existe: {}", id, existe);
        return existe;
    }
}
