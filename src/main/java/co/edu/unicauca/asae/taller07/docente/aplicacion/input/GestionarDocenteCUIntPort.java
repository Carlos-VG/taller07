package co.edu.unicauca.asae.taller07.docente.aplicacion.input;

import co.edu.unicauca.asae.taller07.docente.dominio.modelos.Docente;

public interface GestionarDocenteCUIntPort {
    /**
     * Crea un docente con su persona y oficina (cascade PERSIST)
     * 
     * @param docente docente a crear
     * @return docente creado con ID asignado
     */
    Docente crear(Docente docente);
}