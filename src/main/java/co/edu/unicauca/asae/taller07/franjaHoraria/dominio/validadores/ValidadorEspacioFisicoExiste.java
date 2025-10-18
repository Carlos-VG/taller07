package co.edu.unicauca.asae.taller07.franjaHoraria.dominio.validadores;

import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.FranjaHorariaFormateadorResultadosIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.ValidacionesFranjaHorariaGatewayIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.FranjaHoraria;

/**
 * Validador 2: Verifica que el espacio físico exista
 */
public class ValidadorEspacioFisicoExiste extends ValidadorFranjaHorariaBase {

    private final ValidacionesFranjaHorariaGatewayIntPort validacionesGateway;
    private final FranjaHorariaFormateadorResultadosIntPort formateador;

    public ValidadorEspacioFisicoExiste(
            ValidacionesFranjaHorariaGatewayIntPort validacionesGateway,
            FranjaHorariaFormateadorResultadosIntPort formateador) {
        this.validacionesGateway = validacionesGateway;
        this.formateador = formateador;
    }

    @Override
    protected void ejecutarValidacion(FranjaHoraria franjaHoraria) {
        if (!validacionesGateway.espacioFisicoExiste(franjaHoraria.getEspacioFisico().getId())) {
            formateador.retornarRespuestaErrorEntidadNoExiste(
                    "No existe el espacio físico con id: " + franjaHoraria.getEspacioFisico().getId());
        }
    }
}