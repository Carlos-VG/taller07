package co.edu.unicauca.asae.taller07.franjaHoraria.dominio.validadores;

import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.FranjaHorariaFormateadorResultadosIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.ValidacionesFranjaHorariaGatewayIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.FranjaHoraria;

/**
 * Validador 3: Verifica que el espacio físico NO esté ocupado (JPQL)
 */
public class ValidadorEspacioFisicoDisponible extends ValidadorFranjaHorariaBase {

    private final ValidacionesFranjaHorariaGatewayIntPort validacionesGateway;
    private final FranjaHorariaFormateadorResultadosIntPort formateador;

    public ValidadorEspacioFisicoDisponible(
            ValidacionesFranjaHorariaGatewayIntPort validacionesGateway,
            FranjaHorariaFormateadorResultadosIntPort formateador) {
        this.validacionesGateway = validacionesGateway;
        this.formateador = formateador;
    }

    @Override
    protected void ejecutarValidacion(FranjaHoraria franjaHoraria) {
        if (validacionesGateway.espacioFisicoOcupado(
                franjaHoraria.getDia(),
                franjaHoraria.getHoraInicio(),
                franjaHoraria.getHoraFin(),
                franjaHoraria.getEspacioFisico().getId())) {

            formateador.retornarRespuestaErrorReglaDeNegocio(
                    String.format("El espacio físico '%s' ya está ocupado el %s de %s a %s",
                            franjaHoraria.getEspacioFisico().getNombre(),
                            franjaHoraria.getDia(),
                            franjaHoraria.getHoraInicio(),
                            franjaHoraria.getHoraFin()));
        }
    }
}