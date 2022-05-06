package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.dto.response.DescricaoPedidoResponse;
import org.junit.Test;

import static br.com.ages.adoteumamanha.fixture.Fixture.make;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DescricaoPedidoResponseMapperTest {

    final DescricaoPedidoResponseMapper MAPPER = new DescricaoPedidoResponseMapper();

    @Test
    public void mapper() {
        final Pedido pedido = make(Pedido.builder()).build();

        final DescricaoPedidoResponse response = MAPPER.apply(pedido);

        assertEquals(pedido.getId(), response.getId());
        assertEquals(pedido.getAssunto(), response.getAssunto());
        assertEquals(pedido.getDescricao(), response.getDescricao());
        assertEquals(pedido.getCategoria(), response.getCategoria());
        assertEquals(pedido.getSubcategoria(), response.getSubcategoria());
        assertEquals(pedido.getDataHora(), response.getData());
        assertEquals(pedido.getStatus(), response.getStatus());
        assertEquals(pedido.getTipoPedido(), response.getTipo());
        assertEquals(pedido.getUrlVideo(), response.getUrlVideo());
        assertEquals(pedido.getUsuario().getId(), response.getIdUsuario());
        assertEquals(pedido.getUsuario().getNome(), response.getNomeUsuario());

    }

    @Test
    public void mapper_null() {
        final DescricaoPedidoResponse response = MAPPER.apply(null);
        assertNull(response);
    }

}