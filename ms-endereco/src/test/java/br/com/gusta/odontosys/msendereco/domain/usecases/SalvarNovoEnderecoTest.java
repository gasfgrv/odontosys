package br.com.gusta.odontosys.msendereco.domain.usecases;

import br.com.gusta.odontosys.msendereco.domain.entities.Endereco;
import br.com.gusta.odontosys.msendereco.domain.repositories.EnderecoRepository;
import br.com.gusta.odontosys.msendereco.utils.builder.EnderecoBuilder;
import java.util.Locale;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class, OutputCaptureExtension.class})
class SalvarNovoEnderecoTest {

    public static final String CEP = "60410-160";

    public static final int NUMERO = 858;

    @InjectMocks
    private SalvarNovoEndereco salvarNovoEndereco;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Spy
    private MessageSource messageSource;

    @Test
    @DisplayName("Salvar um novo endereço")
    void salvarUmNovoEndereco(CapturedOutput output) {
        var codigoMensagem = "salvando.endereco";
        var argumentos = new String[]{};
        var locale = Locale.getDefault();
        var mensagemLog = "Salvando endereço na base de dados";
        var endereco = new EnderecoBuilder()
                .setCep(CEP)
                .setNumero(NUMERO)
                .setLogradouro("Rua Isaie Bóris")
                .setBairro("Montese")
                .setCidade("Fortaleza")
                .setUf("CE")
                .build();

        when(enderecoRepository.consultarEndereco(CEP, NUMERO)).thenReturn(null);
        when(messageSource.getMessage(codigoMensagem, argumentos, locale)).thenReturn(mensagemLog);
        when(enderecoRepository.salvarEndereco(endereco)).thenReturn(endereco);

        var enderecoSalvo = salvarNovoEndereco.salvarNovoEndereco(endereco);

        assertThat(enderecoSalvo).isNotNull().isInstanceOf(Endereco.class);
        assertThat(output.getOut()).contains(mensagemLog);

        verify(enderecoRepository, times(1)).consultarEndereco(CEP, NUMERO);
        verify(messageSource, times(1)).getMessage(codigoMensagem, argumentos, locale);
        verify(enderecoRepository, times(1)).salvarEndereco(endereco);
    }

    @Test
    @DisplayName("Retornar um endereço já existente em vez de salvar um novo")
    void retornarUmEnderecoJaExistenteEmVezDeSalvarUmNovo(CapturedOutput output) {
        var codigoMensagem = "retornando.endereco.existente";
        var argumentos = new String[]{};
        var locale = Locale.getDefault();
        var mensagemLog = "Endereço existente na base de dados, retornando os dados do mesmo";
        var endereco = new EnderecoBuilder()
                .setCep(CEP)
                .setNumero(NUMERO)
                .setLogradouro("Rua Isaie Bóris")
                .setBairro("Montese")
                .setCidade("Fortaleza")
                .setUf("CE")
                .build();

        when(enderecoRepository.consultarEndereco(CEP, NUMERO)).thenReturn(endereco);
        when(messageSource.getMessage(codigoMensagem, argumentos, locale)).thenReturn(mensagemLog);

        var enderecoSalvo = salvarNovoEndereco.salvarNovoEndereco(endereco);

        assertThat(enderecoSalvo).isNotNull().isInstanceOf(Endereco.class);
        assertThat(output.getOut()).contains(mensagemLog);

        verify(enderecoRepository, times(1)).consultarEndereco(CEP, NUMERO);
        verify(messageSource, times(1)).getMessage(codigoMensagem, argumentos, locale);
    }
}