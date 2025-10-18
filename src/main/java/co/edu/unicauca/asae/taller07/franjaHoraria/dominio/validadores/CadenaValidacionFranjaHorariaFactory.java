package co.edu.unicauca.asae.taller07.franjaHoraria.dominio.validadores;

import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.FranjaHorariaFormateadorResultadosIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.ValidacionesFranjaHorariaGatewayIntPort;

/**
 * Factory que construye la cadena de validadores en el orden correcto
 */
public class CadenaValidacionFranjaHorariaFactory {

    /**
     * Construye la cadena completa de validadores
     * Orden: CursoExiste -> EspacioExiste -> EspacioDisponible ->
     * DocentesDisponibles
     */
    public static ValidadorFranjaHoraria crear(
            ValidacionesFranjaHorariaGatewayIntPort validacionesGateway,
            FranjaHorariaFormateadorResultadosIntPort formateador) {

        // Crear los validadores
        ValidadorFranjaHoraria validador0 = new ValidadorHorasLogicas(formateador);
        ValidadorFranjaHoraria validador1 = new ValidadorCursoExiste(validacionesGateway, formateador);
        ValidadorFranjaHoraria validador2 = new ValidadorEspacioFisicoExiste(validacionesGateway, formateador);
        ValidadorFranjaHoraria validador3 = new ValidadorEspacioFisicoDisponible(validacionesGateway, formateador);
        ValidadorFranjaHoraria validador4 = new ValidadorDocentesDisponibles(validacionesGateway, formateador);

        // Encadenar: 0 -> 1 -> 2 -> 3 -> 4
        validador0.setSiguiente(validador1);
        validador1.setSiguiente(validador2);
        validador2.setSiguiente(validador3);
        validador3.setSiguiente(validador4);

        // Retornar el primero de la cadena
        return validador0;
    }
}