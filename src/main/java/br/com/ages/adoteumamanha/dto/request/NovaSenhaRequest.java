package br.com.ages.adoteumamanha.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder(setterPrefix = "with")
public final class NovaSenhaRequest implements Serializable {

    private static final long serialVersionUID = -4607937866469488753L;

    private final String novaSenha;

}
