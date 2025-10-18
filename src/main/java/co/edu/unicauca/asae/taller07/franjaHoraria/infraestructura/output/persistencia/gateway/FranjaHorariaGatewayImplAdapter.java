package co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.gateway;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.FranjaHorariaGatewayIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.*;
import co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.entidades.*;
import co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.repositorios.FranjaHorariaRepository;

// IMPORTS DE REPOSITORIOS Y ENTITIES DE OTROS DOMINIOS (path completo)
import co.edu.unicauca.asae.taller07.curso.infraestructura.output.persistencia.repositorios.CursoRepository;
import co.edu.unicauca.asae.taller07.espacioFisico.infraestructura.output.persistencia.repositorios.EspacioFisicoRepository;
import co.edu.unicauca.asae.taller07.curso.infraestructura.output.persistencia.entidades.CursoEntity;
import co.edu.unicauca.asae.taller07.espacioFisico.infraestructura.output.persistencia.entidades.EspacioFisicoEntity;
import co.edu.unicauca.asae.taller07.docente.infraestructura.output.persistencia.entidades.DocenteEntity;

/**
 * Gateway para operaciones CRUD de Franja Horaria
 * Usa repositorios de otros dominios SIN duplicarlos
 */
@Service
public class FranjaHorariaGatewayImplAdapter implements FranjaHorariaGatewayIntPort {

    private final FranjaHorariaRepository franjaRepository;

    // INYECCIÓN DE REPOSITORIOS ORIGINALES (sin conflicto de beans)
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
        // Crear entity de franja horaria
        FranjaHorariaEntity entity = new FranjaHorariaEntity();

        // Especificar el enum de entity explícitamente
        entity.setDia(
                co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.entidades.DiaSemana
                        .valueOf(franjaHoraria.getDia().toString()));

        entity.setHoraInicio(franjaHoraria.getHoraInicio());
        entity.setHoraFin(franjaHoraria.getHoraFin());

        if (franjaHoraria.getCurso() != null && franjaHoraria.getCurso().getId() > 0) {
            entity.setCurso(cursoRepository.getReferenceById(franjaHoraria.getCurso().getId()));
        }

        if (franjaHoraria.getEspacioFisico() != null && franjaHoraria.getEspacioFisico().getId() > 0) {
            entity.setEspacioFisico(espacioRepository.getReferenceById(franjaHoraria.getEspacioFisico().getId()));
        }

        FranjaHorariaEntity entityGuardada = franjaRepository.save(entity);

        return mappearEntityBasicaADominio(entityGuardada);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FranjaHoraria> listarPorDocente(int docenteId) {
        // Usa query optimizada con EAGER fetch
        List<FranjaHorariaEntity> entities = franjaRepository.findByDocenteIdFetchCurso(docenteId);

        return entities.stream()
                .map(this::mappearEntityCompletaADominio)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FranjaHoraria> listarPorCurso(int cursoId) {
        // Usa query optimizada con EntityGraph
        List<FranjaHorariaEntity> entities = franjaRepository.findByCurso_Id(cursoId);

        return entities.stream()
                .map(this::mappearEntityCompletaADominio)
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
    // MÉTODOS AUXILIARES PARA MAPEO
    // ========================================================================

    /**
     * Mapeo básico: solo IDs, sin cargar relaciones completas
     * Usado después de guardar
     */
    private FranjaHoraria mappearEntityBasicaADominio(FranjaHorariaEntity entity) {
        FranjaHoraria franja = new FranjaHoraria();
        franja.setId(entity.getId());
        franja.setDia(co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.DiaSemana
                .valueOf(entity.getDia().toString()));
        franja.setHoraInicio(entity.getHoraInicio());
        franja.setHoraFin(entity.getHoraFin());

        // Solo IDs (no carga datos completos para evitar LazyInitializationException)
        if (entity.getCurso() != null) {
            Curso curso = new Curso();
            curso.setId(entity.getCurso().getId());
            franja.setCurso(curso);
        }

        if (entity.getEspacioFisico() != null) {
            EspacioFisico espacio = new EspacioFisico();
            espacio.setId(entity.getEspacioFisico().getId());
            franja.setEspacioFisico(espacio);
        }

        return franja;
    }

    /**
     * Mapeo completo: con todas las relaciones cargadas
     * Usado en consultas con EAGER fetch
     */
    private FranjaHoraria mappearEntityCompletaADominio(FranjaHorariaEntity entity) {
        FranjaHoraria franja = new FranjaHoraria();
        franja.setId(entity.getId());
        franja.setDia(co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.DiaSemana
                .valueOf(entity.getDia().toString()));
        franja.setHoraInicio(entity.getHoraInicio());
        franja.setHoraFin(entity.getHoraFin());

        // Mapear curso completo con docentes
        if (entity.getCurso() != null) {
            franja.setCurso(mappearCursoEntityADominio(entity.getCurso()));
        }

        // Mapear espacio físico completo
        if (entity.getEspacioFisico() != null) {
            franja.setEspacioFisico(mappearEspacioEntityADominio(entity.getEspacioFisico()));
        }

        return franja;
    }

    /**
     * Convierte CursoEntity (de otro dominio) → Curso (modelo de dominio
     * FranjaHoraria)
     * CONVERSIÓN EN LA FRONTERA
     */
    private Curso mappearCursoEntityADominio(CursoEntity entity) {
        Curso curso = new Curso();
        curso.setId(entity.getId());
        curso.setNombre(entity.getNombre());
        curso.setMatriculaEstimada(entity.getMatriculaEstimada());

        // Mapear docentes del curso
        if (entity.getDocentes() != null && !entity.getDocentes().isEmpty()) {
            curso.setDocentes(
                    entity.getDocentes().stream()
                            .map(this::mappearDocenteEntityADominio)
                            .collect(Collectors.toSet()));
        }

        return curso;
    }

    /**
     * Convierte DocenteEntity (de otro dominio) → Docente (modelo de dominio
     * FranjaHoraria)
     * CONVERSIÓN EN LA FRONTERA
     */
    private Docente mappearDocenteEntityADominio(DocenteEntity entity) {
        Docente docente = new Docente();
        docente.setId(entity.getId());
        docente.setNombre(entity.getNombre());
        docente.setApellido(entity.getApellido());
        docente.setCorreo(entity.getCorreo());
        return docente;
    }

    /**
     * Convierte EspacioFisicoEntity (de otro dominio) → EspacioFisico (modelo de
     * dominio FranjaHoraria)
     * CONVERSIÓN EN LA FRONTERA
     */
    private EspacioFisico mappearEspacioEntityADominio(EspacioFisicoEntity entity) {
        EspacioFisico espacio = new EspacioFisico();
        espacio.setId(entity.getId());
        espacio.setNombre(entity.getNombre());
        espacio.setCapacidad(entity.getCapacidad());
        espacio.setActivo(entity.isActivo());
        return espacio;
    }
}