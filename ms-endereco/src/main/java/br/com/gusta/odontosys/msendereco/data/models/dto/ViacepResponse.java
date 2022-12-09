package br.com.gusta.odontosys.msendereco.data.models.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ViacepResponse {
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
}
