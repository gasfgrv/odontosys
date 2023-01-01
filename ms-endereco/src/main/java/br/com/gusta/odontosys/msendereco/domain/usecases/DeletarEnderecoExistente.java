package br.com.gusta.odontosys.msendereco.domain.usecases;

import br.com.gusta.odontosys.msendereco.core.exceptions.EnderecoNotFoundException;
import br.com.gusta.odontosys.msendereco.core.utils.MensagemUtils;
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
public class DeletarEnderecoExistente {

    private final EnderecoRepository enderecoRepository;

    private final MessageSource messageSource;

    public void deletarEndereco(String cep, int numero) {
        var enderecoExistente = Optional.ofNullable(enderecoRepository.consultarEndereco(cep, numero));

        if (enderecoExistente.isEmpty()) {
            var msg = MensagemUtils.getMensagem(messageSource, "endereco.nao.existe");
            log.error(msg);
            throw new EnderecoNotFoundException(msg);
        }

        log.info(MensagemUtils.getMensagem(messageSource, "deletando.endereco", cep, Integer.toString(numero)));

        enderecoRepository.deletarEndereco(cep, numero);
    }

}
