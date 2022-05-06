package br.com.ages.adoteumamanha.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder(setterPrefix = "with")
public final class LoginRequest implements Serializable {

    private static final long serialVersionUID = 1788611393386716319L;

    private final String email;
    private final String senha;

}
