package br.com.gusta.odontosys.msendereco.data.datasources;

import br.com.gusta.odontosys.msendereco.data.models.entity.EnderecoEntity;
import br.com.gusta.odontosys.msendereco.data.models.entity.EnderecoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface JpaEnderecoRepository extends JpaRepository<EnderecoEntity, EnderecoId> {
}
