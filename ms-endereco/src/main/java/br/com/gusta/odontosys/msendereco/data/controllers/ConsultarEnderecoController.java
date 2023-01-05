package br.com.gusta.odontosys.msendereco.data.controllers;

import br.com.gusta.odontosys.msendereco.core.utils.MensagemUtils;
import br.com.gusta.odontosys.msendereco.data.mappers.GenericMapper;
import br.com.gusta.odontosys.msendereco.data.models.dto.response.EnderecoResponse;
import br.com.gusta.odontosys.msendereco.domain.entities.Endereco;
import br.com.gusta.odontosys.msendereco.domain.usecases.ConsultarEndereco;
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
@RequestMapping("/endereco")
public class ConsultarEnderecoController {

    private final ConsultarEndereco consultarEndereco;
    private final MessageSource messageSource;
    private final GenericMapper<Endereco, EnderecoResponse> enderecoEnderecoResponseMapper;

    @GetMapping
    public ResponseEntity<EnderecoResponse> consultarEndereco(@RequestParam String cep,
                                                              @RequestParam String numero) {
        var endereco = consultarEndereco.consultarEndereco(cep, Integer.parseInt(numero));
        var response = enderecoEnderecoResponseMapper.map(endereco);

        log.info(MensagemUtils.getMensagem(messageSource, "consulta.sucesso"));

        return ResponseEntity.ok(response);
    }

}