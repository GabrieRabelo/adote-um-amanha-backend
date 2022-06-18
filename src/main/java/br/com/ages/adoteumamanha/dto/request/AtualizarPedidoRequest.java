package br.com.ages.adoteumamanha.dto.request;

import br.com.ages.adoteumamanha.domain.enumeration.Categoria;
import br.com.ages.adoteumamanha.domain.enumeration.Subcategoria;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder(setterPrefix = "with")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AtualizarPedidoRequest implements Serializable {

    private static final long serialVersionUID = 5632330232162822488L;

    private final String assunto;
    private final String descricao;
    private final Categoria categoria;
    private final Subcategoria subcategoria;
    private final String urlVideo;

}
