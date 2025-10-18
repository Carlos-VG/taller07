package co.edu.unicauca.asae.taller07.espacioFisico.aplicacion.input;

public interface GestionarEspacioFisicoCUIntPort {
    /**
     * Actualiza el estado (activo/inactivo) de un espacio físico
     * 
     * @param id     ID del espacio físico
     * @param activo nuevo estado
     * @return número de registros actualizados
     */
    int actualizarEstado(int id, boolean activo);
}