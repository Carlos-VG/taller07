package co.edu.unicauca.asae.taller07.espacioFisico.aplicacion.output;

import java.util.List;
import co.edu.unicauca.asae.taller07.espacioFisico.dominio.modelos.EspacioFisico;

public interface EspacioFisicoGatewayIntPort {

    List<EspacioFisico> listarPorPatronYCapacidad(String patron, Integer capacidadMinima);

    int actualizarEstado(int id, boolean activo);

    boolean existePorId(int id);
}