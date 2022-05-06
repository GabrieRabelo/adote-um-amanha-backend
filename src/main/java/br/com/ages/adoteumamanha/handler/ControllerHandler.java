package br.com.ages.adoteumamanha.handler;

import br.com.ages.adoteumamanha.dto.response.ErroResponse;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.exception.Mensagem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErroResponse<String>> handlerApiException(ApiException apiException) {
        ErroResponse<String> response = new ErroResponse<>();
        response.setCodigoStatus(apiException.getHttpStatus().value());
        response.setInformacao(apiException.getMessage());

        return ResponseEntity.status(apiException.getHttpStatus()).body(response);
    }


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErroResponse<String>> AuthenticationException() {
        ErroResponse<String> response = new ErroResponse<>();
        response.setCodigoStatus(HttpStatus.FORBIDDEN.value());
        response.setInformacao(Mensagem.ACESSO_NAO_PERMITIDO.getDescricao());

        return ResponseEntity.status((HttpStatus.FORBIDDEN.value())).body(response);
    }

}

