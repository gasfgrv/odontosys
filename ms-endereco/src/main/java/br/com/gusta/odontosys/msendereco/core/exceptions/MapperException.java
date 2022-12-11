package br.com.gusta.odontosys.msendereco.core.exceptions;

public class MapperException extends RuntimeException {

    public MapperException() {
        super();
    }

    public MapperException(String message, Throwable cause) {
        super(message, cause);
    }

    public MapperException(String message) {
        super(message);
    }

    public MapperException(Throwable cause) {
        super(cause);
    }

    protected MapperException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
