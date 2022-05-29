package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Match;
import br.com.ages.adoteumamanha.dto.response.DescricaoMatchResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;

import static java.util.Optional.ofNullable;

@Component
public class DescricaoMatchResponseMapper implements Function<Match, DescricaoMatchResponse> {

    @Override
    public DescricaoMatchResponse apply(final Match match) {
        return ofNullable(match)
                .map(m -> DescricaoMatchResponse.builder()
                        .withId(m.getId())
                        .withIdCasa(m.getNecessidade().getUsuario().getId())
                        .withIdDoador(m.getDoacao().getUsuario().getId())
                        .withNomeCasa(m.getNecessidade().getUsuario().getNome())
                        .withNomeDoador(m.getDoacao().getUsuario().getNome())
                        .withStatus(m.getStatus())
                        .withDataCriacao(m.getDataCriacao())
                        .withDataFechamento(m.getDataFechamento())
                        .withCategoria(m.getNecessidade().getCategoria())
                        .withSubcategoria(m.getNecessidade().getSubcategoria())
                        .withAssunto(m.getNecessidade().getAssunto())
                        .withDescricao(m.getNecessidade().getDescricao())
                        .withFinalizadoPor(m.getFinalizadoPor())
                        .build())
                .orElse(null);
    }
}