package br.com.gusta.odontosys.msendereco.data.controllers;

import br.com.gusta.odontosys.msendereco.core.exceptions.CepInvalidoException;
import br.com.gusta.odontosys.msendereco.core.utils.MensagemUtils;
import br.com.gusta.odontosys.msendereco.data.mappers.GenericMapper;
import br.com.gusta.odontosys.msendereco.data.models.dto.input.NovoEnderecoForm;
import br.com.gusta.odontosys.msendereco.data.models.dto.response.BuscarEnderecoResponse;
import br.com.gusta.odontosys.msendereco.data.models.dto.response.EnderecoResponse;
import br.com.gusta.odontosys.msendereco.domain.entities.Endereco;
import br.com.gusta.odontosys.msendereco.domain.usecases.AtualizarEndereco;
import br.com.gusta.odontosys.msendereco.domain.usecases.BuscarEndereco;
import br.com.gusta.odontosys.msendereco.domain.usecases.ConsultarEndereco;
import br.com.gusta.odontosys.msendereco.domain.usecases.DeletarEnderecoExistente;
import br.com.gusta.odontosys.msendereco.domain.usecases.SalvarNovoEndereco;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    private final MessageSource messageSource;
    private final BuscarEndereco buscarEndereco;
    private final ConsultarEndereco consultarEndereco;
    private final SalvarNovoEndereco salvarNovoEndereco;
    private final DeletarEnderecoExistente deletarEnderecoExistente;
    private final AtualizarEndereco atualizarEndereco;
    private final GenericMapper<Endereco, BuscarEnderecoResponse> enderecoBuscarEnderecoResponseMapper;
    private final GenericMapper<Endereco, EnderecoResponse> enderecoEnderecoResponseMapper;
    private final GenericMapper<NovoEnderecoForm, Endereco> novoEnderecoFormEnderecoMapper;

    @GetMapping("/buscar")
    public ResponseEntity<BuscarEnderecoResponse> buscarEndereco(@RequestParam(required = false) String cep) {
        String mensagem = null;
        if (cep == null) {
            mensagem = MensagemUtils.getMensagem(messageSource, "cep.nao.passado");
            log.error(mensagem);
            throw new CepInvalidoException(mensagem);
        }

        var endereco = buscarEndereco.buscarEndereco(cep);
        var response = enderecoBuscarEnderecoResponseMapper.map(endereco);
        log.info(MensagemUtils.getMensagem(messageSource, "busca.sucesso"));
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<EnderecoResponse> consultarEndereco(@RequestParam(required = false) String cep,
                                                              @RequestParam(required = false) int numero) {
        if (Stream.of(cep, numero).anyMatch(Objects::isNull)) {
            var mensagem = MensagemUtils.getMensagem(messageSource, "dados.necessarios.nao.informados");
            log.error(mensagem);
            throw new CepInvalidoException(mensagem);
        }

        var endereco = consultarEndereco.consultarEndereco(cep, numero);
        var response = enderecoEnderecoResponseMapper.map(endereco);
        log.info(MensagemUtils.getMensagem(messageSource, "consulta.sucesso"));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<EnderecoResponse> salvarNovoEndereco(@RequestBody @Valid NovoEnderecoForm form) {
        var novoEndereco = novoEnderecoFormEnderecoMapper.map(form);
        var enderecoSalvo = salvarNovoEndereco.salvarNovoEndereco(novoEndereco);
        log.info(MensagemUtils.getMensagem(messageSource, "cadastro.sucesso"));
        var enderecoResponse = enderecoEnderecoResponseMapper.map(enderecoSalvo);
        var uri = URI.create(String.format("/endereco?cep=%s&?numero=%s",
                enderecoSalvo.getCep(),
                enderecoSalvo.getNumero()));
        return ResponseEntity.created(uri).body(enderecoResponse);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Void> deletarEnderecoExistente(@RequestParam(required = false) String cep,
                                                         @RequestParam(required = false) int numero) {
        if (Stream.of(cep, numero).anyMatch(Objects::isNull)) {
            var mensagem = MensagemUtils.getMensagem(messageSource, "dados.necessarios.nao.informados");
            log.error(mensagem);
            throw new CepInvalidoException(mensagem);
        }
        deletarEnderecoExistente.deletarEndereco(cep, numero);
        log.info(MensagemUtils.getMensagem(messageSource, "remocao.sucesso"));
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<EnderecoResponse> atualizarEndereco(@RequestBody @Valid NovoEnderecoForm form) {
        var endereco = novoEnderecoFormEnderecoMapper.map(form);
        var enderecoAtualizado = atualizarEndereco.atualizarEndereco(endereco);
        log.info(MensagemUtils.getMensagem(messageSource, "remocao.sucesso"));
        var response = enderecoEnderecoResponseMapper.map(enderecoAtualizado);
        return ResponseEntity.ok(response);
    }
}
