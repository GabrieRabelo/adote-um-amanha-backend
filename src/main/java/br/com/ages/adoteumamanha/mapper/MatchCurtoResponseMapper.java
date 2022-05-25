package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Match;
import br.com.ages.adoteumamanha.dto.response.ResumoMatchResponse;
import org.apache.commons.lang3.ObjectUtils;

import java.util.function.Function;

import static java.util.Optional.ofNullable;

public class MatchCurtoResponseMapper implements Function<Match, ResumoMatchResponse> {
    public ResumoMatchResponse apply(final Match match){
        return ofNullable(match)
                .map(m -> ResumoMatchResponse.builder()
                        .withId(m.getId())
                        .withAssunto(m.getNecessidade().getAssunto())
                        .withCategoria(m.getNecessidade().getCategoria())
                        .withSubcategoria(m.getNecessidade().getSubcategoria())
                        .withData(m.getData())
                        .withNomeCasa(m.getNecessidade().getUsuario().getNome())
                        .withNomeDoador(m.getDoacao().getUsuario().getNome())
                        .build())
                .orElse(null);
    }

}
