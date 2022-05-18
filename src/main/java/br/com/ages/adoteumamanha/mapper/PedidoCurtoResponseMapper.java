package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.dto.response.ResumoPedidoResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;

import static java.util.Optional.ofNullable;

@Component
public class PedidoCurtoResponseMapper implements Function<Pedido, ResumoPedidoResponse> {

    @Override
    public ResumoPedidoResponse apply(final Pedido pedido) {
        return ofNullable(pedido)
                .map(p -> ResumoPedidoResponse.builder()
                        .withId(p.getId())
                        .withAssunto(p.getAssunto())
                        .withData(p.getDataHora())
                        .withStatus(p.getStatus())
                        .withSubcategoria(p.getSubcategoria())
                        .withTipo(p.getTipoPedido())
                        .withNomeUsuario(p.getUsuario().getNome())
                        .build())
                .orElse(null);
    }

}


