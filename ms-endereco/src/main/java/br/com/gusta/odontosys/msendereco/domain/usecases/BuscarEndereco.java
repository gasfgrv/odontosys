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
public class BuscarEndereco {

    private final EnderecoRepository enderecoRepository;

    public Endereco buscarEndereco(String cep) {
        log.info("Buscando {} em webservice externo", cep);
        return enderecoRepository.buscarEndereco(cep);
    }

}
