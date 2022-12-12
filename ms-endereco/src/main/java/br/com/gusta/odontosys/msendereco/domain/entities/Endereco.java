package br.com.gusta.odontosys.msendereco.domain.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Endereco {
    private String cep;
    private String logradouro;
    private int numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
}
