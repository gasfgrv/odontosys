package br.com.gusta.odontosys.msendereco.utils.builder;

import br.com.gusta.odontosys.msendereco.data.models.dto.ViacepResponse;

public class ViacepResponseBuilder {

    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;

    public ViacepResponseBuilder setCep(String cep) {
        this.cep = cep;
        return this;
    }

    public ViacepResponseBuilder setLogradouro(String logradouro) {
        this.logradouro = logradouro;
        return this;
    }

    public ViacepResponseBuilder setComplemento(String complemento) {
        this.complemento = complemento;
        return this;
    }

    public ViacepResponseBuilder setBairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public ViacepResponseBuilder setLocalidade(String localidade) {
        this.localidade = localidade;
        return this;
    }

    public ViacepResponseBuilder setUf(String uf) {
        this.uf = uf;
        return this;
    }

    public ViacepResponse build() {
        return ViacepResponse.builder()
                .cep(this.cep)
                .logradouro(this.logradouro)
                .complemento(this.complemento)
                .bairro(this.bairro)
                .localidade(this.localidade)
                .uf(this.uf)
                .build();
    }
}
