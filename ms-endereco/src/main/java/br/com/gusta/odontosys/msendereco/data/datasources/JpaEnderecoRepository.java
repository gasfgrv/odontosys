package br.com.gusta.odontosys.msendereco.data.datasources;

import br.com.gusta.odontosys.msendereco.data.models.entity.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaEnderecoRepository extends JpaRepository<EnderecoEntity, String> {
}
