package br.com.gusta.odontosys.msendereco.core.exceptions;

public class CepInvalidoException extends EnderecoException {
    public CepInvalidoException() {
        super();
    }

    public CepInvalidoException(String message) {
        super(message);
    }

    public CepInvalidoException(String message, Throwable cause) {
        super(message, cause);
    }

    public CepInvalidoException(Throwable cause) {
        super(cause);
    }

    protected CepInvalidoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
