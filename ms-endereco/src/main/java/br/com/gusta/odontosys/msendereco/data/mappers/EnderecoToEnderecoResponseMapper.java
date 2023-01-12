package br.com.gusta.odontosys.msendereco.data.mappers;

import br.com.gusta.odontosys.msendereco.data.models.dto.response.EnderecoResponse;
import br.com.gusta.odontosys.msendereco.domain.entities.Endereco;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component

public class EnderecoToEnderecoResponseMapper {

    private final ModelMapper mapper;

    public EnderecoToEnderecoResponseMapper(ModelMapper mapper) {
        var formataCep = (Converter<String, String>) ctx ->
                "%s-%s".formatted(ctx.getSource().substring(0, 4), ctx.getSource().substring(5));
        this.mapper = mapper;
        this.mapper.createTypeMap(Endereco.class, EnderecoResponse.class)
                .addMappings(map -> map.using(formataCep).map(Endereco::getCep, EnderecoResponse::setCep));
    }

    public EnderecoResponse toResponse(Endereco endereco) {
        return mapper.map(endereco, EnderecoResponse.class);
    }
}
