package co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.gateway;

import java.time.LocalTime;

import org.springframework.stereotype.Service;

import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.ValidacionesFranjaHorariaGatewayIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.DiaSemana;
import co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.repositorios.FranjaHorariaRepository;

// IMPORTS DE REPOSITORIOS ORIGINALES DE OTROS DOMINIOS
import co.edu.unicauca.asae.taller07.curso.infraestructura.output.persistencia.repositorios.CursoRepository;
import co.edu.unicauca.asae.taller07.espacioFisico.infraestructura.output.persistencia.repositorios.EspacioFisicoRepository;
import co.edu.unicauca.asae.taller07.docente.infraestructura.output.persistencia.repositorios.DocenteRepository;

/**
 * Gateway para validaciones de reglas de negocio
 * Implementa Cadena de Responsabilidad
 * Usa repositorios ORIGINALES de otros dominios (NO duplicados)
 */
@Service
public class ValidacionesFranjaHorariaGatewayImplAdapter implements ValidacionesFranjaHorariaGatewayIntPort {

    private final FranjaHorariaRepository franjaRepository;

    // INYECCIÓN DE REPOSITORIOS ORIGINALES (sin conflicto de beans)
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

        // Usar query JPQL del repositorio
        long count = franjaRepository.countOcupacionEspacio(diaEntity, horaInicio, horaFin, espacioId);
        return count > 0;
    }

    @Override
    public boolean docenteOcupado(String dia, LocalTime horaInicio, LocalTime horaFin, int docenteId) {
        // Usar query SQL Nativa del repositorio
        long count = franjaRepository.countOcupacionDocente(dia, horaInicio, horaFin, docenteId);
        return count > 0;
    }

    @Override
    public boolean cursoExiste(int cursoId) {
        // Usa el repositorio ORIGINAL del dominio Curso
        return cursoRepository.existsById(cursoId);
    }

    @Override
    public boolean espacioFisicoExiste(int espacioId) {
        // Usa el repositorio ORIGINAL del dominio EspacioFisico
        return espacioRepository.existsById(espacioId);
    }

    @Override
    public boolean docenteExiste(int docenteId) {
        // Usa el repositorio ORIGINAL del dominio Docente
        return docenteRepository.existsById(docenteId);
    }
}