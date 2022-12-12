package br.com.gusta.odontosys.msendereco.data.mappers;

import br.com.gusta.odontosys.msendereco.data.models.dto.input.NovoEnderecoForm;
import br.com.gusta.odontosys.msendereco.domain.entities.Endereco;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NovoEnderecoFormEnderecoMapper extends GenericMapper<NovoEnderecoForm, Endereco> {

    private final ModelMapper mapper;

    @Override
    protected Endereco convert(NovoEnderecoForm form) {
        return mapper.map(form, Endereco.class);
    }
}
