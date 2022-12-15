package br.com.gusta.odontosys.msendereco.data.models.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class EnderecoId implements Serializable {
    private String cep;
    private int numero;
}
