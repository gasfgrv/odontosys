package br.com.gusta.odontosys.msendereco.data.controllers;

import br.com.gusta.odontosys.msendereco.core.utils.MensagemUtils;
import br.com.gusta.odontosys.msendereco.data.mappers.EnderecoToEnderecoResponseMapper;
import br.com.gusta.odontosys.msendereco.data.mappers.NovoEnderecoFormToEnderecoMapper;
import br.com.gusta.odontosys.msendereco.data.models.dto.input.NovoEnderecoForm;
import br.com.gusta.odontosys.msendereco.data.models.dto.response.EnderecoResponse;
import br.com.gusta.odontosys.msendereco.domain.usecases.AtualizarEndereco;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/endereco")
public class AtualizarEnderecoController {

    private final MessageSource messageSource;
    private final AtualizarEndereco atualizarEndereco;
    private final EnderecoToEnderecoResponseMapper enderecoToEnderecoResponseMapper;
    private final NovoEnderecoFormToEnderecoMapper novoEnderecoFormToEnderecoMapper;

    @PutMapping
    @Transactional
    public ResponseEntity<EnderecoResponse> atualizarEndereco(@RequestBody @Valid NovoEnderecoForm form) {
        var endereco = novoEnderecoFormToEnderecoMapper.toModel(form);
        var enderecoAtualizado = atualizarEndereco.atualizarEndereco(endereco);

        log.info(MensagemUtils.getMensagem(messageSource, "atualizacao.sucesso"));

        var response = enderecoToEnderecoResponseMapper.toResponse(enderecoAtualizado);
        return ResponseEntity.ok(response);
    }

}
