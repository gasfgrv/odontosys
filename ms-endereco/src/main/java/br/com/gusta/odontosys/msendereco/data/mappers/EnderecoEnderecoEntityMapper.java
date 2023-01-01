package br.com.gusta.odontosys.msendereco.data.mappers;

import br.com.gusta.odontosys.msendereco.data.models.entity.EnderecoEntity;
import br.com.gusta.odontosys.msendereco.domain.entities.Endereco;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.DestinationSetter;
import org.modelmapper.spi.SourceGetter;
import org.springframework.stereotype.Component;

@Component
public class EnderecoEnderecoEntityMapper extends GenericMapper<Endereco, EnderecoEntity> {

    public static final SourceGetter<EnderecoEntity> GET_CEP = entity -> entity.getEnderecoId().getCep();

    public static final SourceGetter<EnderecoEntity> GET_NUMERO = entity -> entity.getEnderecoId().getNumero();

    public static final DestinationSetter<Endereco, String> SET_CEP = Endereco::setCep;

    public static final DestinationSetter<Endereco, Integer> SET_NUMERO = Endereco::setNumero;

    private final ModelMapper mapper;

    public EnderecoEnderecoEntityMapper(ModelMapper mapper) {
        this.mapper = mapper;
        mapper.createTypeMap(EnderecoEntity.class, Endereco.class)
                .addMapping(GET_CEP, SET_CEP)
                .addMapping(GET_NUMERO, SET_NUMERO);
    }

    @Override
    protected EnderecoEntity convert(Endereco endereco) {
        return mapper.map(endereco, EnderecoEntity.class);
    }

}
