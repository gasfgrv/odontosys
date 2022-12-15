package br.com.gusta.odontosys.msendereco.domain.usecases;

import br.com.gusta.odontosys.msendereco.domain.entities.Endereco;
import br.com.gusta.odontosys.msendereco.domain.repositories.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SalvarNovoEndereco {

    private final EnderecoRepository enderecoRepository;
    private final ConsultarEndereco consultarEndereco;

    public Endereco salvarNovoEndereco(Endereco endereco) {
        var enderecoExistente = Optional
                .ofNullable(consultarEndereco.consultarEndereco(endereco.getCep(), endereco.getNumero()));

        if (enderecoExistente.isPresent()) {
            log.info("Endereço existente na base de dados, retornando os dados do mesmo.");
            return enderecoExistente.get();
        }

        log.info("Salvando endereço na base de dados");
        return enderecoRepository.salvarEndereco(endereco);
    }
}
