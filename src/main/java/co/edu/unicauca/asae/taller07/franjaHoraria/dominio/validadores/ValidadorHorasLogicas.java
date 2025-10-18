package co.edu.unicauca.asae.taller07.franjaHoraria.dominio.validadores;

import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.FranjaHorariaFormateadorResultadosIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.FranjaHoraria;

public class ValidadorHorasLogicas extends ValidadorFranjaHorariaBase {

    private final FranjaHorariaFormateadorResultadosIntPort formateador;

    public ValidadorHorasLogicas(FranjaHorariaFormateadorResultadosIntPort formateador) {
        this.formateador = formateador;
    }

    @Override
    protected void ejecutarValidacion(FranjaHoraria franjaHoraria) {
        if (franjaHoraria.getHoraInicio().isAfter(franjaHoraria.getHoraFin()) ||
                franjaHoraria.getHoraInicio().equals(franjaHoraria.getHoraFin())) {
            formateador.retornarRespuestaErrorReglaDeNegocio(
                    "La hora de inicio debe ser menor que la hora de fin");
        }
    }
}