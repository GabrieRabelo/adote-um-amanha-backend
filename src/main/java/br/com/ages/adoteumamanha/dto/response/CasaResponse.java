package br.com.ages.adoteumamanha.dto.response;

import br.com.ages.adoteumamanha.domain.entity.Endereco;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder(setterPrefix = "with")
public class CasaResponse implements Serializable {

    private static final long serialVersionUID = 84109019533928162L;

    private final String nome;
    private final String telefone;
    private final String email;
    private final Endereco endereco;
    private final String site;

}
