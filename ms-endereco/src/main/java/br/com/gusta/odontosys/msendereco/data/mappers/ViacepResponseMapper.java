package br.com.gusta.odontosys.msendereco.data.mappers;

import br.com.gusta.odontosys.msendereco.data.models.dto.ViacepResponse;
import br.com.gusta.odontosys.msendereco.domain.entities.Endereco;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ViacepResponseMapper extends GenericMapper<ViacepResponse, Endereco> {

    private final ModelMapper mapper;

    @Autowired
    public ViacepResponseMapper(ModelMapper mapper) {
        this.mapper = mapper;
        mapper.createTypeMap(ViacepResponse.class, Endereco.class)
                .addMapping(ViacepResponse::getLocalidade, Endereco::setCidade);
    }
    @Override
    protected Endereco convert(ViacepResponse viacepResponse) {
        return mapper.map(viacepResponse, Endereco.class);
    }
}
