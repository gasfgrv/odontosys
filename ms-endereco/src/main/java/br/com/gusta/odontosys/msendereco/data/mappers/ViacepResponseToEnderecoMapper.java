package br.com.gusta.odontosys.msendereco.data.mappers;

import br.com.gusta.odontosys.msendereco.data.models.dto.ViacepResponse;
import br.com.gusta.odontosys.msendereco.domain.entities.Endereco;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ViacepResponseToEnderecoMapper {

    private final ModelMapper mapper;

    public ViacepResponseToEnderecoMapper(ModelMapper mapper) {
        this.mapper = mapper;
        mapper.createTypeMap(ViacepResponse.class, Endereco.class)
                .addMapping(ViacepResponse::getLocalidade, Endereco::setCidade);
    }

    public Endereco toModel(ViacepResponse viacepResponse) {
        return mapper.map(viacepResponse, Endereco.class);
    }
}
