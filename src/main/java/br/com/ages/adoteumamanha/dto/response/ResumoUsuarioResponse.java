package br.com.ages.adoteumamanha.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder(setterPrefix = "with")
public class ResumoUsuarioResponse implements Serializable {

    private static final long serialVersionUID = -2338010642651294681L;

    private final long id;
    private final String nome;
    private final int doacoesAprovadas;
    private final int doacoesRecusadas;

}
