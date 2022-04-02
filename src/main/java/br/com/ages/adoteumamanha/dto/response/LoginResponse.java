package br.com.ages.adoteumamanha.dto.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder(setterPrefix = "with")
public final class LoginResponse implements Serializable {

    private static final long serialVersionUID = 8700443845146596279L;

    private final String accessToken;
}

