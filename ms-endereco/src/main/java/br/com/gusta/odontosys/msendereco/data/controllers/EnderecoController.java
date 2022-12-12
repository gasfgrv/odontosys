package br.com.gusta.odontosys.msendereco.data.controllers;

import br.com.gusta.odontosys.msendereco.core.exceptions.CepInvalidoException;
import br.com.gusta.odontosys.msendereco.data.mappers.GenericMapper;
import br.com.gusta.odontosys.msendereco.data.models.dto.input.NovoEnderecoForm;
import br.com.gusta.odontosys.msendereco.data.models.dto.response.BuscarEnderecoResponse;
import br.com.gusta.odontosys.msendereco.data.models.dto.response.EnderecoResponse;
import br.com.gusta.odontosys.msendereco.domain.entities.Endereco;
import br.com.gusta.odontosys.msendereco.domain.usecases.BuscarEndereco;
import br.com.gusta.odontosys.msendereco.domain.usecases.ConsultarEndereco;
import br.com.gusta.odontosys.msendereco.domain.usecases.SalvarNovoEndereco;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping("/endereco")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EnderecoController {

    private final BuscarEndereco buscarEndereco;
    private final ConsultarEndereco consultarEndereco;
    private final SalvarNovoEndereco salvarNovoEndereco;
    private final GenericMapper<Endereco, BuscarEnderecoResponse> enderecoBuscarEnderecoResponseMapper;
    private final GenericMapper<Endereco, EnderecoResponse> enderecoEnderecoResponseMapper;
    private final GenericMapper<NovoEnderecoForm, Endereco> novoEnderecoFormEnderecoMapper;

    @GetMapping("/buscar")
    public ResponseEntity<BuscarEnderecoResponse> buscarEndereco(@RequestParam(required = false) String cep) {
        if (cep == null) {
            log.error("CEP não passado para a busca");
            throw new CepInvalidoException("CEP não passado para a busca");
        }

        var endereco = buscarEndereco.buscarEndereco(cep);

        var response = enderecoBuscarEnderecoResponseMapper.map(endereco);

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

        var response = enderecoEnderecoResponseMapper.map(endereco);

        log.info("Endereço consultado com sucesso");

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<EnderecoResponse> salvarNovoEndereco(@RequestBody @Valid NovoEnderecoForm form) {
        var novoEndereco = novoEnderecoFormEnderecoMapper.map(form);

        var enderecoSalvo = salvarNovoEndereco.salvarNovoEndereco(novoEndereco);

        log.info("Endereço salvo com sucesso");

        var enderecoResponse = enderecoEnderecoResponseMapper.map(enderecoSalvo);

        var uri = URI.create(String.format("/endereco?cep=%s&?numero=%s",
                enderecoSalvo.getCep(),
                enderecoSalvo.getNumero()));

        return ResponseEntity.created(uri).body(enderecoResponse);
    }
}
