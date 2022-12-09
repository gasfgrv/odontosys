package br.com.gusta.odontosys.msendereco.data.models.dto.response.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Campo {
    private String nome;
    private String mensagem;
}
