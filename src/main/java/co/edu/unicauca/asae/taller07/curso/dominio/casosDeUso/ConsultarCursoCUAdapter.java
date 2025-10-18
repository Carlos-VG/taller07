package co.edu.unicauca.asae.taller07.curso.dominio.casosDeUso;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.unicauca.asae.taller07.curso.aplicacion.input.ConsultarCursoCUIntPort;
import co.edu.unicauca.asae.taller07.curso.aplicacion.output.CursoGatewayIntPort;
import co.edu.unicauca.asae.taller07.curso.dominio.modelos.Curso;

public class ConsultarCursoCUAdapter implements ConsultarCursoCUIntPort {

    private static final Logger log = LoggerFactory.getLogger(ConsultarCursoCUAdapter.class);

    private final CursoGatewayIntPort gateway;

    public ConsultarCursoCUAdapter(CursoGatewayIntPort gateway) {
        this.gateway = gateway;
    }

    @Override
    public List<Curso> listarPorNombreAsignatura(String nombreAsignatura) {
        log.info("▶ Consultando cursos por asignatura: '{}'", nombreAsignatura);

        List<Curso> cursos = gateway.listarPorNombreAsignatura(nombreAsignatura);

        log.info("✓ Se encontraron {} curso(s) para la asignatura '{}'",
                cursos.size(), nombreAsignatura);
        log.debug("Cursos encontrados: {}", cursos);

        return cursos;
    }
}