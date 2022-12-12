package br.com.gusta.odontosys.msendereco.data.datasources;

import br.com.gusta.odontosys.msendereco.data.models.entity.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface JpaEnderecoRepository extends JpaRepository<EnderecoEntity, String> {

    @Query("SELECT e FROM EnderecoEntity e WHERE e.cep = :cep and e.numero = :numero")
    Optional<EnderecoEntity> consultarEndereco(@Param("cep") String cep, @Param("numero") int numero);

}
