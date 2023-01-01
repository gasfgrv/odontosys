package br.com.gusta.odontosys.msendereco.data.repositories;

import br.com.gusta.odontosys.msendereco.core.utils.MensagemUtils;
import br.com.gusta.odontosys.msendereco.data.datasources.CepClient;
import br.com.gusta.odontosys.msendereco.data.datasources.JpaEnderecoRepository;
import br.com.gusta.odontosys.msendereco.data.mappers.GenericMapper;
import br.com.gusta.odontosys.msendereco.data.models.dto.ViacepResponse;
import br.com.gusta.odontosys.msendereco.data.models.entity.EnderecoEntity;
import br.com.gusta.odontosys.msendereco.data.models.entity.EnderecoId;
import br.com.gusta.odontosys.msendereco.domain.entities.Endereco;
import br.com.gusta.odontosys.msendereco.domain.repositories.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EnderecoRepositoryImpl implements EnderecoRepository {

    private final MessageSource messageSource;
    private final JpaEnderecoRepository repository;
    private final CepClient cepClient;
    private final GenericMapper<EnderecoEntity, Endereco> enderecoEntityEnderecoMapper;
    private final GenericMapper<Endereco, EnderecoEntity> enderecoEnderecoEntityMapper;
    private final GenericMapper<ViacepResponse, Endereco> viacepResponseEnderecoMapper;

    @Override
    @CacheEvict(value = "enderecoDatabase", allEntries = true)
    public Endereco salvarEndereco(Endereco endereco) {
        var entity = enderecoEnderecoEntityMapper.map(endereco);
        var enderecoSalvo = repository.save(entity);
        return enderecoEntityEnderecoMapper.map(enderecoSalvo);
    }

    @Override
    @Cacheable("enderecoWebService")
    public Endereco buscarEndereco(String cep) {
        log.info(MensagemUtils.getMensagem(messageSource, "buscando.web.service", cep));

        var enderecoWs = cepClient.buscarEnderecoPorCep(cep);

        return viacepResponseEnderecoMapper.map(enderecoWs);
    }

    @Override
    @Cacheable("enderecoDatabase")
    public Endereco consultarEndereco(String cep, int numero) {
        var enderecoId = new EnderecoId();
        enderecoId.setCep(cep);
        enderecoId.setNumero(numero);

        var enderecoEntity = repository.findById(enderecoId);

        return enderecoEntity.map(enderecoEntityEnderecoMapper::map).orElse(null);
    }

    @Override
    @CacheEvict(value = "enderecoDatabase", allEntries = true)
    public void deletarEndereco(String cep, int numero) {
        var enderecoId = new EnderecoId();
        enderecoId.setCep(cep);
        enderecoId.setNumero(numero);
        repository.deleteById(enderecoId);
    }
}
