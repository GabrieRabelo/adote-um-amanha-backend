package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Match;
import br.com.ages.adoteumamanha.dto.response.ResumoMatchResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;

import static java.util.Optional.ofNullable;

@Component
public class ResumoMatchResponseMapper implements Function<Match, ResumoMatchResponse> {

    @Override
    public ResumoMatchResponse apply(final Match match) {
        return ofNullable(match)
                .map(m -> ResumoMatchResponse.builder()
                        .withId(m.getId())
                        .withAssunto(m.getNecessidade().getAssunto())
                        .withData(m.getDataCriacao())
                        .withCategoria(m.getNecessidade().getCategoria())
                        .withSubcategoria(m.getNecessidade().getSubcategoria())
                        .withStatus(m.getStatus())
                        .withDescricaoDoacao(m.getNecessidade().getDescricao())
                        .withIdCasa(m.getNecessidade().getUsuario().getId())
                        .withNomeCasa(m.getNecessidade().getUsuario().getNome())
                        .withIdDoador(m.getDoacao().getUsuario().getId())
                        .withNomeDoador(m.getDoacao().getUsuario().getNome())
                        .build())
                .orElse(null);
    }
}