package co.edu.unicauca.asae.taller07.docente.aplicacion.output;

public interface DocenteFormateadorResultadosIntPort {

    void retornarRespuestaErrorEntidadExiste(String mensaje);

    void retornarRespuestaErrorReglaDeNegocio(String mensaje);

    void retornarRespuestaErrorEntidadNoExiste(String mensaje);
}