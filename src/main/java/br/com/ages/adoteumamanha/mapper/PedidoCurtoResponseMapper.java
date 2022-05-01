package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.dto.response.DoacaoResponse;
import br.com.ages.adoteumamanha.dto.response.NecessidadeResponse;
import br.com.ages.adoteumamanha.dto.response.PedidoCurtoResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;

import static java.util.Optional.ofNullable;

@Component
public class PedidoCurtoResponseMapper implements Function<Pedido, PedidoCurtoResponse> {

    @Override
    public PedidoCurtoResponse apply(final Pedido pedido) {
        return ofNullable(pedido)
                .map(ped -> PedidoCurtoResponse.builder()
                        .withId(ped.getId())
                        .withAssunto(ped.getAssunto())
                        .withData(ped.getDataHora())
                        .withStatus(ped.getStatus())
                        .withSubcategoria(ped.getSubcategoria())
                        .build())
                .orElse(null);
    }
}


