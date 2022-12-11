package br.com.gusta.odontosys.msendereco.data.models.dto.response;

import br.com.gusta.odontosys.msendereco.domain.entities.Endereco;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EnderecoResponse {
    private Endereco endereco;
}
