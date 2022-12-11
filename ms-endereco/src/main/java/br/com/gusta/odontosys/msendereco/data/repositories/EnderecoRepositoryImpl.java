package br.com.gusta.odontosys.msendereco.data.repositories;

import br.com.gusta.odontosys.msendereco.data.datasources.CepClient;
import br.com.gusta.odontosys.msendereco.data.datasources.JpaEnderecoRepository;
import br.com.gusta.odontosys.msendereco.data.mappers.GenericMapper;
import br.com.gusta.odontosys.msendereco.data.models.dto.ViacepResponse;
import br.com.gusta.odontosys.msendereco.data.models.entity.EnderecoEntity;
import br.com.gusta.odontosys.msendereco.domain.entities.Endereco;
import br.com.gusta.odontosys.msendereco.domain.repositories.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EnderecoRepositoryImpl implements EnderecoRepository {

    private final JpaEnderecoRepository repository;
    private final CepClient cepClient;
    private final GenericMapper<EnderecoEntity, Endereco> enderecoMapper;
    private final GenericMapper<Endereco, EnderecoEntity> enderecoEntityMapper;
    private final GenericMapper<ViacepResponse, Endereco> viacepResponseMapper;

    @Override
    public Endereco salvarEndereco(Endereco endereco) {
        var entity = enderecoEntityMapper.map(endereco);
        var enderecoSalvo = repository.save(entity);
        return enderecoMapper.map(enderecoSalvo);
    }

    @Override
    public Endereco buscarEndereco(String cep) {
        log.info("Buscando em: https://viacep.com.br/ws/{}/json", cep);
        var enderecoWs = cepClient.buscarEnderecoPorCep(cep);
        return viacepResponseMapper.map(enderecoWs);
    }

    @Override
    public Endereco consultarEndereco(String cep) {
        repository.findById(cep);
        return null;
    }

    @Override
    public void deletarEndereco(String cep) {
        repository.deleteById(cep);
    }
}
