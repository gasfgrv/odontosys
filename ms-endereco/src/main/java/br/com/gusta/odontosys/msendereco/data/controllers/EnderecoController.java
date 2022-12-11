package br.com.gusta.odontosys.msendereco.data.controllers;

import br.com.gusta.odontosys.msendereco.core.exceptions.CepInvalidoException;
import br.com.gusta.odontosys.msendereco.data.mappers.GenericMapper;
import br.com.gusta.odontosys.msendereco.data.models.dto.response.BuscarEnderecoResponse;
import br.com.gusta.odontosys.msendereco.data.models.dto.response.EnderecoResponse;
import br.com.gusta.odontosys.msendereco.domain.entities.Endereco;
import br.com.gusta.odontosys.msendereco.domain.usecases.BuscarEndereco;
import br.com.gusta.odontosys.msendereco.domain.usecases.ConsultarEndereco;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping("/endereco")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EnderecoController {

    private final BuscarEndereco buscarEndereco;
    private final ConsultarEndereco consultarEndereco;
    private final GenericMapper<Endereco, BuscarEnderecoResponse> buscarEnderecoResponseMapper;
    private final GenericMapper<Endereco, EnderecoResponse> enderecoResponseMapper;

    @GetMapping("/buscar")
    public ResponseEntity<BuscarEnderecoResponse> buscarEndereco(@RequestParam(required = false) String cep) {
        if (cep == null) {
            log.error("CEP não passado para a busca");
            throw new CepInvalidoException("CEP não passado para a busca");
        }

        var endereco = buscarEndereco.buscarEndereco(cep);

        var response = buscarEnderecoResponseMapper.map(endereco);

        log.info("Busca realizada com sucesso");

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<EnderecoResponse> consultarEndereco(@RequestParam(required = false) String cep,
                                                                    @RequestParam(required = false) int numero) {
        if (Stream.of(cep, numero).anyMatch(Objects::isNull)) {
            log.error("Dados necessários não foram passados para a busca, impossível de localizar");
            throw new CepInvalidoException("Dados necessários não foram passados para a busca, impossível de localizar");
        }

        var endereco = consultarEndereco.consultarEndereco(cep, numero);

        var response = enderecoResponseMapper.map(endereco);

        log.info("Endereço consultado com sucesso");

        return ResponseEntity.ok(response);
    }

}
