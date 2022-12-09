package br.com.gusta.odontosys.msendereco.data.repositories;

import br.com.gusta.odontosys.msendereco.data.datasources.CepClient;
import br.com.gusta.odontosys.msendereco.data.datasources.JpaEnderecoRepository;
import br.com.gusta.odontosys.msendereco.data.mappers.EnderecoMapper;
import br.com.gusta.odontosys.msendereco.domain.entities.Endereco;
import br.com.gusta.odontosys.msendereco.domain.repositories.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class EnderecoRepositoryImpl implements EnderecoRepository {

    private final JpaEnderecoRepository repository;
    private final CepClient cepClient;
    private final EnderecoMapper mapper;

    @Override
    public Endereco salvarEndereco(Endereco endereco) {
        return null;
    }

    @Override
    public Endereco buscarEndereco(String cep) {
        log.info("Buscando em: https://viacep.com.br/ws/{}/json", cep);
        var enderecoWs = cepClient.buscarEnderecoPorCep(cep);
        return mapper.map(enderecoWs);
    }

    @Override
    public Endereco consultarEndereco(String cep) {
        return null;
    }

    @Override
    public Endereco deletarEndereco(String cep) {
        return null;
    }
}
