package br.com.gusta.odontosys.msendereco.core.utils;

import lombok.experimental.UtilityClass;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.Objects;
import org.springframework.validation.ObjectError;

@UtilityClass
public class MensagemUtils {

    public String getMensagem(MessageSource messageSource, String codigo, String... argumentos) {
        return messageSource.getMessage(codigo, argumentos, Locale.getDefault());
    }

    public static String getMensagem(MessageSource messageSource, ObjectError erro) {
        return messageSource.getMessage(erro, Locale.getDefault());
    }
}
