package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.dto.response.DoacaoResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;

import static java.util.Optional.ofNullable;

@Component
public class DoacaoResponseMapper implements Function<Pedido, DoacaoResponse> {

    @Override
    public DoacaoResponse apply(final Pedido pedido) {
        return ofNullable(pedido)
                .map(doacao -> DoacaoResponse.builder()
                        .withId(doacao.getId())
                        .withAssunto(doacao.getAssunto())
                        .withDescricao(doacao.getDescricao())
                        .withData(doacao.getDataHora())
                        .withStatus(doacao.getStatus())
                        .withCategoria(doacao.getCategoria())
                        .withSubcategoria(doacao.getSubcategoria())
                        .build())
                .orElse(null);
    }
}