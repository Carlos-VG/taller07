package co.edu.unicauca.asae.taller07.compartido.infraestructura.output.controladorExcepciones;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import co.edu.unicauca.asae.taller07.compartido.infraestructura.output.controladorExcepciones.estructuraExcepciones.Error;
import co.edu.unicauca.asae.taller07.compartido.infraestructura.output.controladorExcepciones.estructuraExcepciones.CodigoError;
import co.edu.unicauca.asae.taller07.compartido.infraestructura.output.controladorExcepciones.estructuraExcepciones.ErrorUtils;
import co.edu.unicauca.asae.taller07.compartido.infraestructura.output.controladorExcepciones.excepcionesPropias.EntidadNoExisteException;
import co.edu.unicauca.asae.taller07.compartido.infraestructura.output.controladorExcepciones.excepcionesPropias.EntidadYaExisteException;
import co.edu.unicauca.asae.taller07.compartido.infraestructura.output.controladorExcepciones.excepcionesPropias.ReglaNegocioExcepcion;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

/**
 * Controlador global de excepciones para toda la aplicación.
 * Maneja diferentes tipos de excepciones y retorna respuestas HTTP adecuadas.
 */
@ControllerAdvice
public class RestApiExceptionHandler {

        /**
         * Maneja excepciones genéricas no capturadas por otros handlers.
         * Retorna HTTP 500 (INTERNAL_SERVER_ERROR)
         */
        @ExceptionHandler(Exception.class)
        public ResponseEntity<Error> handleGenericException(
                        final HttpServletRequest req,
                        final Exception ex,
                        final Locale locale) {

                final Error error = ErrorUtils
                                .crearError(
                                                CodigoError.ERROR_GENERICO.getCodigo(),
                                                CodigoError.ERROR_GENERICO.getLlaveMensaje(),
                                                HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .setUrl(req.getRequestURL().toString())
                                .setMetodo(req.getMethod());

                return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        /**
         * Maneja excepciones cuando una entidad ya existe.
         * Retorna HTTP 406 (NOT_ACCEPTABLE)
         */
        @ExceptionHandler(EntidadYaExisteException.class)
        public ResponseEntity<Error> handleEntidadYaExisteException(
                        final HttpServletRequest req,
                        final EntidadYaExisteException ex) {

                final Error error = ErrorUtils
                                .crearError(
                                                CodigoError.ENTIDAD_YA_EXISTE.getCodigo(),
                                                String.format("%s, %s",
                                                                CodigoError.ENTIDAD_YA_EXISTE.getLlaveMensaje(),
                                                                ex.getMessage()),
                                                HttpStatus.NOT_ACCEPTABLE.value())
                                .setUrl(req.getRequestURL().toString())
                                .setMetodo(req.getMethod());

                return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
        }

        /**
         * Maneja excepciones de violación de reglas de negocio.
         * Retorna HTTP 400 (BAD_REQUEST)
         */
        @ExceptionHandler(ReglaNegocioExcepcion.class)
        public ResponseEntity<Error> handleReglaNegocioException(
                        final HttpServletRequest req,
                        final ReglaNegocioExcepcion ex,
                        final Locale locale) {

                final Error error = ErrorUtils
                                .crearError(
                                                CodigoError.VIOLACION_REGLA_DE_NEGOCIO.getCodigo(),
                                                ex.formatException(),
                                                HttpStatus.BAD_REQUEST.value())
                                .setUrl(req.getRequestURL().toString())
                                .setMetodo(req.getMethod());

                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        /**
         * Maneja excepciones cuando una entidad no existe.
         * Retorna HTTP 404 (NOT_FOUND)
         */
        @ExceptionHandler(EntidadNoExisteException.class)
        public ResponseEntity<Error> handleEntidadNoExisteException(
                        final HttpServletRequest req,
                        final EntidadNoExisteException ex,
                        final Locale locale) {

                final Error error = ErrorUtils
                                .crearError(
                                                CodigoError.ENTIDAD_NO_ENCONTRADA.getCodigo(),
                                                String.format("%s, %s",
                                                                CodigoError.ENTIDAD_NO_ENCONTRADA.getLlaveMensaje(),
                                                                ex.getMessage()),
                                                HttpStatus.NOT_FOUND.value())
                                .setUrl(req.getRequestURL().toString())
                                .setMetodo(req.getMethod());

                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        /**
         * Maneja excepciones de validación de Bean Validation (@Valid).
         * Retorna HTTP 400 (BAD_REQUEST) con mapa de errores campo->mensaje
         */
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, String>> handleValidationExceptions(
                        MethodArgumentNotValidException ex) {

                System.out.println("Retornando respuesta con los errores identificados");
                Map<String, String> errores = new HashMap<>();

                ex.getBindingResult().getAllErrors().forEach((error) -> {
                        String campo = ((FieldError) error).getField();
                        String mensajeDeError = error.getDefaultMessage();
                        errores.put(campo, mensajeDeError);
                });

                return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }

        /**
         * Maneja excepciones de violación de constraints.
         * Retorna HTTP 400 (BAD_REQUEST)
         */
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<String> handleConstraintViolationException(
                        ConstraintViolationException e) {

                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
}