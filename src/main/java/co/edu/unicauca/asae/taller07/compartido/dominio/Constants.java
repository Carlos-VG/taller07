package co.edu.unicauca.asae.taller07.compartido.dominio;

public final class Constants {

    private Constants() {
        throw new UnsupportedOperationException("Utility class");
    }

    // Formatos
    public static final String TIME_FORMAT = "HH:mm";
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    // Validaciones
    public static final int MIN_NOMBRE_LENGTH = 2;
    public static final int MAX_NOMBRE_LENGTH = 50;
    public static final int MIN_OFICINA_LENGTH = 3;
    public static final int MAX_OFICINA_LENGTH = 20;

    // Mensajes
    public static final String ENTIDAD_NO_EXISTE_MSG = "La entidad con id %d no existe";
    public static final String ESPACIO_OCUPADO_MSG = "El espacio '%s' está ocupado el %s de %s a %s";
    public static final String DOCENTE_OCUPADO_MSG = "El docente '%s %s' está ocupado el %s de %s a %s";
}