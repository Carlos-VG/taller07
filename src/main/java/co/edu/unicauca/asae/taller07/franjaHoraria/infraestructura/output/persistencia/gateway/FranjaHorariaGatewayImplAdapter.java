package co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.gateway;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.FranjaHorariaGatewayIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.*;
import co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.entidades.*;
import co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.repositorios.*;

/**
 * Gateway para operaciones CRUD de Franja Horaria
 */
@Service
public class FranjaHorariaGatewayImplAdapter implements FranjaHorariaGatewayIntPort {

    private final FranjaHorariaRepository franjaRepository;
    private final CursoRepository cursoRepository;
    private final EspacioFisicoRepository espacioRepository;
    private final ModelMapper mapper;

    public FranjaHorariaGatewayImplAdapter(
            FranjaHorariaRepository franjaRepository,
            CursoRepository cursoRepository,
            EspacioFisicoRepository espacioRepository,
            @Qualifier("franjaHorariaModelMapper") ModelMapper mapper) {
        this.franjaRepository = franjaRepository;
        this.cursoRepository = cursoRepository;
        this.espacioRepository = espacioRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public FranjaHoraria guardar(FranjaHoraria franjaHoraria) {
        // Mapear dominio a entity
        FranjaHorariaEntity entity = mapper.map(franjaHoraria, FranjaHorariaEntity.class);

        // Cargar relaciones completas usando getReferenceById (no hace SELECT
        // innecesario)
        if (franjaHoraria.getCurso() != null && franjaHoraria.getCurso().getId() > 0) {
            entity.setCurso(cursoRepository.getReferenceById(franjaHoraria.getCurso().getId()));
        }

        if (franjaHoraria.getEspacioFisico() != null && franjaHoraria.getEspacioFisico().getId() > 0) {
            entity.setEspacioFisico(espacioRepository.getReferenceById(franjaHoraria.getEspacioFisico().getId()));
        }

        // Guardar
        FranjaHorariaEntity entityGuardada = franjaRepository.save(entity);

        // Mapear entity a dominio
        return mapper.map(entityGuardada, FranjaHoraria.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FranjaHoraria> listarPorDocente(int docenteId) {
        // Usa query optimizada con EAGER puntual
        List<FranjaHorariaEntity> entities = franjaRepository.findByDocenteIdFetchCurso(docenteId);

        return entities.stream()
                .map(entity -> mappearEntityCompleta(entity))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> obtenerDetalleFranjasCurso(int cursoId) {
        // Retorna directamente el resultado del JOIN múltiple
        return franjaRepository.obtenerDetalleFranjasCurso(cursoId);
    }

    @Override
    @Transactional
    public int eliminarFranjasPorCurso(int cursoId) {
        return franjaRepository.eliminarFranjasPorCursoId(cursoId);
    }

    // ========================================================================
    // MÉTODOS AUXILIARES PARA MAPEO COMPLETO
    // ========================================================================

    /**
     * Mapea una FranjaHorariaEntity completa con todas sus relaciones
     */
    private FranjaHoraria mappearEntityCompleta(FranjaHorariaEntity entity) {
        FranjaHoraria franja = new FranjaHoraria();
        franja.setId(entity.getId());
        franja.setDia(co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.DiaSemana.valueOf(
                entity.getDia().toString()));
        franja.setHoraInicio(entity.getHoraInicio());
        franja.setHoraFin(entity.getHoraFin());

        // Mapear curso con docentes
        if (entity.getCurso() != null) {
            Curso curso = new Curso();
            curso.setId(entity.getCurso().getId());
            curso.setNombre(entity.getCurso().getNombre());
            curso.setMatriculaEstimada(entity.getCurso().getMatriculaEstimada());

            // Mapear docentes del curso
            if (entity.getCurso().getDocentes() != null) {
                curso.setDocentes(
                        entity.getCurso().getDocentes().stream()
                                .map(this::mappearDocente)
                                .collect(Collectors.toSet()));
            }

            franja.setCurso(curso);
        }

        // Mapear espacio físico
        if (entity.getEspacioFisico() != null) {
            EspacioFisico espacio = new EspacioFisico();
            espacio.setId(entity.getEspacioFisico().getId());
            espacio.setNombre(entity.getEspacioFisico().getNombre());
            espacio.setCapacidad(entity.getEspacioFisico().getCapacidad());
            espacio.setActivo(entity.getEspacioFisico().isActivo());
            franja.setEspacioFisico(espacio);
        }

        return franja;
    }

    private Docente mappearDocente(DocenteEntity entity) {
        Docente docente = new Docente();
        docente.setId(entity.getId());
        docente.setNombre(entity.getNombre());
        docente.setApellido(entity.getApellido());
        docente.setCorreo(entity.getCorreo());
        return docente;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FranjaHoraria> listarPorCurso(int cursoId) {
        // Usa query optimizada con EntityGraph
        List<FranjaHorariaEntity> entities = franjaRepository.findByCurso_Id(cursoId);

        return entities.stream()
                .map(entity -> mappearEntityCompleta(entity))
                .collect(Collectors.toList());
    }
}