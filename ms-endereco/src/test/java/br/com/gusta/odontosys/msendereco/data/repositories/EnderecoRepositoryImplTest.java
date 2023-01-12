package br.com.gusta.odontosys.msendereco.data.repositories;

import br.com.gusta.odontosys.msendereco.data.datasources.CepClient;
import br.com.gusta.odontosys.msendereco.data.datasources.JpaEnderecoRepository;
import br.com.gusta.odontosys.msendereco.data.mappers.EnderecoEntityToEnderecoMapper;
import br.com.gusta.odontosys.msendereco.data.mappers.EnderecoToEnderecoEntityMapper;
import br.com.gusta.odontosys.msendereco.data.mappers.ViacepResponseToEnderecoMapper;
import br.com.gusta.odontosys.msendereco.data.models.entity.EnderecoEntity;
import br.com.gusta.odontosys.msendereco.data.models.entity.EnderecoId;
import br.com.gusta.odontosys.msendereco.domain.entities.Endereco;
import br.com.gusta.odontosys.msendereco.utils.builder.EnderecoBuilder;
import br.com.gusta.odontosys.msendereco.utils.builder.EnderecoEntityBuilder;
import br.com.gusta.odontosys.msendereco.utils.builder.ViacepResponseBuilder;
import java.util.Locale;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class, OutputCaptureExtension.class})
class EnderecoRepositoryImplTest {

    public static final String CEP = "78555-569";

    public static final int NUMERO = 228;

    public static final Endereco ENDERECO = new EnderecoBuilder()
            .setCep(CEP)
            .setNumero(NUMERO)
            .setLogradouro("Rua Guanabara")
            .setBairro("Residencial Ipanema")
            .setCidade("Sinop")
            .setUf("MT")
            .build();

    public static final EnderecoEntity ENTITY = new EnderecoEntityBuilder()
            .setCep(CEP)
            .setNumero(NUMERO)
            .setLogradouro("Rua Guanabara")
            .setBairro("Residencial Ipanema")
            .setCidade("Sinop")
            .setUf("MT")
            .build();

    @Spy
    private MessageSource messageSource;

    @Mock
    private ModelMapper mapper;

    @Mock
    private JpaEnderecoRepository jpaEnderecoRepository;

    @Mock
    private CepClient cepClient;

    @Mock
    private EnderecoEntityToEnderecoMapper enderecoEntityToEnderecoMapper;

    @Mock
    private EnderecoToEnderecoEntityMapper enderecoToEnderecoEntityMapper;

    @Mock
    private ViacepResponseToEnderecoMapper viacepResponseToEnderecoMapper;

    @InjectMocks
    private EnderecoRepositoryImpl enderecoRepository;

    @Test
    @DisplayName("Salvar um endereço na base de dados")
    void salvarUmEnderecoNaBaseDeDados() {
        when(enderecoToEnderecoEntityMapper.toEntity(ENDERECO)).thenReturn(ENTITY);
        when(jpaEnderecoRepository.save(ENTITY)).thenReturn(ENTITY);
        when(enderecoEntityToEnderecoMapper.toModel(ENTITY)).thenReturn(ENDERECO);

        var enderecoSalvo = enderecoRepository.salvarEndereco(ENDERECO);

        assertThat(enderecoSalvo).isNotNull().isInstanceOf(Endereco.class);

        verify(enderecoToEnderecoEntityMapper, times(1)).toEntity(ENDERECO);
        verify(jpaEnderecoRepository, times(1)).save(ENTITY);
        verify(enderecoEntityToEnderecoMapper, times(1)).toModel(ENTITY);
    }

    @Test
    @DisplayName("Buscar um endereço no web service")
    void buscarUmEnderecoNoWebService(CapturedOutput output) {
        var codigoMensagem = "buscando.web.service";
        var argumentos = new String[]{CEP};
        var locale = Locale.getDefault();
        var mensagemLog = "Buscando em: https://viacep.com.br/ws/%s/json".formatted(CEP);
        var viacepResponse = new ViacepResponseBuilder()
                .setCep(CEP)
                .setLogradouro("Rua Guanabara")
                .setBairro("Residencial Ipanema")
                .setLocalidade("Sinop")
                .setUf("MT")
                .build();

        when(messageSource.getMessage(codigoMensagem, argumentos, locale)).thenReturn(mensagemLog);
        when(cepClient.buscarEnderecoPorCep(CEP)).thenReturn(viacepResponse);
        when(viacepResponseToEnderecoMapper.toModel(viacepResponse)).thenReturn(ENDERECO);

        var enderecoBuscado = enderecoRepository.buscarEndereco(CEP);

        assertThat(enderecoBuscado).isNotNull().isInstanceOf(Endereco.class);
        assertThat(output.getOut()).contains(mensagemLog);

        verify(messageSource, times(1)).getMessage(codigoMensagem, argumentos, locale);
        verify(cepClient, times(1)).buscarEnderecoPorCep(CEP);
        verify(viacepResponseToEnderecoMapper, times(1)).toModel(viacepResponse);
    }

    @Test
    @DisplayName("Cosultar um endereço na base de dados passando cep e numero")
    void consultarUmEnderecoNaBaseDeDadosPassandoCepENumero() {
        var enderecoId = new EnderecoId();
        enderecoId.setCep(CEP.replaceAll("-", ""));
        enderecoId.setNumero(NUMERO);

        when(jpaEnderecoRepository.findById(enderecoId)).thenReturn(Optional.of(ENTITY));
        when(enderecoEntityToEnderecoMapper.toModel(ENTITY)).thenReturn(ENDERECO);

        var enderecoConsultado = enderecoRepository.consultarEndereco(CEP, NUMERO);

        assertThat(enderecoConsultado).isNotNull().isInstanceOf(Endereco.class);

        verify(jpaEnderecoRepository, times(1)).findById(enderecoId);
        verify(enderecoEntityToEnderecoMapper, times(1)).toModel(ENTITY);
    }

    @Test
    @DisplayName("Cosultar um endereço na base de dados passando cep e numero retornando nulo")
    void consultarUmEnderecoNaBaseDeDadosPassandoCepENumeroRetornandoNulo() {
        var enderecoId = new EnderecoId();
        enderecoId.setCep(CEP.replaceAll("-", ""));
        enderecoId.setNumero(NUMERO);

        when(jpaEnderecoRepository.findById(enderecoId)).thenReturn(Optional.empty());
        when(enderecoEntityToEnderecoMapper.toModel(ENTITY)).thenReturn(ENDERECO);

        var enderecoConsultado = enderecoRepository.consultarEndereco(CEP, NUMERO);

        assertThat(enderecoConsultado).isNull();

        verify(jpaEnderecoRepository, times(1)).findById(enderecoId);
        verify(enderecoEntityToEnderecoMapper, never()).toModel(ENTITY);
    }

    @Test
    @DisplayName("Deletar um endereço na base de dados passando cep e numero")
    void name() {
        var enderecoId = new EnderecoId();
        enderecoId.setCep(CEP.replaceAll("-", ""));
        enderecoId.setNumero(NUMERO);

        doNothing().when(jpaEnderecoRepository).deleteById(enderecoId);

        assertThatCode(() -> jpaEnderecoRepository.deleteById(enderecoId)).doesNotThrowAnyException();

        verify(jpaEnderecoRepository, times(1)).deleteById(enderecoId);
    }
}