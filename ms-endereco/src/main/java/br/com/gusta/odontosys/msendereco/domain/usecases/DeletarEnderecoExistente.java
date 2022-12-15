package br.com.gusta.odontosys.msendereco.domain.usecases;

import br.com.gusta.odontosys.msendereco.core.exceptions.EnderecoNotFoundException;
import br.com.gusta.odontosys.msendereco.domain.repositories.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeletarEnderecoExistente {

    private final EnderecoRepository enderecoRepository;
    private final ConsultarEndereco consultarEndereco;

    public void deletarEndereco(String cep, int numero) {
        var enderecoExistente = Optional
                .ofNullable(consultarEndereco.consultarEndereco(cep, numero));

        if (enderecoExistente.isEmpty()) {
            var msg = "Endereço não existe na base de dados";
            log.error(msg);
            throw new EnderecoNotFoundException(msg);
        }

        log.info("Deletando o endereço com o CEP: {}, {}", cep, numero);
        enderecoRepository.deletarEndereco(cep, numero);
    }

}
