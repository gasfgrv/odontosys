package br.com.gusta.odontosys.msendereco.core.exceptions;

public class EnderecoException extends RuntimeException {
    public EnderecoException() {
        super();
    }

    public EnderecoException(String message) {
        super(message);
    }

    public EnderecoException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnderecoException(Throwable cause) {
        super(cause);
    }

    protected EnderecoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
