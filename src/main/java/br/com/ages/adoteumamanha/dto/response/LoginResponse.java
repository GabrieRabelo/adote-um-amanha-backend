package br.com.ages.adoteumamanha.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder(setterPrefix = "with")
public final class LoginResponse implements Serializable {

    private static final long serialVersionUID = 8535654719306884214L;

    private final String accessToken;

}

