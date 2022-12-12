package br.com.gusta.odontosys.msendereco.data.mappers;

import br.com.gusta.odontosys.msendereco.data.models.dto.response.BuscarEnderecoResponse;
import br.com.gusta.odontosys.msendereco.domain.entities.Endereco;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EnderecoBuscarEnderecoResponseMapper extends GenericMapper<Endereco, BuscarEnderecoResponse> {

    private final ModelMapper mapper;

    @Override
    protected BuscarEnderecoResponse convert(Endereco endereco) {
        return mapper.map(endereco, BuscarEnderecoResponse.class);
    }

}
