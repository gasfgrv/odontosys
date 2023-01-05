package br.com.gusta.odontosys.msendereco.utils.builder;

import br.com.gusta.odontosys.msendereco.domain.entities.Endereco;

public class EnderecoBuilder {

    private String cep;
    private String logradouro;
    private int numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;

    public EnderecoBuilder setCep(String cep) {
        this.cep = cep;
        return this;
    }

    public EnderecoBuilder setLogradouro(String logradouro) {
        this.logradouro = logradouro;
        return this;
    }

    public EnderecoBuilder setNumero(int numero) {
        this.numero = numero;
        return this;
    }

    public EnderecoBuilder setComplemento(String complemento) {
        this.complemento = complemento;
        return this;
    }

    public EnderecoBuilder setBairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public EnderecoBuilder setCidade(String cidade) {
        this.cidade = cidade;
        return this;
    }

    public EnderecoBuilder setUf(String uf) {
        this.uf = uf;
        return this;
    }

    public Endereco build() {
        var endereco = new Endereco();
        endereco.setCep(this.cep);
        endereco.setLogradouro(this.logradouro);
        endereco.setNumero(this.numero);
        endereco.setComplemento(this.complemento);
        endereco.setBairro(this.bairro);
        endereco.setCidade(this.cidade);
        endereco.setUf(this.uf);
        return endereco;
    }
}
