package br.com.gusta.odontosys.msendereco.core.exceptions;

public class EnderecoNotFoundException extends EnderecoException {

    public EnderecoNotFoundException() {
        super();
    }

    public EnderecoNotFoundException(String message) {
        super(message);
    }

    public EnderecoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnderecoNotFoundException(Throwable cause) {
        super(cause);
    }

    protected EnderecoNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
