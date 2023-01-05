package br.com.gusta.odontosys.msendereco.data.controllers;

import br.com.gusta.odontosys.msendereco.core.utils.MensagemUtils;
import br.com.gusta.odontosys.msendereco.domain.usecases.DeletarEnderecoExistente;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/endereco")
public class DeletarEnderecoExistenteController {

    private final MessageSource messageSource;
    private final DeletarEnderecoExistente deletarEnderecoExistente;

    @DeleteMapping
    @Transactional
    public ResponseEntity<Void> deletarEnderecoExistente(@RequestParam String cep,
                                                         @RequestParam String numero) {
        deletarEnderecoExistente.deletarEndereco(cep, Integer.parseInt(numero));

        log.info(MensagemUtils.getMensagem(messageSource, "remocao.sucesso"));

        return ResponseEntity.noContent().build();
    }

}
