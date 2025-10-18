package co.edu.unicauca.asae.taller07.docente.infraestructura.input.controllerGestionarDocente.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.unicauca.asae.taller07.docente.dominio.modelos.Docente;
import co.edu.unicauca.asae.taller07.docente.dominio.modelos.Oficina;
import co.edu.unicauca.asae.taller07.docente.infraestructura.input.controllerGestionarDocente.DTOPeticion.DocenteDTOPeticion;
import co.edu.unicauca.asae.taller07.docente.infraestructura.input.controllerGestionarDocente.DTOPeticion.OficinaDTOPeticion;
import co.edu.unicauca.asae.taller07.docente.infraestructura.input.controllerGestionarDocente.DTORespuesta.DocenteDTORespuesta;
import co.edu.unicauca.asae.taller07.docente.infraestructura.input.controllerGestionarDocente.DTORespuesta.OficinaDTORespuesta;

@Mapper(componentModel = "spring")
public interface DocenteMapperInfraestructuraDominio {

    @Mapping(target = "id", ignore = true)
    Docente mappearDePeticionADocente(DocenteDTOPeticion peticion);

    @Mapping(target = "id", ignore = true)
    Oficina mappearDePeticionAOficina(OficinaDTOPeticion peticion);

    DocenteDTORespuesta mappearDeDocenteARespuesta(Docente docente);

    OficinaDTORespuesta mappearDeOficinaARespuesta(Oficina oficina);
}