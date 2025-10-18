package co.edu.unicauca.asae.taller07.franjaHoraria.dominio.validadores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.FranjaHorariaFormateadorResultadosIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.ValidacionesFranjaHorariaGatewayIntPort;

/**
 * Factory que construye la cadena de validadores en el orden correcto
 */
public class CadenaValidacionFranjaHorariaFactory {

    private static final Logger log = LoggerFactory.getLogger(CadenaValidacionFranjaHorariaFactory.class);

    /**
     * Construye la cadena completa de validadores
     * Orden: HorasLógicas → CursoExiste → EspacioExiste → EspacioDisponible →
     * DocentesDisponibles
     */
    public static ValidadorFranjaHoraria crear(
            ValidacionesFranjaHorariaGatewayIntPort validacionesGateway,
            FranjaHorariaFormateadorResultadosIntPort formateador) {

        log.debug("Construyendo cadena de validación...");

        // Crear los validadores
        ValidadorFranjaHoraria validador0 = new ValidadorHorasLogicas(formateador);
        ValidadorFranjaHoraria validador1 = new ValidadorCursoExiste(validacionesGateway, formateador);
        ValidadorFranjaHoraria validador2 = new ValidadorEspacioFisicoExiste(validacionesGateway, formateador);
        ValidadorFranjaHoraria validador3 = new ValidadorEspacioFisicoDisponible(validacionesGateway, formateador);
        ValidadorFranjaHoraria validador4 = new ValidadorDocentesDisponibles(validacionesGateway, formateador);

        // Encadenar: 0 → 1 → 2 → 3 → 4
        validador0.setSiguiente(validador1);
        validador1.setSiguiente(validador2);
        validador2.setSiguiente(validador3);
        validador3.setSiguiente(validador4);

        log.debug(
                "✓ Cadena construida: HorasLógicas → CursoExiste → EspacioExiste → EspacioDisponible → DocentesDisponibles");

        // Retornar el primero de la cadena
        return validador0;
    }
}