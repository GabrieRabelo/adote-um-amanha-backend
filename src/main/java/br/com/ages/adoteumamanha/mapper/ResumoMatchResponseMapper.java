package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Match;
import br.com.ages.adoteumamanha.dto.response.ResumoMatchResponse;

import java.util.function.Function;

import static java.util.Optional.ofNullable;

public class ResumoMatchResponseMapper implements Function<Match, ResumoMatchResponse> {
    public ResumoMatchResponse apply(final Match match) {
        return ofNullable(match)
                .map(m -> ResumoMatchResponse.builder()
                        .withId(m.getId())
                        .withAssunto(m.getNecessidade().getAssunto())
                        .withCategoria(m.getNecessidade().getCategoria())
                        .withSubcategoria(m.getNecessidade().getSubcategoria())
                        .withData(m.getDataCriacao())
                        .withNomeCasa(m.getNecessidade().getUsuario().getNome())
                        .withNomeDoador(m.getDoacao().getUsuario().getNome())
                        .withStatus(m.getStatus())
                        .build())
                .orElse(null);
    }

}
