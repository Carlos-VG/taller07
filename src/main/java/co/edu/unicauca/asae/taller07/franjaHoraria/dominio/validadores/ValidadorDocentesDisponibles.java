package co.edu.unicauca.asae.taller07.franjaHoraria.dominio.validadores;

import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.FranjaHorariaFormateadorResultadosIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.ValidacionesFranjaHorariaGatewayIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.FranjaHoraria;

/**
 * Validador 4: Verifica que los docentes existan y NO estén ocupados (SQL
 * Nativo)
 */
public class ValidadorDocentesDisponibles extends ValidadorFranjaHorariaBase {

    private final ValidacionesFranjaHorariaGatewayIntPort validacionesGateway;
    private final FranjaHorariaFormateadorResultadosIntPort formateador;

    public ValidadorDocentesDisponibles(
            ValidacionesFranjaHorariaGatewayIntPort validacionesGateway,
            FranjaHorariaFormateadorResultadosIntPort formateador) {
        this.validacionesGateway = validacionesGateway;
        this.formateador = formateador;
    }

    @Override
    protected void ejecutarValidacion(FranjaHoraria franjaHoraria) {
        for (var docente : franjaHoraria.getCurso().getDocentes()) {
            // Validar existencia
            if (!validacionesGateway.docenteExiste(docente.getId())) {
                formateador.retornarRespuestaErrorEntidadNoExiste(
                        "No existe el docente con id: " + docente.getId());
            }

            // Validar disponibilidad
            if (validacionesGateway.docenteOcupado(
                    franjaHoraria.getDia().toString(),
                    franjaHoraria.getHoraInicio(),
                    franjaHoraria.getHoraFin(),
                    docente.getId())) {

                formateador.retornarRespuestaErrorReglaDeNegocio(
                        String.format("El docente '%s %s' ya está ocupado el %s de %s a %s",
                                docente.getNombre(),
                                docente.getApellido(),
                                franjaHoraria.getDia(),
                                franjaHoraria.getHoraInicio(),
                                franjaHoraria.getHoraFin()));
            }
        }
    }
}