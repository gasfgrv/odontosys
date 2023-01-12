package br.com.gusta.odontosys.msendereco.data.controllers;

import br.com.gusta.odontosys.msendereco.core.exceptions.EnderecoNotFoundException;
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
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class EnderecoControllerAdvice extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

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
        problema.setMensagem(MensagemUtils.getMensagem(messageSource, "campos.invalidos"));
        problema.setCampos(erroCampos);

        return handleExceptionInternal(ex, problema, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers,
                                                                          HttpStatus status,
                                                                          WebRequest request) {
        var nomeParametro = ex.getParameterName();

        var problema = new Problema();
        problema.setStatus(status.value());
        problema.setDataHora(LocalDateTime.now());
        problema.setMensagem(MensagemUtils.getMensagem(messageSource, "parametro.invalido", nomeParametro));

        return handleExceptionInternal(ex, problema, headers, status, request);
    }

}