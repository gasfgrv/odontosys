package br.com.gusta.odontosys.msendereco.data.models.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class EnderecoId implements Serializable {
    private String cep;
    private int numero;
}
