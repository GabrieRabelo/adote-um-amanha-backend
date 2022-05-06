package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.dto.response.DescricaoPedidoResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;

import static java.util.Optional.ofNullable;

@Component
public class DescricaoPedidoResponseMapper implements Function<Pedido, DescricaoPedidoResponse> {

    @Override
    public DescricaoPedidoResponse apply(final Pedido pedido) {
        return ofNullable(pedido)
                .map(p -> DescricaoPedidoResponse.builder()
                        .withId(p.getId())
                        .withAssunto(p.getAssunto())
                        .withDescricao(p.getDescricao())
                        .withData(p.getDataHora())
                        .withStatus(p.getStatus())
                        .withCategoria(p.getCategoria())
                        .withSubcategoria(p.getSubcategoria())
                        .withIdUsuario(p.getUsuario().getId())
                        .withNomeUsuario(p.getUsuario().getNome())
                        .withUrlVideo(p.getUrlVideo())
                        .withTipo(p.getTipoPedido())
                        .build())
                .orElse(null);
    }

}


