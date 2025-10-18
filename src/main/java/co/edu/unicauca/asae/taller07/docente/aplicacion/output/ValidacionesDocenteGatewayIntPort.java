package co.edu.unicauca.asae.taller07.docente.aplicacion.output;

public interface ValidacionesDocenteGatewayIntPort {
    /**
     * Verifica si existe un docente con el correo dado
     */
    boolean existeDocentePorCorreo(String correo);
}