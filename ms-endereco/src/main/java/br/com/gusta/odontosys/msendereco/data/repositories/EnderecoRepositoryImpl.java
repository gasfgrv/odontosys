package br.com.gusta.odontosys.msendereco.data.repositories;

import br.com.gusta.odontosys.msendereco.core.utils.MensagemUtils;
import br.com.gusta.odontosys.msendereco.data.datasources.CepClient;
import br.com.gusta.odontosys.msendereco.data.datasources.JpaEnderecoRepository;
import br.com.gusta.odontosys.msendereco.data.mappers.EnderecoToEnderecoEntityMapper;
import br.com.gusta.odontosys.msendereco.data.mappers.EnderecoEntityToEnderecoMapper;
import br.com.gusta.odontosys.msendereco.data.mappers.ViacepResponseToEnderecoMapper;
import br.com.gusta.odontosys.msendereco.data.models.entity.EnderecoId;
import br.com.gusta.odontosys.msendereco.domain.entities.Endereco;
import br.com.gusta.odontosys.msendereco.domain.repositories.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class EnderecoRepositoryImpl implements EnderecoRepository {

    private final MessageSource messageSource;
    private final JpaEnderecoRepository repository;
    private final CepClient cepClient;
    private final EnderecoEntityToEnderecoMapper enderecoEntityToEnderecoMapper;

    private final EnderecoToEnderecoEntityMapper enderecoToEnderecoEntityMapper;

    private final ViacepResponseToEnderecoMapper viacepResponseToEnderecoMapper;

    @Override
    @CacheEvict(value = "enderecoDatabase", allEntries = true)
    public Endereco salvarEndereco(Endereco endereco) {
        endereco.setCep(formataCep(endereco.getCep()));

        var entity = enderecoToEnderecoEntityMapper.toEntity(endereco);

        var enderecoSalvo = repository.save(entity);

        return enderecoEntityToEnderecoMapper.toModel(enderecoSalvo);
    }

    @Override
    @Cacheable("enderecoWebService")
    public Endereco buscarEndereco(String cep) {
        log.info(MensagemUtils.getMensagem(messageSource, "buscando.web.service", cep));

        var enderecoWs = cepClient.buscarEnderecoPorCep(cep);

        return viacepResponseToEnderecoMapper.toModel(enderecoWs);
    }

    @Override
    @Cacheable("enderecoDatabase")
    public Endereco consultarEndereco(String cep, int numero) {
        var enderecoId = new EnderecoId();
        enderecoId.setCep(formataCep(cep));
        enderecoId.setNumero(numero);

        var enderecoEntity = repository.findById(enderecoId);

        return enderecoEntity.map(enderecoEntityToEnderecoMapper::toModel).orElse(null);
    }

    @Override
    @CacheEvict(value = "enderecoDatabase", allEntries = true)
    public void deletarEndereco(String cep, int numero) {
        var enderecoId = new EnderecoId();
        enderecoId.setCep(formataCep(cep));
        enderecoId.setNumero(numero);
        repository.deleteById(enderecoId);
    }

    private String formataCep(String cep) {
        if (cep.matches("\\d{5}-\\d{3}")) {
            return cep.replace("-", "");
        }

        return cep;
    }
}
