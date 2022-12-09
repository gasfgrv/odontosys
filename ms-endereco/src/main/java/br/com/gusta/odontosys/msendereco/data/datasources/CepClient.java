package br.com.gusta.odontosys.msendereco.data.datasources;

import br.com.gusta.odontosys.msendereco.data.models.dto.ViacepResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(url= "https://viacep.com.br/ws/" , name = "viacep")
public interface CepClient {

    @GetMapping("{cep}/json")
    ViacepResponse buscarEnderecoPorCep(@PathVariable("cep") String cep);

}
