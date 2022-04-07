package br.com.ages.adoteumamanha.dto.request;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder(setterPrefix = "with")
public final class LoginRequest implements Serializable {

    private static final long serialVersionUID = 7429570525169621064L;

    private final String email;
    private final String senha;
}
