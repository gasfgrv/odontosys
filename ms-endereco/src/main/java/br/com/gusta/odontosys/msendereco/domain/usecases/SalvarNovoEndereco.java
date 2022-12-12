package br.com.gusta.odontosys.msendereco.domain.usecases;

import br.com.gusta.odontosys.msendereco.domain.entities.Endereco;
import br.com.gusta.odontosys.msendereco.domain.repositories.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SalvarNovoEndereco {

    private final EnderecoRepository enderecoRepository;

    public Endereco salvarNovoEndereco(Endereco endereco) {
        log.info("Salvando endereço na base de dados");

        return enderecoRepository.salvarEndereco(endereco);
    }
}
