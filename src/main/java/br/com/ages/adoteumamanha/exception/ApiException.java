package br.com.ages.adoteumamanha.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Getter
public class ApiException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -1085643168290025461L;

    private final HttpStatus httpStatus;

    public ApiException(final String mensagem, final HttpStatus httpStatus) {
        super(mensagem);
        this.httpStatus = httpStatus;
    }

}
