package co.edu.unicauca.asae.taller07.curso.dominio.casosDeUso;

import java.util.List;
import co.edu.unicauca.asae.taller07.curso.aplicacion.input.ConsultarCursoCUIntPort;
import co.edu.unicauca.asae.taller07.curso.aplicacion.output.CursoGatewayIntPort;
import co.edu.unicauca.asae.taller07.curso.dominio.modelos.Curso;

public class ConsultarCursoCUAdapter implements ConsultarCursoCUIntPort {

    private final CursoGatewayIntPort gateway;

    public ConsultarCursoCUAdapter(CursoGatewayIntPort gateway) {
        this.gateway = gateway;
    }

    @Override
    public List<Curso> listarPorNombreAsignatura(String nombreAsignatura) {
        return gateway.listarPorNombreAsignatura(nombreAsignatura);
    }
}