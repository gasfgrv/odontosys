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
class BuscarEnderecoTest {

    @InjectMocks
    private BuscarEndereco buscarEndereco;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Spy
    private MessageSource messageSource;

    @Test
    @DisplayName("Buscar um endereco a partir do cep")
    void buscarUmEnderecoAPartirDoCep(CapturedOutput output) {
        var cep = "01001-000";
        var codigoMensagem = "buscando.webservice.externo";
        var argumentos = new String[]{cep};
        var locale = Locale.getDefault();
        var mensagemLog = "Buscando %s em webservice externo".formatted(cep);
        var endereco = new EnderecoBuilder()
                .setCep(cep)
                .setLogradouro("Praça da Sé")
                .setComplemento("lado ímpar")
                .setBairro("Sé")
                .setCidade("São Paulo")
                .setUf("SP")
                .build();

        when(enderecoRepository.buscarEndereco(cep)).thenReturn(endereco);
        when(messageSource.getMessage(codigoMensagem, argumentos, locale)).thenReturn(mensagemLog);

        var enderecoBuscado = buscarEndereco.buscarEndereco(cep);

        assertThat(enderecoBuscado).isNotNull().isInstanceOf(Endereco.class);
        assertThat(output.getOut()).contains(mensagemLog);

        verify(enderecoRepository, times(1)).buscarEndereco(cep);
        verify(messageSource, times(1)).getMessage(codigoMensagem, argumentos, locale);
    }
}