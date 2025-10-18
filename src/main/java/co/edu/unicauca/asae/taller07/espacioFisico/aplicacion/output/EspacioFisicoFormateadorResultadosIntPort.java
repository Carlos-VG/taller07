package co.edu.unicauca.asae.taller07.espacioFisico.aplicacion.output;

public interface EspacioFisicoFormateadorResultadosIntPort {

    void retornarRespuestaErrorEntidadExiste(String mensaje);

    void retornarRespuestaErrorReglaDeNegocio(String mensaje);

    void retornarRespuestaErrorEntidadNoExiste(String mensaje);
}