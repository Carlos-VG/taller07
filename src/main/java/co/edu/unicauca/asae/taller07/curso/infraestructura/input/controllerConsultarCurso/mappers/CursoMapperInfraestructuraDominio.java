package co.edu.unicauca.asae.taller07.curso.infraestructura.input.controllerConsultarCurso.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import co.edu.unicauca.asae.taller07.curso.dominio.modelos.Curso;
import co.edu.unicauca.asae.taller07.curso.infraestructura.input.controllerConsultarCurso.DTORespuesta.CursoDTORespuesta;

@Mapper(componentModel = "spring")
public interface CursoMapperInfraestructuraDominio {

    CursoDTORespuesta mappearDeCursoARespuesta(Curso curso);

    List<CursoDTORespuesta> mappearDeCursosARespuesta(List<Curso> cursos);
}