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
class ConsultarEnderecoTest {

    public static final String CEP = "25936-155";

    public static final int NUMERO = 541;

    @InjectMocks
    private ConsultarEndereco consultarEndereco;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Spy
    private MessageSource messageSource;

    @Test
    @DisplayName("Consultar um endereço a partir do cep e do número")
    void consultarUmEnderecoAPartirDoCepEDoNumero(CapturedOutput output) {
        var endereco = new EnderecoBuilder()
                .setCep(CEP)
                .setNumero(NUMERO)
                .setLogradouro("Alameda Edwirginha")
                .setBairro("Jardim Nazareno")
                .setComplemento("Vila Inhomirim")
                .setCidade("Magé")
                .setUf("RJ")
                .build();

        var codigoMensagem = "buscando.base";

        var argumentos = new String[]{CEP};

        var locale = Locale.getDefault();

        var mensagemLog = "Buscando %s na base de dados".formatted(CEP);

        given(enderecoRepository.consultarEndereco(CEP, NUMERO)).willReturn(endereco);

        given(messageSource.getMessage(codigoMensagem, argumentos, locale)).willReturn(mensagemLog);

        assertThat(consultarEndereco.consultarEndereco(CEP, NUMERO))
                .isNotNull()
                .isInstanceOf(Endereco.class);

        assertThat(output.getOut()).contains(mensagemLog);

        verify(enderecoRepository, times(1)).consultarEndereco(CEP, NUMERO);

        verify(messageSource, times(1)).getMessage(codigoMensagem, argumentos, locale);
    }

    @Test
    @DisplayName("Lançar EnderecoNotFoundException quando não existe endereco")
    void LancaEnderecoNotFoundExceptionQuandoNaoExisteEndereco() {
        var codigoMensagem = "endereco.nao.encontrado";

        var argumentos = new String[]{};

        var locale = Locale.getDefault();

        var mensagemErro = "Endereço não encontrado";

        given(enderecoRepository.consultarEndereco(CEP, NUMERO)).willReturn(null);

        given(messageSource.getMessage(codigoMensagem, argumentos, locale)).willReturn(mensagemErro);

        assertThatExceptionOfType(EnderecoNotFoundException.class)
                .isThrownBy(() -> consultarEndereco.consultarEndereco(CEP, NUMERO))
                .withMessage(mensagemErro);

        verify(enderecoRepository, times(1)).consultarEndereco(CEP, NUMERO);

        verify(messageSource, times(1)).getMessage(codigoMensagem, argumentos, locale);
    }

}