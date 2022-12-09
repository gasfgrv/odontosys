package br.com.gusta.odontosys.msendereco.data.mappers;

import br.com.gusta.odontosys.msendereco.data.models.dto.ViacepResponse;
import br.com.gusta.odontosys.msendereco.data.models.dto.response.EnderecoResponse;
import br.com.gusta.odontosys.msendereco.domain.entities.Endereco;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EnderecoResponseMapper {

    private final ModelMapper mapper;

    public EnderecoResponse map(Endereco endereco) {
        return mapper.map(endereco, EnderecoResponse.class);
    }

}
