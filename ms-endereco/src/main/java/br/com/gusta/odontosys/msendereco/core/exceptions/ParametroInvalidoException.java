package br.com.gusta.odontosys.msendereco.core.exceptions;

public class ParametroInvalidoException extends EnderecoException {
    public ParametroInvalidoException() {
        super();
    }

    public ParametroInvalidoException(String message) {
        super(message);
    }

    public ParametroInvalidoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParametroInvalidoException(Throwable cause) {
        super(cause);
    }

    protected ParametroInvalidoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
