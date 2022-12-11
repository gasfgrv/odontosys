package br.com.gusta.odontosys.msendereco.data.controllers;

import br.com.gusta.odontosys.msendereco.core.exceptions.CepInvalidoException;
import br.com.gusta.odontosys.msendereco.core.exceptions.EnderecoNotFoundException;
import br.com.gusta.odontosys.msendereco.core.exceptions.MapperException;
import br.com.gusta.odontosys.msendereco.data.models.dto.response.error.Problema;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class EnderecoControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CepInvalidoException.class)
    public ResponseEntity<Object> handleCepInvalidoException(CepInvalidoException ex, WebRequest request) {
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
}
