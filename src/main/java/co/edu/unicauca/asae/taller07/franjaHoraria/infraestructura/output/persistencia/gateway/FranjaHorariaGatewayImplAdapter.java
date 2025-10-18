package co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.gateway;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.FranjaHorariaGatewayIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.*;
import co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.entidades.*;
import co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.repositorios.FranjaHorariaRepository;

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

    private static final Logger log = LoggerFactory.getLogger(FranjaHorariaGatewayImplAdapter.class);

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
        log.debug("═══════════════════════════════════════════════════");
        log.debug("GUARDANDO FRANJA HORARIA EN BD");
        log.debug("═══════════════════════════════════════════════════");

        // Crear entity de franja horaria
        FranjaHorariaEntity entity = new FranjaHorariaEntity();

        // Mapear día (enum del dominio → enum de entity)
        log.debug("Mapeando día: {} → entity enum", franjaHoraria.getDia());
        entity.setDia(
                co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.entidades.DiaSemana
                        .valueOf(franjaHoraria.getDia().toString()));

        entity.setHoraInicio(franjaHoraria.getHoraInicio());
        entity.setHoraFin(franjaHoraria.getHoraFin());

        log.debug("Horario configurado: {} {} - {}",
                entity.getDia(), entity.getHoraInicio(), entity.getHoraFin());

        // Asociar curso
        if (franjaHoraria.getCurso() != null && franjaHoraria.getCurso().getId() > 0) {
            int cursoId = franjaHoraria.getCurso().getId();
            log.debug("Asociando curso ID: {} (usando getReferenceById)", cursoId);
            entity.setCurso(cursoRepository.getReferenceById(cursoId));
        }

        // Asociar espacio físico
        if (franjaHoraria.getEspacioFisico() != null && franjaHoraria.getEspacioFisico().getId() > 0) {
            int espacioId = franjaHoraria.getEspacioFisico().getId();
            log.debug("Asociando espacio físico ID: {} (usando getReferenceById)", espacioId);
            entity.setEspacioFisico(espacioRepository.getReferenceById(espacioId));
        }

        if (log.isTraceEnabled()) {
            log.trace("Entity antes de save(): {}", entity);
        }

        log.debug("Ejecutando repository.save()...");
        FranjaHorariaEntity entityGuardada = franjaRepository.save(entity);

        log.info("Franja horaria guardada con ID: {}", entityGuardada.getId());

        log.debug("Mapeando entity → modelo de dominio");
        FranjaHoraria resultado = mappearEntityBasicaADominio(entityGuardada);

        log.debug("═══════════════════════════════════════════════════");

        return resultado;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FranjaHoraria> listarPorDocente(int docenteId) {
        log.debug("Consultando franjas del docente ID: {} (EAGER: curso+espacio)", docenteId);

        List<FranjaHorariaEntity> entities = franjaRepository.findByDocenteIdFetchCurso(docenteId);

        log.debug("Entities encontradas: {}", entities.size());

        if (log.isTraceEnabled()) {
            entities.forEach(e -> log.trace("  - Entity ID: {} | {} | {} - {}",
                    e.getId(), e.getDia(), e.getHoraInicio(), e.getHoraFin()));
        }

        log.debug("Mapeando {} entities → modelos de dominio", entities.size());

        List<FranjaHoraria> franjas = entities.stream()
                .map(this::mappearEntityCompletaADominio)
                .collect(Collectors.toList());

        log.debug("✓ Mapeo completado");

        return franjas;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FranjaHoraria> listarPorCurso(int cursoId) {
        log.debug("Consultando franjas del curso ID: {} (EntityGraph: curso+docentes+espacio)", cursoId);

        List<FranjaHorariaEntity> entities = franjaRepository.findByCurso_Id(cursoId);

        log.debug("Entities encontradas: {}", entities.size());

        if (log.isTraceEnabled()) {
            entities.forEach(e -> log.trace("  - Entity ID: {} | {} | {} - {}",
                    e.getId(), e.getDia(), e.getHoraInicio(), e.getHoraFin()));
        }

        log.debug("Mapeando {} entities → modelos de dominio", entities.size());

        List<FranjaHoraria> franjas = entities.stream()
                .map(this::mappearEntityCompletaADominio)
                .collect(Collectors.toList());

        log.debug("✓ Mapeo completado");

        return franjas;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> obtenerDetalleFranjasCurso(int cursoId) {
        log.debug("Obteniendo detalle de franjas del curso ID: {} (JOIN múltiple)", cursoId);

        List<Object[]> detalle = franjaRepository.obtenerDetalleFranjasCurso(cursoId);

        log.debug("Filas obtenidas: {}", detalle.size());

        if (log.isTraceEnabled()) {
            detalle.forEach(fila -> log.trace("  Fila: {}", java.util.Arrays.toString(fila)));
        }

        return detalle;
    }

    @Override
    @Transactional
    public int eliminarFranjasPorCurso(int cursoId) {
        log.debug("Eliminando franjas del curso ID: {}", cursoId);

        int eliminadas = franjaRepository.eliminarFranjasPorCursoId(cursoId);

        log.info("Eliminadas {} franja(s) del curso ID: {}", eliminadas, cursoId);

        return eliminadas;
    }

    // ========================================================================
    // MÉTODOS AUXILIARES PARA MAPEO
    // ========================================================================

    /**
     * Mapeo básico: solo IDs, sin cargar relaciones completas
     */
    private FranjaHoraria mappearEntityBasicaADominio(FranjaHorariaEntity entity) {
        log.trace("Mapeo básico entity → dominio (solo IDs)");

        FranjaHoraria franja = new FranjaHoraria();
        franja.setId(entity.getId());
        franja.setDia(co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.DiaSemana
                .valueOf(entity.getDia().toString()));
        franja.setHoraInicio(entity.getHoraInicio());
        franja.setHoraFin(entity.getHoraFin());

        // Solo IDs
        if (entity.getCurso() != null) {
            Curso curso = new Curso();
            curso.setId(entity.getCurso().getId());
            franja.setCurso(curso);
            log.trace("  - Curso ID: {}", curso.getId());
        }

        if (entity.getEspacioFisico() != null) {
            EspacioFisico espacio = new EspacioFisico();
            espacio.setId(entity.getEspacioFisico().getId());
            franja.setEspacioFisico(espacio);
            log.trace("  - Espacio ID: {}", espacio.getId());
        }

        return franja;
    }

    /**
     * Mapeo completo: con todas las relaciones cargadas
     */
    private FranjaHoraria mappearEntityCompletaADominio(FranjaHorariaEntity entity) {
        log.trace("Mapeo completo entity → dominio (con relaciones)");

        FranjaHoraria franja = new FranjaHoraria();
        franja.setId(entity.getId());
        franja.setDia(co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.DiaSemana
                .valueOf(entity.getDia().toString()));
        franja.setHoraInicio(entity.getHoraInicio());
        franja.setHoraFin(entity.getHoraFin());

        // Mapear curso completo con docentes
        if (entity.getCurso() != null) {
            log.trace("  - Mapeando curso ID: {}", entity.getCurso().getId());
            franja.setCurso(mappearCursoEntityADominio(entity.getCurso()));
        }

        // Mapear espacio físico completo
        if (entity.getEspacioFisico() != null) {
            log.trace("  - Mapeando espacio ID: {}", entity.getEspacioFisico().getId());
            franja.setEspacioFisico(mappearEspacioEntityADominio(entity.getEspacioFisico()));
        }

        return franja;
    }

    /**
     * Convierte CursoEntity → Curso (modelo de dominio FranjaHoraria)
     */
    private Curso mappearCursoEntityADominio(CursoEntity entity) {
        Curso curso = new Curso();
        curso.setId(entity.getId());
        curso.setNombre(entity.getNombre());
        curso.setMatriculaEstimada(entity.getMatriculaEstimada());

        // Mapear docentes del curso
        if (entity.getDocentes() != null && !entity.getDocentes().isEmpty()) {
            log.trace("    - Mapeando {} docente(s) del curso", entity.getDocentes().size());
            curso.setDocentes(
                    entity.getDocentes().stream()
                            .map(this::mappearDocenteEntityADominio)
                            .collect(Collectors.toSet()));
        }

        return curso;
    }

    /**
     * Convierte DocenteEntity → Docente (modelo de dominio FranjaHoraria)
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
     * Convierte EspacioFisicoEntity → EspacioFisico (modelo de dominio)
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