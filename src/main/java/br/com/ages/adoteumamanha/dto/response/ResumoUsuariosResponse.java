package br.com.ages.adoteumamanha.dto.response;

import br.com.ages.adoteumamanha.domain.Pageable;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
@Builder(setterPrefix = "with")
public final class ResumoUsuariosResponse implements Pageable<ResumoUsuarioResponse>, Serializable {

    private static final long serialVersionUID = 4600163212524209808L;

    private final Boolean ultimaPagina;
    private final Integer totalDePaginas;
    private final Long totalDeElementos;
    private final Integer numeroDaPagina;
    private final Integer tamanhoDaPagina;
    private final Boolean primeiraPagina;
    private final Integer numeroDeElementos;
    private final Boolean paginaVazia;
    private final List<ResumoUsuarioResponse> conteudo;

}


