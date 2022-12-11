package br.com.gusta.odontosys.msendereco.data.mappers;

import br.com.gusta.odontosys.msendereco.data.models.dto.response.EnderecoResponse;
import br.com.gusta.odontosys.msendereco.domain.entities.Endereco;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EnderecoResponseMapper extends GenericMapper<Endereco, EnderecoResponse> {

    private final ModelMapper mapper;

    @Override
    protected EnderecoResponse convert(Endereco endereco) {
        return mapper.map(endereco, EnderecoResponse.class);
    }

}
