package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.dto.response.NecessidadeResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;

import static java.util.Optional.ofNullable;

@Component
public class NecessidadeResponseMapper implements Function<Pedido, NecessidadeResponse> {

    @Override
    public NecessidadeResponse apply(final Pedido pedido) {
        return ofNullable(pedido)
                .map(necessidade -> NecessidadeResponse.builder()
                        .withId(necessidade.getId())
                        .withAssunto(necessidade.getAssunto())
                        .withDescricao(necessidade.getDescricao())
                        .withData(necessidade.getDataHora())
                        .withStatus(necessidade.getStatus())
                        .withCategoria(necessidade.getCategoria())
                        .withSubcategoria(necessidade.getSubcategoria())
                        .withIdCasa(necessidade.getUsuario().getId())
                        .withNomeCasa(necessidade.getUsuario().getNome())
                        .withUrlVideo(necessidade.getUrlVideo())
                        .build())
                .orElse(null);
    }
}


