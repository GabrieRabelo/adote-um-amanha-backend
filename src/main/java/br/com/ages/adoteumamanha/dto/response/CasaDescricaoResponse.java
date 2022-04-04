package br.com.ages.adoteumamanha.dto.response;

import br.com.ages.adoteumamanha.domain.entity.Endereco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(setterPrefix = "with")
public class CasaDescricaoResponse {

    private String nome;
    private String telefone;
    private String email;
    private Endereco endereco;
    private String site;

}
