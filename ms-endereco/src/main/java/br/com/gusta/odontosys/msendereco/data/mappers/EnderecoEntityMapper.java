package br.com.gusta.odontosys.msendereco.data.mappers;

import br.com.gusta.odontosys.msendereco.data.models.entity.EnderecoEntity;
import br.com.gusta.odontosys.msendereco.domain.entities.Endereco;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EnderecoEntityMapper extends GenericMapper<Endereco, EnderecoEntity> {

    private final ModelMapper mapper;

    @Override
    protected EnderecoEntity convert(Endereco endereco) {
        return mapper.map(endereco, EnderecoEntity.class);
    }

}
