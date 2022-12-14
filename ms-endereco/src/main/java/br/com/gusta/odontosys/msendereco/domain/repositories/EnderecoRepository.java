package br.com.gusta.odontosys.msendereco.domain.repositories;

import br.com.gusta.odontosys.msendereco.domain.entities.Endereco;

public interface EnderecoRepository {

    Endereco salvarEndereco(Endereco endereco);

    Endereco buscarEndereco(String cep);

    Endereco consultarEndereco(String cep, int numero);

    void deletarEndereco(String cep, int numero);

}
