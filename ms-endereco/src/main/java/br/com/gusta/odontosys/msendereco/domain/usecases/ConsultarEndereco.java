package br.com.gusta.odontosys.msendereco.domain.usecases;

import br.com.gusta.odontosys.msendereco.core.exceptions.EnderecoNotFoundException;
import br.com.gusta.odontosys.msendereco.core.utils.MensagemUtils;
import br.com.gusta.odontosys.msendereco.domain.entities.Endereco;
import br.com.gusta.odontosys.msendereco.domain.repositories.EnderecoRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ConsultarEndereco {

    private final EnderecoRepository enderecoRepository;

    private final MessageSource messageSource;

    public Endereco consultarEndereco(String cep, int numero) {

        log.info(MensagemUtils.getMensagem(messageSource, "buscando.base", cep));

        return Optional.ofNullable(enderecoRepository.consultarEndereco(cep, numero))
                .orElseThrow(() -> new EnderecoNotFoundException(
                        MensagemUtils.getMensagem(messageSource, "endereco.nao.encontrado")));
    }
}
