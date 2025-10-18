package co.edu.unicauca.asae.taller07.franjaHoraria.dominio.modelos;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Modelo de dominio para Franja Horaria (POJO puro)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FranjaHoraria {
    private int id;
    private DiaSemana dia;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Curso curso; // Representación del dominio FranjaHoraria
    private EspacioFisico espacioFisico; // Representación del dominio FranjaHoraria
}