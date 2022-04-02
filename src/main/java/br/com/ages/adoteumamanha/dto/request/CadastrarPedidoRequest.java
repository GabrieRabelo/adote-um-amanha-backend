package br.com.ages.adoteumamanha.dto.request;

import br.com.ages.adoteumamanha.domain.enumeration.Categoria;
import br.com.ages.adoteumamanha.domain.enumeration.Subcategoria;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public final class CadastrarPedidoRequest implements Serializable {

    private static final long serialVersionUID = 9108505036289116509L;

    private final String assunto;

    private final String descricao;

    private final Categoria categoria;

    private final Subcategoria subcategoria;
}
