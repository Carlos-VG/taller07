package co.edu.unicauca.asae.taller07.docente.aplicacion.output;

import co.edu.unicauca.asae.taller07.docente.dominio.modelos.Docente;

public interface DocenteGatewayIntPort {

    Docente guardar(Docente docente);

    boolean existePorId(int id);
}