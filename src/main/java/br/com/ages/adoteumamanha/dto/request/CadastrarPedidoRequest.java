package br.com.ages.adoteumamanha.dto.request;

import br.com.ages.adoteumamanha.domain.enumeration.Categoria;
import br.com.ages.adoteumamanha.domain.enumeration.Subcategoria;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder(setterPrefix = "with")
public final class CadastrarPedidoRequest implements Serializable {

    private static final long serialVersionUID = 9091540399973146249L;

    private final String assunto;
    private final String descricao;
    private final Categoria categoria;
    private final Subcategoria subcategoria;
    private final String urlVideo;

}
