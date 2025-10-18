package co.edu.unicauca.asae.taller07.franjaHoraria.dominio.casosDeUso;

import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.input.GestionarFranjaHorariaCUIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.FranjaHorariaFormateadorResultadosIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.FranjaHorariaGatewayIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.ValidacionesFranjaHorariaGatewayIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.FranjaHoraria;

/**
 * Caso de uso: Crear y eliminar franjas horarias
 * Implementa Cadena de Responsabilidad para validaciones
 */
public class GestionarFranjaHorariaCUAdapter implements GestionarFranjaHorariaCUIntPort {

    private final FranjaHorariaGatewayIntPort gateway;
    private final ValidacionesFranjaHorariaGatewayIntPort validacionesGateway;
    private final FranjaHorariaFormateadorResultadosIntPort formateador;

    public GestionarFranjaHorariaCUAdapter(
            FranjaHorariaGatewayIntPort gateway,
            ValidacionesFranjaHorariaGatewayIntPort validacionesGateway,
            FranjaHorariaFormateadorResultadosIntPort formateador) {
        this.gateway = gateway;
        this.validacionesGateway = validacionesGateway;
        this.formateador = formateador;
    }

    @Override
    public FranjaHoraria crear(FranjaHoraria franjaHoraria) {
        // ====================================================================
        // CADENA DE RESPONSABILIDAD - VALIDACIONES
        // ====================================================================

        // VALIDACIÓN 1: Curso existe
        if (!validacionesGateway.cursoExiste(franjaHoraria.getCurso().getId())) {
            formateador.retornarRespuestaErrorEntidadNoExiste(
                    "No existe el curso con id: " + franjaHoraria.getCurso().getId());
        }

        // VALIDACIÓN 2: Espacio físico existe
        if (!validacionesGateway.espacioFisicoExiste(franjaHoraria.getEspacioFisico().getId())) {
            formateador.retornarRespuestaErrorEntidadNoExiste(
                    "No existe el espacio físico con id: " + franjaHoraria.getEspacioFisico().getId());
        }

        // VALIDACIÓN 3: Espacio físico no ocupado (JPQL)
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

        // VALIDACIÓN 4: Docentes no ocupados (SQL Nativo)
        // Verificar cada docente del curso
        for (var docente : franjaHoraria.getCurso().getDocentes()) {
            if (!validacionesGateway.docenteExiste(docente.getId())) {
                formateador.retornarRespuestaErrorEntidadNoExiste(
                        "No existe el docente con id: " + docente.getId());
            }

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

        // Si pasa todas las validaciones, guardar
        return gateway.guardar(franjaHoraria);
    }

    @Override
    public int eliminarFranjasPorCurso(int cursoId) {
        // Validar que el curso exista
        if (!validacionesGateway.cursoExiste(cursoId)) {
            formateador.retornarRespuestaErrorEntidadNoExiste(
                    "No existe el curso con id: " + cursoId);
        }

        return gateway.eliminarFranjasPorCurso(cursoId);
    }
}