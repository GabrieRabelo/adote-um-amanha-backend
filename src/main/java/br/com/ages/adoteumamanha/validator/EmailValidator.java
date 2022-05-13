package br.com.ages.adoteumamanha.validator;

import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.exception.Mensagem;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Component
public class EmailValidator {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public void validar(final String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);

        if (isFalse(matcher.find())) {
            throw new ApiException(Mensagem.EMAIL_INVALIDO.getDescricao(), HttpStatus.BAD_REQUEST);
        }

    }
}


