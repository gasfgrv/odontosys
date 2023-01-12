package br.com.gusta.odontosys.msendereco.utils.builder;

import br.com.gusta.odontosys.msendereco.data.models.entity.EnderecoEntity;
import br.com.gusta.odontosys.msendereco.data.models.entity.EnderecoId;

public class EnderecoEntityBuilder {
    private EnderecoId enderecoId;
    private String cep;
    private int numero;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;

    public EnderecoEntityBuilder setCep(String cep) {
        this.cep = cep;
        return this;
    }

    public EnderecoEntityBuilder setNumero(int numero) {
        this.numero = numero;
        return this;
    }

    public EnderecoEntityBuilder setLogradouro(String logradouro) {
        this.logradouro = logradouro;
        return this;
    }

    public EnderecoEntityBuilder setComplemento(String complemento) {
        this.complemento = complemento;
        return this;
    }

    public EnderecoEntityBuilder setBairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public EnderecoEntityBuilder setCidade(String cidade) {
        this.cidade = cidade;
        return this;
    }

    public EnderecoEntityBuilder setUf(String uf) {
        this.uf = uf;
        return this;
    }

    public EnderecoEntity build() {
        var id = new EnderecoId();
        id.setCep(this.cep);
        id.setNumero(this.numero);

        var entity = new EnderecoEntity();
        entity.setEnderecoId(id);
        entity.setLogradouro(this.logradouro);
        entity.setComplemento(this.complemento);
        entity.setBairro(this.bairro);
        entity.setCidade(this.cidade);
        entity.setUf(this.uf);

        return entity;
    }
}
