package br.com.gusta.odontosys.msendereco.domain.usecases;

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
public class SalvarNovoEndereco {

    private final EnderecoRepository enderecoRepository;
    private final MessageSource messageSource;

    public Endereco salvarNovoEndereco(Endereco endereco) {
        var enderecoExistente = Optional.ofNullable(
                enderecoRepository.consultarEndereco(endereco.getCep(), endereco.getNumero()));

        if (enderecoExistente.isPresent()) {
            log.info(MensagemUtils.getMensagem(messageSource, "retornando.endereco.existente"));
            return enderecoExistente.get();
        }

        log.info(MensagemUtils.getMensagem(messageSource, "salvando.endereco"));

        return enderecoRepository.salvarEndereco(endereco);
    }
}
