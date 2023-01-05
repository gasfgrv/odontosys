package br.com.gusta.odontosys.msendereco.domain.usecases;

import br.com.gusta.odontosys.msendereco.core.exceptions.EnderecoNotFoundException;
import br.com.gusta.odontosys.msendereco.core.utils.MensagemUtils;
import br.com.gusta.odontosys.msendereco.domain.entities.Endereco;
import br.com.gusta.odontosys.msendereco.domain.repositories.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtualizarEndereco {

    private final EnderecoRepository enderecoRepository;

    private final MessageSource messageSource;

    public Endereco atualizarEndereco(Endereco endereco) {
        var enderecoExistente = Optional
                .ofNullable(enderecoRepository.consultarEndereco(endereco.getCep(), endereco.getNumero()));

        if (enderecoExistente.isEmpty()) {
            var msg = MensagemUtils.getMensagem(messageSource, "endereco.nao.existe");
            log.error(msg);
            throw new EnderecoNotFoundException(msg);
        }

        log.info(MensagemUtils.getMensagem(messageSource, "atualizando.endereco"));

        return enderecoRepository.salvarEndereco(endereco);
    }

}
