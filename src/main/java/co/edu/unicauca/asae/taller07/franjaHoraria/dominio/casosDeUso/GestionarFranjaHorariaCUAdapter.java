package co.edu.unicauca.asae.taller07.franjaHoraria.dominio.casosDeUso;

import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.input.GestionarFranjaHorariaCUIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.FranjaHorariaFormateadorResultadosIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.FranjaHorariaGatewayIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.aplicacion.output.ValidacionesFranjaHorariaGatewayIntPort;
import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.FranjaHoraria;
import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.validadores.CadenaValidacionFranjaHorariaFactory;
import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.validadores.ValidadorFranjaHoraria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Caso de uso: Crear y eliminar franjas horarias
 * Implementa Cadena de Responsabilidad para validaciones
 */
public class GestionarFranjaHorariaCUAdapter implements GestionarFranjaHorariaCUIntPort {

    private final FranjaHorariaGatewayIntPort gateway;
    private final ValidadorFranjaHoraria cadenaValidacion;
    private static final Logger log = LoggerFactory.getLogger(GestionarFranjaHorariaCUAdapter.class);

    public GestionarFranjaHorariaCUAdapter(
            FranjaHorariaGatewayIntPort gateway,
            ValidacionesFranjaHorariaGatewayIntPort validacionesGateway,
            FranjaHorariaFormateadorResultadosIntPort formateador) {
        this.gateway = gateway;

        // Construir la cadena de validadores
        this.cadenaValidacion = CadenaValidacionFranjaHorariaFactory.crear(
                validacionesGateway,
                formateador);
    }

    @Override
    public FranjaHoraria crear(FranjaHoraria franjaHoraria) {
        log.info("Creando franja horaria para curso ID: {}", franjaHoraria.getCurso().getId());

        cadenaValidacion.validar(franjaHoraria);

        FranjaHoraria resultado = gateway.guardar(franjaHoraria);

        log.info("Franja horaria creada exitosamente con ID: {}", resultado.getId());
        return resultado;
    }

    @Override
    public int eliminarFranjasPorCurso(int cursoId) {
        // Aquí podríamos usar otro validador si fuera necesario
        return gateway.eliminarFranjasPorCurso(cursoId);
    }
}