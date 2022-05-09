package br.com.ages.adoteumamanha.validator;

import br.com.ages.adoteumamanha.dto.request.LoginRequest;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.exception.Mensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;
import static org.springframework.util.ObjectUtils.isEmpty;

@Component
@RequiredArgsConstructor
public class LoginRequestValidator {

    private final EmailValidator emailValidator;

    public void validar(final LoginRequest request) {

        if (isNull(request)) {
            throw new ApiException(Mensagem.REQUEST_INVALIDO.getDescricao(), HttpStatus.BAD_REQUEST);
        }

        if (isEmpty(request.getEmail())) {
            throw new ApiException(Mensagem.EMAIL_INVALIDO.getDescricao(), HttpStatus.BAD_REQUEST);
        }

        if (isEmpty(request.getSenha())) {
            throw new ApiException(Mensagem.SENHA_INVALIDA.getDescricao(), HttpStatus.BAD_REQUEST);
        }

        emailValidator.validar(request.getEmail());
    }
}
