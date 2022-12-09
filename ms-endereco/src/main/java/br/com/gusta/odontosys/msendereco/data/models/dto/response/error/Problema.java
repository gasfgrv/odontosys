package br.com.gusta.odontosys.msendereco.data.models.dto.response.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class Problema {
    private Integer status;
    private LocalDateTime dataHora;
    private String mensagem;
    private List<Campo> campos;
}
