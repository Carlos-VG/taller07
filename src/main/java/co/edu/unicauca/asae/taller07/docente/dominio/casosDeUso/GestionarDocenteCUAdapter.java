package co.edu.unicauca.asae.taller07.docente.dominio.casosDeUso;

import co.edu.unicauca.asae.taller07.docente.aplicacion.input.GestionarDocenteCUIntPort;
import co.edu.unicauca.asae.taller07.docente.aplicacion.output.DocenteFormateadorResultadosIntPort;
import co.edu.unicauca.asae.taller07.docente.aplicacion.output.DocenteGatewayIntPort;
import co.edu.unicauca.asae.taller07.docente.aplicacion.output.ValidacionesDocenteGatewayIntPort;
import co.edu.unicauca.asae.taller07.docente.dominio.modelos.Docente;

/**
 * Caso de uso: Crear docente
 * Aplica patrón Cadena de Responsabilidad para validaciones
 */
public class GestionarDocenteCUAdapter implements GestionarDocenteCUIntPort {

    private final DocenteGatewayIntPort gateway;
    private final ValidacionesDocenteGatewayIntPort validacionesGateway;
    private final DocenteFormateadorResultadosIntPort formateador;

    public GestionarDocenteCUAdapter(
            DocenteGatewayIntPort gateway,
            ValidacionesDocenteGatewayIntPort validacionesGateway,
            DocenteFormateadorResultadosIntPort formateador) {
        this.gateway = gateway;
        this.validacionesGateway = validacionesGateway;
        this.formateador = formateador;
    }

    @Override
    public Docente crear(Docente docente) {
        // VALIDACIÓN 1: Correo único (Cadena de Responsabilidad)
        if (validacionesGateway.existeDocentePorCorreo(docente.getCorreo())) {
            formateador.retornarRespuestaErrorEntidadExiste(
                    "Ya existe un docente con el correo: " + docente.getCorreo());
        }

        // Si pasa validaciones, guardar (cascade PERSIST maneja oficina)
        return gateway.guardar(docente);
    }
}