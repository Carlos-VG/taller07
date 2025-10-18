package co.edu.unicauca.asae.taller07.espacioFisico.infraestructura.input.controllerGestionarEspacioFisico.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import co.edu.unicauca.asae.taller07.espacioFisico.dominio.modelos.EspacioFisico;
import co.edu.unicauca.asae.taller07.espacioFisico.infraestructura.input.controllerGestionarEspacioFisico.DTORespuesta.EspacioFisicoDTORespuesta;

@Mapper(componentModel = "spring")
public interface EspacioFisicoMapperInfraestructuraDominio {

    EspacioFisicoDTORespuesta mappearDeEspacioFisicoARespuesta(EspacioFisico espacioFisico);

    List<EspacioFisicoDTORespuesta> mappearDeEspaciosFisicosARespuesta(List<EspacioFisico> espaciosFisicos);
}