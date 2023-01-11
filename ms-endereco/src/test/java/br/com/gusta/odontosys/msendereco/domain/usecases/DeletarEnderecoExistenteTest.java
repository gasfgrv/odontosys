package br.com.gusta.odontosys.msendereco.domain.usecases;

import br.com.gusta.odontosys.msendereco.core.exceptions.EnderecoNotFoundException;
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
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class, OutputCaptureExtension.class})
class DeletarEnderecoExistenteTest {

    public static final String CEP = "58020-386";

    public static final int NUMERO = 700;

    @InjectMocks
    private DeletarEnderecoExistente deletarEnderecoExistente;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Spy
    private MessageSource messageSource;

    @Test
    @DisplayName("Deletar endereço passando cep e numero")
    void deletarEnderecoPassandoCepENumero(CapturedOutput output) {
        var codigoMensagem = "deletando.endereco";
        var argumentos = new String[]{CEP, String.valueOf(NUMERO)};
        var locale = Locale.getDefault();
        var mensagemLog = "Deletando o endereço com o CEP: %s e número: %s".formatted(CEP, NUMERO);
        var endereco = new EnderecoBuilder()
                .setCep(CEP)
                .setNumero(NUMERO)
                .setLogradouro("Rua Dirce Gomes da Silveira")
                .setBairro("Roger")
                .setCidade("João Pessoa")
                .setUf("PB")
                .build();

        when(enderecoRepository.consultarEndereco(CEP, NUMERO)).thenReturn(endereco);
        when(messageSource.getMessage(codigoMensagem, argumentos, locale)).thenReturn(mensagemLog);
        doNothing().when(enderecoRepository).deletarEndereco(CEP, NUMERO);

        assertThatCode(() -> deletarEnderecoExistente.deletarEndereco(CEP, NUMERO)).doesNotThrowAnyException();
        assertThat(output.getOut()).contains(mensagemLog);

        verify(enderecoRepository, times(1)).deletarEndereco(CEP, NUMERO);
        verify(messageSource, times(1)).getMessage(codigoMensagem, argumentos, locale);
    }

    @Test
    @DisplayName("Lançar EnderecoNotFoundException quando o endereço não existe")
    void lancarEnderecoNotFoundExceptionQuandoOEnderecoNaoExiste() {
        var codigoMensagem = "endereco.nao.existe";
        var argumentos = new String[]{};
        var locale = Locale.getDefault();
        var mensagemErro = "Endereço não existe na base de dados";

        when(enderecoRepository.consultarEndereco(CEP, NUMERO)).thenReturn(null);
        when(messageSource.getMessage(codigoMensagem, argumentos, locale)).thenReturn(mensagemErro);

        assertThatExceptionOfType(EnderecoNotFoundException.class)
                .isThrownBy(() -> deletarEnderecoExistente.deletarEndereco(CEP, NUMERO))
                .withMessage(mensagemErro);

        verify(enderecoRepository, times(1)).consultarEndereco(CEP, NUMERO);
        verify(messageSource, times(1)).getMessage(codigoMensagem, argumentos, locale);
    }

}