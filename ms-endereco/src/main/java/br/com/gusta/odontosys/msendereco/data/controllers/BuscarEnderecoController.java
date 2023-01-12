package br.com.gusta.odontosys.msendereco.data.controllers;

import br.com.gusta.odontosys.msendereco.core.utils.MensagemUtils;
import br.com.gusta.odontosys.msendereco.data.mappers.EnderecoToBuscarEnderecoResponseMapper;
import br.com.gusta.odontosys.msendereco.data.models.dto.response.BuscarEnderecoResponse;
import br.com.gusta.odontosys.msendereco.domain.usecases.BuscarEndereco;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/endereco/buscar")
public class BuscarEnderecoController {

    private final MessageSource messageSource;
    private final BuscarEndereco buscarEndereco;
    private final EnderecoToBuscarEnderecoResponseMapper enderecoToBuscarEnderecoResponseMapper;

    @GetMapping
    public ResponseEntity<BuscarEnderecoResponse> buscarEndereco(@RequestParam String cep) {
        var endereco = buscarEndereco.buscarEndereco(cep);
        var response = enderecoToBuscarEnderecoResponseMapper.toResponse(endereco);

        log.info(MensagemUtils.getMensagem(messageSource, "busca.sucesso"));

        return ResponseEntity.ok(response);
    }

}
