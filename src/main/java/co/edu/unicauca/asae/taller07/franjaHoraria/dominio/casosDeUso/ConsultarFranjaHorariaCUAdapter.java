package co.edu.unicauca.asae.taller07.franjaHoraria.dominio.casosDeUso;

import java.util.List;

import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.input.ConsultarFranjaHorariaCUIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.FranjaHorariaFormateadorResultadosIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.FranjaHorariaGatewayIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.ValidacionesFranjaHorariaGatewayIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.FranjaHoraria;

/**
 * Caso de uso: Consultar franjas horarias
 */
public class ConsultarFranjaHorariaCUAdapter implements ConsultarFranjaHorariaCUIntPort {

    private final FranjaHorariaGatewayIntPort gateway;
    private final ValidacionesFranjaHorariaGatewayIntPort validacionesGateway;
    private final FranjaHorariaFormateadorResultadosIntPort formateador;

    public ConsultarFranjaHorariaCUAdapter(
            FranjaHorariaGatewayIntPort gateway,
            ValidacionesFranjaHorariaGatewayIntPort validacionesGateway,
            FranjaHorariaFormateadorResultadosIntPort formateador) {
        this.gateway = gateway;
        this.validacionesGateway = validacionesGateway;
        this.formateador = formateador;
    }

    @Override
    public List<FranjaHoraria> listarPorDocente(int docenteId) {
        if (!validacionesGateway.docenteExiste(docenteId)) {
            formateador.retornarRespuestaErrorEntidadNoExiste(
                    "No existe el docente con id: " + docenteId);
        }
        return gateway.listarPorDocente(docenteId);
    }

    @Override
    public List<FranjaHoraria> listarPorCurso(int cursoId) {
        if (!validacionesGateway.cursoExiste(cursoId)) {
            formateador.retornarRespuestaErrorEntidadNoExiste(
                    "No existe el curso con id: " + cursoId);
        }
        return gateway.listarPorCurso(cursoId);
    }

    @Override
    public List<Object[]> obtenerDetalleFranjasCurso(int cursoId) {
        if (!validacionesGateway.cursoExiste(cursoId)) {
            formateador.retornarRespuestaErrorEntidadNoExiste(
                    "No existe el curso con id: " + cursoId);
        }
        return gateway.obtenerDetalleFranjasCurso(cursoId);
    }
}