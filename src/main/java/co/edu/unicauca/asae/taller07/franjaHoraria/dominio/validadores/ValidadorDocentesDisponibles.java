package co.edu.unicauca.asae.taller07.franjaHoraria.dominio.validadores;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.FranjaHorariaFormateadorResultadosIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.ValidacionesFranjaHorariaGatewayIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.Docente;
import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.FranjaHoraria;

/**
 * Validador 4: Verifica que los docentes existan y NO estén ocupados (SQL
 * Nativo)
 */
public class ValidadorDocentesDisponibles extends ValidadorFranjaHorariaBase {

        private static final Logger log = LoggerFactory.getLogger(ValidadorDocentesDisponibles.class);

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

                // OBTENER DOCENTES DESDE LA BASE DE DATOS
                Set<Docente> docentes = validacionesGateway.obtenerDocentesDeCurso(
                                franjaHoraria.getCurso().getId());

                // Verificar que el curso tenga docentes
                if (docentes.isEmpty()) {
                        return; // No hay docentes que validar
                }

                log.debug("Validando disponibilidad de {} docente(s)", docentes.size());

                // Validar cada docente del curso
                for (Docente docente : docentes) {
                        log.debug(">>> Validando docente ID: {} - {} {}",
                                        docente.getId(), docente.getNombre(), docente.getApellido());

                        // Validar existencia (redundante, pero por seguridad)
                        if (!validacionesGateway.docenteExiste(docente.getId())) {
                                formateador.retornarRespuestaErrorEntidadNoExiste(
                                                "No existe el docente con id: " + docente.getId());
                        }

                        // VALIDAR DISPONIBILIDAD (Esta es la validación crítica)
                        boolean ocupado = validacionesGateway.docenteOcupado(
                                        franjaHoraria.getDia().toString(), // "LUNES", "MARTES", etc.
                                        franjaHoraria.getHoraInicio(),
                                        franjaHoraria.getHoraFin(),
                                        docente.getId());

                        log.debug("Docente {} {} (ID: {}) - Ocupado: {}",
                                        docente.getNombre(), docente.getApellido(), docente.getId(), ocupado);

                        if (ocupado) {
                                String mensaje = String.format(
                                                "El docente '%s %s' (ID: %d) ya está ocupado el %s de %s a %s",
                                                docente.getNombre(),
                                                docente.getApellido(),
                                                docente.getId(),
                                                franjaHoraria.getDia(),
                                                franjaHoraria.getHoraInicio(),
                                                franjaHoraria.getHoraFin());

                                log.error("VALIDACIÓN FALLIDA: {}", mensaje);
                                formateador.retornarRespuestaErrorReglaDeNegocio(mensaje);
                        }
                }

                log.debug("Todos los docentes están disponibles");
        }
}