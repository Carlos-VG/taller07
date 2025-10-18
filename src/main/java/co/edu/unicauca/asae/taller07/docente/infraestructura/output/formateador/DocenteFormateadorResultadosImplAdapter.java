package co.edu.unicauca.asae.taller07.docente.infraestructura.output.formateador;

import org.springframework.stereotype.Service;

import co.edu.unicauca.asae.taller07.compartido.infraestructura.output.controladorExcepciones.excepcionesPropias.EntidadNoExisteException;
import co.edu.unicauca.asae.taller07.compartido.infraestructura.output.controladorExcepciones.excepcionesPropias.EntidadYaExisteException;
import co.edu.unicauca.asae.taller07.compartido.infraestructura.output.controladorExcepciones.excepcionesPropias.ReglaNegocioExcepcion;
import co.edu.unicauca.asae.taller07.docente.aplicacion.output.DocenteFormateadorResultadosIntPort;

@Service
public class DocenteFormateadorResultadosImplAdapter implements DocenteFormateadorResultadosIntPort {

    @Override
    public void retornarRespuestaErrorEntidadExiste(String mensaje) {
        throw new EntidadYaExisteException(mensaje);
    }

    @Override
    public void retornarRespuestaErrorReglaDeNegocio(String mensaje) {
        throw new ReglaNegocioExcepcion(mensaje);
    }

    @Override
    public void retornarRespuestaErrorEntidadNoExiste(String mensaje) {
        throw new EntidadNoExisteException(mensaje);
    }
}