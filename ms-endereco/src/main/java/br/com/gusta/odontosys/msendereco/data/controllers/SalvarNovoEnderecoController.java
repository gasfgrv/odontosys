package br.com.gusta.odontosys.msendereco.data.controllers;

import br.com.gusta.odontosys.msendereco.core.utils.MensagemUtils;
import br.com.gusta.odontosys.msendereco.data.mappers.EnderecoToEnderecoResponseMapper;
import br.com.gusta.odontosys.msendereco.data.mappers.NovoEnderecoFormToEnderecoMapper;
import br.com.gusta.odontosys.msendereco.data.models.dto.input.NovoEnderecoForm;
import br.com.gusta.odontosys.msendereco.data.models.dto.response.EnderecoResponse;
import br.com.gusta.odontosys.msendereco.domain.usecases.SalvarNovoEndereco;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/endereco")
public class SalvarNovoEnderecoController {

    private final MessageSource messageSource;
    private final SalvarNovoEndereco salvarNovoEndereco;
    private final NovoEnderecoFormToEnderecoMapper novoEnderecoFormToEnderecoMapper;
    private final EnderecoToEnderecoResponseMapper enderecoToEnderecoResponseMapper;

    @PostMapping
    @Transactional
    public ResponseEntity<EnderecoResponse> salvarNovoEndereco(@RequestBody @Valid NovoEnderecoForm form) {
        var novoEndereco = novoEnderecoFormToEnderecoMapper.toModel(form);

        var enderecoSalvo = salvarNovoEndereco.salvarNovoEndereco(novoEndereco);

        log.info(MensagemUtils.getMensagem(messageSource, "cadastro.sucesso"));

        var enderecoResponse = enderecoToEnderecoResponseMapper.toResponse(enderecoSalvo);

        var uri = URI.create(String.format("/endereco?cep=%s&?numero=%s",
                enderecoSalvo.getCep(),
                enderecoSalvo.getNumero()));

        return ResponseEntity.created(uri).body(enderecoResponse);
    }

}
