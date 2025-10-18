package co.edu.unicauca.asae.taller07.espacioFisico.aplicacion.input;

import java.util.List;
import co.edu.unicauca.asae.taller07.espacioFisico.dominio.modelos.EspacioFisico;

public interface ConsultarEspacioFisicoCUIntPort {
    /**
     * Lista espacios físicos que comiencen con un patrón y capacidad >= parámetro
     * Ordenados ascendentemente por nombre
     * 
     * @param patron          patrón de búsqueda (ignore case)
     * @param capacidadMinima capacidad mínima
     * @return lista de espacios físicos
     */
    List<EspacioFisico> listarPorPatronYCapacidad(String patron, Integer capacidadMinima);
}