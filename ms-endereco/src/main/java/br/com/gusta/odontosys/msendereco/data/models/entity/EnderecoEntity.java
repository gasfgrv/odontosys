package br.com.gusta.odontosys.msendereco.data.models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "endereco")
@Getter
@Setter
public class EnderecoEntity {
    @EmbeddedId
    private EnderecoId id;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
}
