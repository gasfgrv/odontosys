package br.com.gusta.odontosys.msendereco.data.mappers;

import br.com.gusta.odontosys.msendereco.data.models.dto.response.BuscarEnderecoResponse;
import br.com.gusta.odontosys.msendereco.domain.entities.Endereco;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnderecoToBuscarEnderecoResponseMapper {

    private final ModelMapper mapper;

    public BuscarEnderecoResponse toResponse(Endereco endereco) {
        return mapper.map(endereco, BuscarEnderecoResponse.class);
    }

}
