package br.com.gusta.odontosys.msendereco.data.mappers;

import br.com.gusta.odontosys.msendereco.data.models.entity.EnderecoEntity;
import br.com.gusta.odontosys.msendereco.domain.entities.Endereco;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.DestinationSetter;
import org.modelmapper.spi.SourceGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnderecoEntityEnderecoMapper extends GenericMapper<EnderecoEntity, Endereco> {

    public static final SourceGetter<Endereco> GET_CEP = Endereco::getCep;

    public static final SourceGetter<Endereco> GET_NUMERO = Endereco::getNumero;

    public static final DestinationSetter<EnderecoEntity, String> SET_CEP = (entity, cep) -> entity.getEnderecoId().setCep(cep);

    public static final DestinationSetter<EnderecoEntity, Integer> SET_NUMERO = (entity, numero) -> entity.getEnderecoId().setNumero(numero);

    private final ModelMapper mapper;

    @Autowired
    public EnderecoEntityEnderecoMapper(ModelMapper mapper) {
        this.mapper = mapper;
        mapper.createTypeMap(Endereco.class, EnderecoEntity.class)
                .addMapping(GET_CEP, SET_CEP)
                .addMapping(GET_NUMERO, SET_NUMERO);
    }

    @Override
    protected Endereco convert(EnderecoEntity enderecoEntity) {
        return mapper.map(enderecoEntity, Endereco.class);
    }

}
