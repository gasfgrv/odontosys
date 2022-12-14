package br.com.gusta.odontosys.msendereco.domain.usecases;

import br.com.gusta.odontosys.msendereco.domain.repositories.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeletarEnderecoExistente {

    private final EnderecoRepository enderecoRepository;

    public void deletarEndereco(String cep) {
        log.info("Deletando o endereço com o CEP: {}", cep);
        enderecoRepository.deletarEndereco(cep);
    }

}
