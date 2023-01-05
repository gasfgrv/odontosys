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

    @InjectMocks
    private ConsultarEndereco consultarEndereco;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Spy
    private MessageSource messageSource;

    @Test
    @DisplayName("Consultar um endereço a partir do cep e do número")
    void consultarUmEnderecoAPartirDoCepEDoNumero(CapturedOutput output) {
        var cep = "25936-155";
        var numero = 541;

        var endereco = new EnderecoBuilder()
                .setCep(cep)
                .setNumero(numero)
                .setLogradouro("Alameda Edwirginha")
                .setBairro("Jardim Nazareno")
                .setComplemento("Vila Inhomirim")
                .setCidade("Magé")
                .setUf("RJ")
                .build();

        var mensagemLog = "Buscando %s na base de dados".formatted(cep);

        given(enderecoRepository.consultarEndereco(cep, numero)).willReturn(endereco);

        given(messageSource.getMessage("buscando.base", new String[]{cep}, Locale.getDefault()))
                .willReturn(mensagemLog);

        assertThat(consultarEndereco.consultarEndereco(cep, numero))
                .isNotNull()
                .isInstanceOf(Endereco.class);

        assertThat(output.getOut()).contains(mensagemLog);

        verify(enderecoRepository, times(1)).consultarEndereco(cep, numero);

        verify(messageSource, times(1))
                .getMessage("buscando.base", new String[]{cep}, Locale.getDefault());
    }

    @Test
    @DisplayName("Lançar EnderecoNotFoundException quando não existe endereco")
    void LancaEnderecoNotFoundExceptionQuandoNaoExisteEndereco() {
        var cep = "25936-155";
        var numero = 541;
        var mensagemErro = "Endereço não encontrado";

        given(enderecoRepository.consultarEndereco(cep, numero)).willReturn(null);

        given(messageSource.getMessage("endereco.nao.encontrado", new String[]{}, Locale.getDefault()))
                .willReturn(mensagemErro);

        assertThatExceptionOfType(EnderecoNotFoundException.class)
                .isThrownBy(() -> consultarEndereco.consultarEndereco(cep, numero))
                .withMessage(mensagemErro);

        verify(enderecoRepository, times(1)).consultarEndereco(cep, numero);

        verify(messageSource, times(1))
                .getMessage("endereco.nao.encontrado", new String[]{}, Locale.getDefault());
    }

}