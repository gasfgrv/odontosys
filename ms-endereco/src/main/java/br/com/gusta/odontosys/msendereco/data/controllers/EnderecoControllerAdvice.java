package br.com.gusta.odontosys.msendereco.data.controllers;

import br.com.gusta.odontosys.msendereco.core.exceptions.EnderecoNotFoundException;
import br.com.gusta.odontosys.msendereco.core.utils.MensagemUtils;
import br.com.gusta.odontosys.msendereco.data.models.dto.response.error.Campo;
import br.com.gusta.odontosys.msendereco.data.models.dto.response.error.Problema;
import feign.FeignException;
import java.time.LocalDateTime;
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

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Object> handleFeignException(FeignException ex, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;

        var uri = montarUri(request);

        var problema = Problema.builder()
                .status(status.value())
                .uri(uri)
                .mensagem(MensagemUtils.getMensagem(messageSource, "erro.buscando.web.service"))
                .dataHora(LocalDateTime.now())
                .build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EnderecoNotFoundException.class)
    public ResponseEntity<Object> handleEnderecoNotFoundException(EnderecoNotFoundException ex, WebRequest request) {
        var status = HttpStatus.NOT_FOUND;

        var uri = montarUri(request);

        var problema = Problema.builder()
                .status(status.value())
                .uri(uri)
                .mensagem(ex.getMessage())
                .dataHora(LocalDateTime.now())
                .build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        var erroCampos = ex.getBindingResult().getAllErrors()
                .stream().map(erro -> {
                    var nomeCampo = ((FieldError) erro).getField();
                    var mensagemErro = MensagemUtils.getMensagem(messageSource, erro);
                    return new Campo(nomeCampo, mensagemErro);
                }).toList();

        var problema = Problema.builder()
                .status(status.value())
                .uri(request.getDescription(false).substring(4))
                .dataHora(LocalDateTime.now())
                .mensagem(MensagemUtils.getMensagem(messageSource, "campos.invalidos"))
                .campos(erroCampos)
                .build();

        return handleExceptionInternal(ex, problema, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers,
                                                                          HttpStatus status,
                                                                          WebRequest request) {
        var parametroEmBranco = ex.getParameterName();

        var uri = montarUri(request);

        var problema = Problema.builder()
                .status(status.value())
                .uri(uri)
                .dataHora(LocalDateTime.now())
                .mensagem(MensagemUtils.getMensagem(messageSource, "parametro.invalido", parametroEmBranco))
                .build();

        return handleExceptionInternal(ex, problema, headers, status, request);
    }

    private String montarUri(WebRequest request) {
        var parametros = new StringBuilder();

        request.getParameterMap().forEach(
                (parametro, valor) -> parametros.append("&").append("%s=%s".formatted(parametro, valor[0]))
        );

        return "%s?%s"
                .formatted(request.getDescription(false).substring(4), parametros.toString())
                .replaceFirst("&", "");
    }

}