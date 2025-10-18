package co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.input.controllerGestionarFranjaHoraria.mappers;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos.*;
import co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.input.controllerGestionarFranjaHoraria.DTOPeticion.FranjaHorariaDTOPeticion;
import co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.input.controllerGestionarFranjaHoraria.DTORespuesta.*;

/**
 * Mapper manual entre DTOs e infraestructura <-> Dominio
 */
@Component
public class FranjaHorariaMapperInfraestructuraDominio {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    // ========================================================================
    // PETICIÓN -> DOMINIO
    // ========================================================================

    public FranjaHoraria mappearDePeticionAFranjaHoraria(FranjaHorariaDTOPeticion peticion) {
        FranjaHoraria franja = new FranjaHoraria();
        franja.setDia(DiaSemana.valueOf(peticion.getDia().toUpperCase()));
        franja.setHoraInicio(LocalTime.parse(peticion.getHoraInicio(), TIME_FORMATTER));
        franja.setHoraFin(LocalTime.parse(peticion.getHoraFin(), TIME_FORMATTER));

        // Crear objetos con solo IDs (se completarán en el gateway)
        Curso curso = new Curso();
        curso.setId(peticion.getCursoId());
        franja.setCurso(curso);

        EspacioFisico espacio = new EspacioFisico();
        espacio.setId(peticion.getEspacioFisicoId());
        franja.setEspacioFisico(espacio);

        return franja;
    }

    // ========================================================================
    // DOMINIO -> RESPUESTA SIMPLE
    // ========================================================================

    public FranjaHorariaDTORespuesta mappearDeFranjaHorariaARespuesta(FranjaHoraria franja) {
        FranjaHorariaDTORespuesta dto = new FranjaHorariaDTORespuesta();
        dto.setId(franja.getId());
        dto.setDia(franja.getDia().toString());
        dto.setHoraInicio(franja.getHoraInicio().format(TIME_FORMATTER));
        dto.setHoraFin(franja.getHoraFin().format(TIME_FORMATTER));
        dto.setCursoId(franja.getCurso() != null ? franja.getCurso().getId() : null);
        dto.setEspacioFisicoId(franja.getEspacioFisico() != null ? franja.getEspacioFisico().getId() : null);
        return dto;
    }

    // ========================================================================
    // DOMINIO -> RESPUESTA DE DOCENTE (EAGER: curso y espacio)
    // ========================================================================

    public FranjaHorariaDeDocenteDTORespuesta mappearDeFranjaHorariaADocenteRespuesta(FranjaHoraria franja) {
        FranjaHorariaDeDocenteDTORespuesta dto = new FranjaHorariaDeDocenteDTORespuesta();
        dto.setId(franja.getId());
        dto.setDia(franja.getDia().toString());
        dto.setHoraInicio(franja.getHoraInicio().format(TIME_FORMATTER));
        dto.setHoraFin(franja.getHoraFin().format(TIME_FORMATTER));

        // Curso
        if (franja.getCurso() != null) {
            CursoDTORespuesta cursoDto = new CursoDTORespuesta();
            cursoDto.setId(franja.getCurso().getId());
            cursoDto.setNombre(franja.getCurso().getNombre());
            cursoDto.setDocentes(mappearDocentes(franja.getCurso().getDocentes()));
            dto.setCurso(cursoDto);
        }

        // Espacio
        if (franja.getEspacioFisico() != null) {
            dto.setEspacioFisico(mappearEspacioFisico(franja.getEspacioFisico()));
        }

        return dto;
    }

    public List<FranjaHorariaDeDocenteDTORespuesta> mappearListaDeFranjaHorariaADocenteRespuesta(
            List<FranjaHoraria> franjas) {
        return franjas.stream()
                .map(this::mappearDeFranjaHorariaADocenteRespuesta)
                .collect(Collectors.toList());
    }

    // ========================================================================
    // DOMINIO -> RESPUESTA DE CURSO (LAZY: curso, EAGER: espacio y docentes)
    // ========================================================================

    public FranjaHorariaDeCursoDTORespuesta mappearDeFranjaHorariaACursoRespuesta(FranjaHoraria franja) {
        FranjaHorariaDeCursoDTORespuesta dto = new FranjaHorariaDeCursoDTORespuesta();
        dto.setId(franja.getId());
        dto.setDia(franja.getDia().toString());
        dto.setHoraInicio(franja.getHoraInicio().format(TIME_FORMATTER));
        dto.setHoraFin(franja.getHoraFin().format(TIME_FORMATTER));

        // Espacio
        if (franja.getEspacioFisico() != null) {
            dto.setEspacioFisico(mappearEspacioFisico(franja.getEspacioFisico()));
        }

        // Docentes del curso
        if (franja.getCurso() != null && franja.getCurso().getDocentes() != null) {
            dto.setDocentes(mappearDocentes(franja.getCurso().getDocentes()));
        }

        return dto;
    }

    public List<FranjaHorariaDeCursoDTORespuesta> mappearListaDeFranjaHorariaACursoRespuesta(
            List<FranjaHoraria> franjas) {
        return franjas.stream()
                .map(this::mappearDeFranjaHorariaACursoRespuesta)
                .collect(Collectors.toList());
    }

    // ========================================================================
    // HELPERS
    // ========================================================================

    private EspacioFisicoDTORespuesta mappearEspacioFisico(EspacioFisico espacio) {
        EspacioFisicoDTORespuesta dto = new EspacioFisicoDTORespuesta();
        dto.setId(espacio.getId());
        dto.setNombre(espacio.getNombre());
        dto.setCapacidad(espacio.getCapacidad());
        dto.setActivo(espacio.isActivo());
        return dto;
    }

    private Set<DocenteDTORespuesta> mappearDocentes(Set<Docente> docentes) {
        return docentes.stream()
                .map(this::mappearDocente)
                .collect(Collectors.toSet());
    }

    private DocenteDTORespuesta mappearDocente(Docente docente) {
        DocenteDTORespuesta dto = new DocenteDTORespuesta();
        dto.setId(docente.getId());
        dto.setNombre(docente.getNombre());
        dto.setApellido(docente.getApellido());
        dto.setCorreo(docente.getCorreo());
        return dto;
    }
}