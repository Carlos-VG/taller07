package co.edu.unicauca.asae.taller07.compartido.infraestructura.output.controladorExcepciones.excepcionesPropias;

import co.edu.unicauca.asae.taller07.compartido.infraestructura.output.controladorExcepciones.estructuraExcepciones.CodigoError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public abstract class GestionHorariosRuntimeException extends RuntimeException {

    protected CodigoError codigoError;

    public abstract String formatException();
}
