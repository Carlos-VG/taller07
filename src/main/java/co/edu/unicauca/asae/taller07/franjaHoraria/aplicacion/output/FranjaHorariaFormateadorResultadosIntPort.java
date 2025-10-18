package co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output;

public interface FranjaHorariaFormateadorResultadosIntPort {

    void retornarRespuestaErrorEntidadExiste(String mensaje);

    void retornarRespuestaErrorReglaDeNegocio(String mensaje);

    void retornarRespuestaErrorEntidadNoExiste(String mensaje);
}