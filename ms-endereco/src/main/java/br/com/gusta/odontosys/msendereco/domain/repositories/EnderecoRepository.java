package br.com.gusta.odontosys.msendereco.domain.repositories;

import br.com.gusta.odontosys.msendereco.domain.entities.Endereco;

public interface EnderecoRepository {
    Endereco salvarEndereco(Endereco endereco);
    Endereco buscarEndereco(String cep);
    Endereco consultarEndereco(String cep);
    Endereco deletarEndereco(String cep);
}
