package co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.gateway;

import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.ValidacionesFranjaHorariaGatewayIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.DiaSemana;
import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.Docente;
import co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.repositorios.FranjaHorariaRepository;

// IMPORTS DE REPOSITORIOS ORIGINALES DE OTROS DOMINIOS
import co.edu.unicauca.asae.taller07.curso.infraestructura.output.persistencia.repositorios.CursoRepository;
import co.edu.unicauca.asae.taller07.espacioFisico.infraestructura.output.persistencia.repositorios.EspacioFisicoRepository;
import co.edu.unicauca.asae.taller07.docente.infraestructura.output.persistencia.repositorios.DocenteRepository;
import co.edu.unicauca.asae.taller07.curso.infraestructura.output.persistencia.entidades.CursoEntity;
import co.edu.unicauca.asae.taller07.docente.infraestructura.output.persistencia.entidades.DocenteEntity;

/**
 * Gateway para validaciones de reglas de negocio
 * Implementa Cadena de Responsabilidad
 * Usa repositorios ORIGINALES de otros dominios (NO duplicados)
 */
@Service
public class ValidacionesFranjaHorariaGatewayImplAdapter implements ValidacionesFranjaHorariaGatewayIntPort {

    private static final Logger log = LoggerFactory.getLogger(ValidacionesFranjaHorariaGatewayImplAdapter.class);

    private final FranjaHorariaRepository franjaRepository;
    private final CursoRepository cursoRepository;
    private final EspacioFisicoRepository espacioRepository;
    private final DocenteRepository docenteRepository;

    public ValidacionesFranjaHorariaGatewayImplAdapter(
            FranjaHorariaRepository franjaRepository,
            CursoRepository cursoRepository,
            EspacioFisicoRepository espacioRepository,
            DocenteRepository docenteRepository) {
        this.franjaRepository = franjaRepository;
        this.cursoRepository = cursoRepository;
        this.espacioRepository = espacioRepository;
        this.docenteRepository = docenteRepository;
    }

    @Override
    public boolean espacioFisicoOcupado(DiaSemana dia, LocalTime horaInicio, LocalTime horaFin, int espacioId) {
        // Convertir DiaSemana del dominio a DiaSemana de la entity
        co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.entidades.DiaSemana diaEntity = co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.entidades.DiaSemana
                .valueOf(dia.toString());

        log.debug("Verificando ocupación espacio ID: {} - Día: {} - Horario: {} a {}",
                espacioId, dia, horaInicio, horaFin);

        // Usar query JPQL del repositorio
        long count = franjaRepository.countOcupacionEspacio(diaEntity, horaInicio, horaFin, espacioId);

        log.debug("Franjas que se solapan en espacio {}: {}", espacioId, count);

        return count > 0;
    }

    @Override
    public boolean docenteOcupado(String dia, LocalTime horaInicio, LocalTime horaFin, int docenteId) {
        log.debug("Verificando ocupación docente ID: {} - Día: {} - Horario: {} a {}",
                docenteId, dia, horaInicio, horaFin);

        // Usar query SQL Nativa del repositorio
        long count = franjaRepository.countOcupacionDocente(dia, horaInicio, horaFin, docenteId);

        log.debug("Franjas que se solapan para docente {}: {}", docenteId, count);

        return count > 0;
    }

    @Override
    public boolean cursoExiste(int cursoId) {
        boolean existe = cursoRepository.existsById(cursoId);
        log.debug("Curso ID: {} - Existe: {}", cursoId, existe);
        return existe;
    }

    @Override
    public boolean espacioFisicoExiste(int espacioId) {
        boolean existe = espacioRepository.existsById(espacioId);
        log.debug("Espacio físico ID: {} - Existe: {}", espacioId, existe);
        return existe;
    }

    @Override
    public boolean docenteExiste(int docenteId) {
        boolean existe = docenteRepository.existsById(docenteId);
        log.debug("Docente ID: {} - Existe: {}", docenteId, existe);
        return existe;
    }

    @Override
    public Set<Docente> obtenerDocentesDeCurso(int cursoId) {
        log.debug("Obteniendo docentes del curso ID: {}", cursoId);

        CursoEntity cursoEntity = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con id: " + cursoId));

        if (cursoEntity.getDocentes() == null || cursoEntity.getDocentes().isEmpty()) {
            log.warn("El curso ID: {} no tiene docentes asignados", cursoId);
            return Set.of();
        }

        Set<Docente> docentes = cursoEntity.getDocentes().stream()
                .map(this::mappearDocenteEntityADominio)
                .collect(Collectors.toSet());

        log.debug("Curso ID: {} tiene {} docente(s)", cursoId, docentes.size());

        return docentes;
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
}