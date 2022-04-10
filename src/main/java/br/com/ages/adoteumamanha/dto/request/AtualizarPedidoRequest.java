package br.com.ages.adoteumamanha.dto.request;

import br.com.ages.adoteumamanha.domain.enumeration.Categoria;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AtualizarPedidoRequest {

    private final String assunto;

    private final String descricao;

    private final Categoria categoria;

}
