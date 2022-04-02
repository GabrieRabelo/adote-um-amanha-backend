package br.com.ages.adoteumamanha.dto.request;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder(setterPrefix = "with")
public final class LoginRequest implements Serializable {

    private static final long serialVersionUID = 1190444961888356682L;

    private final String email;
    private final String senha;
}
