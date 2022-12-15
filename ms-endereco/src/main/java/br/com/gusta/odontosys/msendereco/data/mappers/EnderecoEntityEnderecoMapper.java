package br.com.gusta.odontosys.msendereco.data.mappers;

import br.com.gusta.odontosys.msendereco.data.models.entity.EnderecoEntity;
import br.com.gusta.odontosys.msendereco.domain.entities.Endereco;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnderecoEntityEnderecoMapper extends GenericMapper<EnderecoEntity, Endereco> {

    private final ModelMapper mapper;

    @Autowired
    public EnderecoEntityEnderecoMapper(ModelMapper mapper) {
        this.mapper = mapper;
        mapper.createTypeMap(Endereco.class, EnderecoEntity.class)
                .<String>addMapping(Endereco::getCep, (entity, cep) -> entity.getId().setCep(cep))
                .<Integer>addMapping(Endereco::getNumero, (entity, numero) -> entity.getId().setNumero(numero));
    }

    @Override
    protected Endereco convert(EnderecoEntity enderecoEntity) {
        return mapper.map(enderecoEntity, Endereco.class);
    }

}
