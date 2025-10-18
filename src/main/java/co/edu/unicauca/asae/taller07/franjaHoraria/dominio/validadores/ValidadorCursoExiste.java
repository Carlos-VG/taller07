package co.edu.unicauca.asae.taller07.franjaHoraria.dominio.validadores;

import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.FranjaHorariaFormateadorResultadosIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.ValidacionesFranjaHorariaGatewayIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.FranjaHoraria;

/**
 * Validador 1: Verifica que el curso exista
 */
public class ValidadorCursoExiste extends ValidadorFranjaHorariaBase {

    private final ValidacionesFranjaHorariaGatewayIntPort validacionesGateway;
    private final FranjaHorariaFormateadorResultadosIntPort formateador;

    public ValidadorCursoExiste(
            ValidacionesFranjaHorariaGatewayIntPort validacionesGateway,
            FranjaHorariaFormateadorResultadosIntPort formateador) {
        this.validacionesGateway = validacionesGateway;
        this.formateador = formateador;
    }

    @Override
    protected void ejecutarValidacion(FranjaHoraria franjaHoraria) {
        if (!validacionesGateway.cursoExiste(franjaHoraria.getCurso().getId())) {
            formateador.retornarRespuestaErrorEntidadNoExiste(
                    "No existe el curso con id: " + franjaHoraria.getCurso().getId());
        }
    }
}