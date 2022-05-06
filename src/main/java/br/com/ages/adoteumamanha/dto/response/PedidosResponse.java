package br.com.ages.adoteumamanha.dto.response;

import br.com.ages.adoteumamanha.domain.Pageable;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@Getter
@Builder(setterPrefix = "with")
public final class PedidosResponse implements Pageable<ResumoPedidoResponse>, Serializable {

    private static final long serialVersionUID = -1112700589878041641L;

    private final Boolean ultimaPagina;
    private final Integer totalDePaginas;
    private final Long totalDeElementos;
    private final Integer numeroDaPagina;
    private final Integer tamanhoDaPagina;
    private final Boolean primeiraPagina;
    private final Integer numeroDeElementos;
    private final Boolean paginaVazia;
    private final List<ResumoPedidoResponse> conteudo;

}


