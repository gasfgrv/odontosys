package br.com.gusta.odontosys.msendereco.domain.usecases;

import br.com.gusta.odontosys.msendereco.core.exceptions.EnderecoNotFoundException;
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
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith({SpringExtension.class, OutputCaptureExtension.class})
class AtualizarEnderecoTest {

    private static final String CEP = "69318-005";

    private static int NUMERO = 414;
    public static final Endereco ENDERECO = new EnderecoBuilder()
            .setCep(CEP)
            .setNumero(NUMERO)
            .setLogradouro("Rua CC-02")
            .setBairro("Senador Hélio Campos")
            .setCidade("Boa Vista")
            .setUf("RR")
            .build();

    @InjectMocks
    private AtualizarEndereco atualizarEndereco;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Spy
    private MessageSource messageSource;

    @Test
    @DisplayName("Atualizar o endereço")
    void atualizarOEndereco(CapturedOutput output) {
        var codigoMensagem = "atualizando.endereco";

        var argumentos = new String[]{};

        var locale = Locale.getDefault();

        var mensagemLog = "Atualizando endereço na base de dados";

        given(enderecoRepository.consultarEndereco(CEP, NUMERO)).willReturn(ENDERECO);

        given(messageSource.getMessage(codigoMensagem, argumentos, locale)).willReturn(mensagemLog);

        given(enderecoRepository.salvarEndereco(ENDERECO)).willReturn(ENDERECO);

        assertThat(atualizarEndereco.atualizarEndereco(ENDERECO))
                .isNotNull()
                .isInstanceOf(Endereco.class);

        assertThat(output.getOut()).contains(mensagemLog);

        verify(enderecoRepository, times(1)).consultarEndereco(CEP, NUMERO);

        verify(messageSource, times(1)).getMessage(codigoMensagem, argumentos, locale);

        verify(enderecoRepository, times(1)).salvarEndereco(ENDERECO);
    }

    @Test
    @DisplayName("Lançar EnderecoNotFoundException quando o endereço não existe")
    void lancarEnderecoNotFoundExceptionQuandoOEnderecoNaoExiste() {
        var codigoMensagem = "endereco.nao.existe";

        var argumentos = new String[]{};

        var locale = Locale.getDefault();

        var mensagemErro = "Endereço não existe na base de dados";

        given(enderecoRepository.consultarEndereco(CEP, NUMERO)).willReturn(null);

        given(messageSource.getMessage(codigoMensagem, argumentos, locale)).willReturn(mensagemErro);

        assertThatExceptionOfType(EnderecoNotFoundException.class)
                .isThrownBy(() -> atualizarEndereco.atualizarEndereco(ENDERECO))
                .withMessage(mensagemErro);

        verify(enderecoRepository, times(1)).consultarEndereco(CEP, NUMERO);

        verify(messageSource, times(1)).getMessage(codigoMensagem, argumentos, locale);
    }

}