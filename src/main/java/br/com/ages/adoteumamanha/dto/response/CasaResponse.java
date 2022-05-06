package br.com.ages.adoteumamanha.dto.response;

import br.com.ages.adoteumamanha.domain.entity.Endereco;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Builder(setterPrefix = "with")
public class CasaResponse implements Serializable {

    private static final long serialVersionUID = 84109019533928162L;

    private String nome;
    private String telefone;
    private String email;
    private Endereco endereco;
    private String site;

}
