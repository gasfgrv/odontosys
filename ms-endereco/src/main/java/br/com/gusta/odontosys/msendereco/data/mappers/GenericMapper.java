package br.com.gusta.odontosys.msendereco.data.mappers;

import br.com.gusta.odontosys.msendereco.core.exceptions.MapperException;

public abstract class GenericMapper<I, O> {

    public O map(I input) {
        if (input == null) {
            throw new MapperException("Erro ao fazer o mappeamento!");
        }

        return convert(input);
    }

    protected abstract O convert(I input);

}
