package br.com.gusta.odontosys.msendereco.data.mappers;

import br.com.gusta.odontosys.msendereco.data.models.dto.response.EnderecoResponse;
import br.com.gusta.odontosys.msendereco.domain.entities.Endereco;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.DestinationSetter;
import org.modelmapper.spi.SourceGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnderecoResponseMapper extends GenericMapper<Endereco, EnderecoResponse> {

    public static final SourceGetter<Endereco> GET_CEP = Endereco::getCep;
    public static final SourceGetter<Endereco> GET_LOGRADOURO = Endereco::getLogradouro;
    public static final SourceGetter<Endereco> GET_NUMERO = Endereco::getNumero;
    public static final SourceGetter<Endereco> GET_COMPLEMENTO = Endereco::getComplemento;
    public static final SourceGetter<Endereco> GET_BAIRRO = Endereco::getBairro;
    public static final SourceGetter<Endereco> GET_CIDADE = Endereco::getCidade;
    public static final SourceGetter<Endereco> GET_UF = Endereco::getUf;
    public static final DestinationSetter<EnderecoResponse, String> SET_CEP = (enderecoResponse, cep) ->
            enderecoResponse.getEndereco().setCep(cep);
    public static final DestinationSetter<EnderecoResponse, String> SET_LOGRADOURO = (enderecoResponse, logradouro) ->
            enderecoResponse.getEndereco().setLogradouro(logradouro);
    public static final DestinationSetter<EnderecoResponse, String> SET_NUMERO = (enderecoResponse, numero) ->
            enderecoResponse.getEndereco().setNumero(numero);
    public static final DestinationSetter<EnderecoResponse, String> SET_COMPLEMENTO = (enderecoResponse, complemento) ->
            enderecoResponse.getEndereco().setComplemento(complemento);
    public static final DestinationSetter<EnderecoResponse, String> SET_BAIRRO = (enderecoResponse, bairro) ->
            enderecoResponse.getEndereco().setBairro(bairro);
    public static final DestinationSetter<EnderecoResponse, String> SET_CIDADE = (enderecoResponse, cidade) ->
            enderecoResponse.getEndereco().setCidade(cidade);
    public static final DestinationSetter<EnderecoResponse, String> SET_UF = (enderecoResponse, uf) ->
            enderecoResponse.getEndereco().setUf(uf);
    private final ModelMapper mapper;

    @Autowired
    public EnderecoResponseMapper(ModelMapper mapper) {
        this.mapper = mapper;
        mapper.createTypeMap(Endereco.class, EnderecoResponse.class)
                .addMapping(GET_CEP, SET_CEP)
                .addMapping(GET_LOGRADOURO, SET_LOGRADOURO)
                .addMapping(GET_NUMERO, SET_NUMERO)
                .addMapping(GET_COMPLEMENTO, SET_COMPLEMENTO)
                .addMapping(GET_BAIRRO, SET_BAIRRO)
                .addMapping(GET_CIDADE, SET_CIDADE)
                .addMapping(GET_UF, SET_UF);

    }

    @Override
    protected EnderecoResponse convert(Endereco endereco) {
        return mapper.map(endereco, EnderecoResponse.class);
    }
}
