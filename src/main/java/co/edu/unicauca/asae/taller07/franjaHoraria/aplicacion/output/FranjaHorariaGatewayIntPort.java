package co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output;

import java.util.List;
import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.FranjaHoraria;

public interface FranjaHorariaGatewayIntPort {

    FranjaHoraria guardar(FranjaHoraria franjaHoraria);

    List<FranjaHoraria> listarPorDocente(int docenteId);

    List<FranjaHoraria> listarPorCurso(int cursoId);

    List<Object[]> obtenerDetalleFranjasCurso(int cursoId);

    int eliminarFranjasPorCurso(int cursoId);
}