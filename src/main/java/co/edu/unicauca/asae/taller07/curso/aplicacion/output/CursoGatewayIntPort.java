package co.edu.unicauca.asae.taller07.curso.aplicacion.output;

import java.util.List;
import co.edu.unicauca.asae.taller07.curso.dominio.modelos.Curso;

public interface CursoGatewayIntPort {
    List<Curso> listarPorNombreAsignatura(String nombreAsignatura);
}