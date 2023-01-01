package br.com.gusta.odontosys.msendereco.data.controllers;

import br.com.gusta.odontosys.msendereco.core.exceptions.EnderecoNotFoundException;
import br.com.gusta.odontosys.msendereco.core.exceptions.MapperException;
import br.com.gusta.odontosys.msendereco.core.exceptions.ParametroInvalidoException;
import br.com.gusta.odontosys.msendereco.core.utils.MensagemUtils;
import br.com.gusta.odontosys.msendereco.data.models.dto.response.error.Campo;
import br.com.gusta.odontosys.msendereco.data.models.dto.response.error.Problema;
import java.time.LocalDateTime;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class EnderecoControllerAdvice extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(ParametroInvalidoException.class)
    public ResponseEntity<Object> handleCepInvalidoException(ParametroInvalidoException ex, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;

        var erro = new Problema();
        erro.setStatus(status.value());
        erro.setDataHora(LocalDateTime.now());
        erro.setMensagem(ex.getMessage());

        return handleExceptionInternal(ex, erro, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(MapperException.class)
    public ResponseEntity<Object> handleMapperException(MapperException ex, WebRequest request) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;

        var erro = new Problema();
        erro.setStatus(status.value());
        erro.setDataHora(LocalDateTime.now());
        erro.setMensagem(ex.getMessage());

        return handleExceptionInternal(ex, erro, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EnderecoNotFoundException.class)
    public ResponseEntity<Object> handleEnderecoNotFoundException(EnderecoNotFoundException ex, WebRequest request) {
        var status = HttpStatus.NOT_FOUND;

        var erro = new Problema();
        erro.setStatus(status.value());
        erro.setDataHora(LocalDateTime.now());
        erro.setMensagem(ex.getMessage());

        return handleExceptionInternal(ex, erro, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        var erroCampos = new ArrayList<Campo>();

        ex.getBindingResult().getAllErrors().forEach(erro -> {
            var nomeCampo = ((FieldError) erro).getField();
            var mensagemErro = MensagemUtils.getMensagem(messageSource, erro);
            erroCampos.add(new Campo(nomeCampo, mensagemErro));
        });

        var problema = new Problema();
        problema.setStatus(status.value());
        problema.setDataHora(LocalDateTime.now());
        problema.setMensagem("One or more fields are invalid. Fill in correctly and try again.");
        problema.setCampos(erroCampos);

        return handleExceptionInternal(ex, problema, headers, status, request);
    }
}
