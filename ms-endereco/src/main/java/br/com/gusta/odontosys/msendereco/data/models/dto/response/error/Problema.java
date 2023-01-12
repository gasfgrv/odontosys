package br.com.gusta.odontosys.msendereco.data.models.dto.response.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class Problema {
    private Integer status;
    private String uri;
    private LocalDateTime dataHora;
    private String mensagem;
    private List<Campo> campos;
}
