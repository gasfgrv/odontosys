package br.com.gusta.odontosys.msendereco.core.utils;

import lombok.experimental.UtilityClass;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.Objects;

@UtilityClass
public class MensagemUtils {

    public String getMensagem(MessageSource messageSource, String codigo, String... argumentos) {
        return messageSource.getMessage(codigo, argumentos, Locale.getDefault());
    }

}
