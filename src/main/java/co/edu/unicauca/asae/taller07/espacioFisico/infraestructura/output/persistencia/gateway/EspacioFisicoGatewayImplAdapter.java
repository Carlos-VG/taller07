package co.edu.unicauca.asae.taller07.espacioFisico.infraestructura.output.persistencia.gateway;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import co.edu.unicauca.asae.taller07.espacioFisico.aplicacion.output.EspacioFisicoGatewayIntPort;
import co.edu.unicauca.asae.taller07.espacioFisico.dominio.modelos.EspacioFisico;
import co.edu.unicauca.asae.taller07.espacioFisico.infraestructura.output.persistencia.entidades.EspacioFisicoEntity;
import co.edu.unicauca.asae.taller07.espacioFisico.infraestructura.output.persistencia.repositorios.EspacioFisicoRepository;

/**
 * Implementación del Gateway para Espacio Físico
 * Adaptador entre dominio e infraestructura de persistencia
 */
@Service
public class EspacioFisicoGatewayImplAdapter implements EspacioFisicoGatewayIntPort {

    private final EspacioFisicoRepository repository;
    private final ModelMapper mapper;

    public EspacioFisicoGatewayImplAdapter(
            EspacioFisicoRepository repository,
            @Qualifier("espacioFisicoModelMapper") ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<EspacioFisico> listarPorPatronYCapacidad(String patron, Integer capacidadMinima) {
        List<EspacioFisicoEntity> entidades = repository
                .findByNombreStartingWithIgnoreCaseAndCapacidadGreaterThanEqualOrderByNombreAsc(
                        patron, capacidadMinima);

        return mapper.map(entidades, new TypeToken<List<EspacioFisico>>() {
        }.getType());
    }

    @Override
    public int actualizarEstado(int id, boolean activo) {
        return repository.actualizarEstado(id, activo);
    }

    @Override
    public boolean existePorId(int id) {
        return repository.existsById(id);
    }
}