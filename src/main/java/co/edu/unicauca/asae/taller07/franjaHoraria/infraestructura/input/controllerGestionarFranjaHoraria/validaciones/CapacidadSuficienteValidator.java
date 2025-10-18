package co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.input.controllerGestionarFranjaHoraria.validaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.input.controllerGestionarFranjaHoraria.DTOPeticion.FranjaHorariaDTOPeticion;
import co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.repositorios.CursoRepository;
import co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.repositorios.EspacioFisicoRepository;
import co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.entidades.CursoEntity;
import co.edu.unicauca.asae.taller07.franjaHoraria.infraestructura.output.persistencia.entidades.EspacioFisicoEntity;

/**
 * Validador de capacidad suficiente
 */
@Component
public class CapacidadSuficienteValidator
        implements ConstraintValidator<CapacidadSuficiente, FranjaHorariaDTOPeticion> {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private EspacioFisicoRepository espacioFisicoRepository;

    @Override
    public boolean isValid(FranjaHorariaDTOPeticion value, ConstraintValidatorContext context) {
        if (value == null || value.getCursoId() == null || value.getEspacioFisicoId() == null) {
            return true; // Otras validaciones manejan nulos
        }

        // Obtener curso y espacio
        var cursoOpt = cursoRepository.findById(value.getCursoId());
        var espacioOpt = espacioFisicoRepository.findById(value.getEspacioFisicoId());

        if (cursoOpt.isEmpty() || espacioOpt.isEmpty()) {
            return true; // Otras validaciones manejan existencia
        }

        CursoEntity curso = cursoOpt.get();
        EspacioFisicoEntity espacio = espacioOpt.get();

        // Validar capacidad
        if (curso.getMatriculaEstimada() != null && espacio.getCapacidad() != null) {
            return espacio.getCapacidad() >= curso.getMatriculaEstimada();
        }

        return true; // Si no hay datos, se permite
    }
}