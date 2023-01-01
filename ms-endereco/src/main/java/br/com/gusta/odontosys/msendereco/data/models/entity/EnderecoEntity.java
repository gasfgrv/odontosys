package br.com.gusta.odontosys.msendereco.data.models.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "endereco")
@Getter
@Setter
public class EnderecoEntity {
    @EmbeddedId
    private EnderecoId enderecoId;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
}
