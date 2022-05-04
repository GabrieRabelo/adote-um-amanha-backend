package br.com.ages.adoteumamanha.dto.response;

import br.com.ages.adoteumamanha.domain.Pageable;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder(setterPrefix = "with")
public final class NecessidadesResponse implements Pageable<NecessidadeResponse>, Serializable {

    private static final long serialVersionUID = 8877642249248887304L;

    private final Boolean ultimaPagina;
    private final Integer totalDePaginas;
    private final Long totalDeElementos;
    private final Integer numeroDaPagina;
    private final Integer tamanhoDaPagina;
    private final Boolean primeiraPagina;
    private final Integer numeroDeElementos;
    private final Boolean paginaVazia;
    private final List<NecessidadeResponse> conteudo;

}


