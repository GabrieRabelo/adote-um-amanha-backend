package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.dto.response.ResumoPedidoResponse;
import org.junit.Test;

import static br.com.ages.adoteumamanha.fixture.Fixture.make;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PedidoCurtoResponseMapperTest {

    final PedidoCurtoResponseMapper MAPPER = new PedidoCurtoResponseMapper();

    @Test
    public void mapper() {
        final Pedido pedido = make(Pedido.builder()).build();

        final ResumoPedidoResponse response = MAPPER.apply(pedido);

        assertEquals(pedido.getId(), response.getId());
        assertEquals(pedido.getAssunto(), response.getAssunto());
        assertEquals(pedido.getSubcategoria(), response.getSubcategoria());
        assertEquals(pedido.getDataHora(), response.getData());
        assertEquals(pedido.getStatus(), response.getStatus());
        assertEquals(pedido.getTipoPedido(), response.getTipo());
        assertEquals(pedido.getUsuario().getNome(), response.getNomeUsuario());
    }

    @Test
    public void mapper_null() {
        final ResumoPedidoResponse response = MAPPER.apply(null);
        assertNull(response);
    }

}