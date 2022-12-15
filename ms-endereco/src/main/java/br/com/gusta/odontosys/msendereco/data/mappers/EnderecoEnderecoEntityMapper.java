package br.com.gusta.odontosys.msendereco.data.mappers;

import br.com.gusta.odontosys.msendereco.data.models.entity.EnderecoEntity;
import br.com.gusta.odontosys.msendereco.domain.entities.Endereco;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EnderecoEnderecoEntityMapper extends GenericMapper<Endereco, EnderecoEntity> {

    private final ModelMapper mapper;

    public EnderecoEnderecoEntityMapper(ModelMapper mapper) {
        this.mapper = mapper;
        mapper.createTypeMap(EnderecoEntity.class, Endereco.class)
                .addMapping(entity -> entity.getId().getCep(), Endereco::setCep)
                .addMapping(entity -> entity.getId().getNumero(), Endereco::setNumero);
    }

    @Override
    protected EnderecoEntity convert(Endereco endereco) {
        return mapper.map(endereco, EnderecoEntity.class);
    }

}
