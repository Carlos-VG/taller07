package co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.gateway;

import java.time.LocalTime;

import org.springframework.stereotype.Service;

import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.ValidacionesFranjaHorariaGatewayIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.DiaSemana;
import co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.repositorios.*;

/**
 * Gateway para validaciones de reglas de negocio (Cadena de Responsabilidad)
 * Conecta con los repositorios que tienen las queries de validación
 */
@Service
public class ValidacionesFranjaHorariaGatewayImplAdapter implements ValidacionesFranjaHorariaGatewayIntPort {

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
        // Convertir DiaSemana de dominio a entity
        co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.entidades.DiaSemana diaEntity = co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.entidades.DiaSemana
                .valueOf(dia.toString());

        // Usar query JPQL del taller 6
        long count = franjaRepository.countOcupacionEspacio(diaEntity, horaInicio, horaFin, espacioId);
        return count > 0;
    }

    @Override
    public boolean docenteOcupado(String dia, LocalTime horaInicio, LocalTime horaFin, int docenteId) {
        // Usar query SQL Nativa del taller 6
        long count = franjaRepository.countOcupacionDocente(dia, horaInicio, horaFin, docenteId);
        return count > 0;
    }

    @Override
    public boolean cursoExiste(int cursoId) {
        return cursoRepository.existsById(cursoId);
    }

    @Override
    public boolean espacioFisicoExiste(int espacioId) {
        return espacioRepository.existsById(espacioId);
    }

    @Override
    public boolean docenteExiste(int docenteId) {
        return docenteRepository.existsById(docenteId);
    }
}