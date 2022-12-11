package br.com.gusta.odontosys.msendereco.data.controllers;

import br.com.gusta.odontosys.msendereco.core.exceptions.CepInvalidoException;
import br.com.gusta.odontosys.msendereco.data.mappers.GenericMapper;
import br.com.gusta.odontosys.msendereco.data.models.dto.response.EnderecoResponse;
import br.com.gusta.odontosys.msendereco.domain.entities.Endereco;
import br.com.gusta.odontosys.msendereco.domain.usecases.BuscarEndereco;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/endereco")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EnderecoController {

    private final BuscarEndereco buscarEndereco;
    private final GenericMapper<Endereco, EnderecoResponse> enderecoResponseMapper;

    @GetMapping("/buscar")
    public ResponseEntity<EnderecoResponse> buscarEndereco(@RequestParam(required = false) String cep) {
        if (cep == null) {
            log.error("CEP não passado para a busca");
            throw new CepInvalidoException("CEP não passado para a busca");
        }

        var endereco = buscarEndereco.buscarEndereco(cep);

        var response = enderecoResponseMapper.map(endereco);

        log.info("Busca realizada com sucesso");

        return ResponseEntity.ok(response);
    }

}
