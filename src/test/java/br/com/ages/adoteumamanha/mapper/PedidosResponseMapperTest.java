package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.dto.response.PedidosResponse;
import org.junit.Test;
import org.springframework.data.domain.Page;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class PedidosResponseMapperTest {

    final PedidosResponseMapper MAPPER = new PedidosResponseMapper();
    private Page<Pedido> pedidos;

    @Test
    public void mapper() {

        pedidos = mock(Page.class);

        final PedidosResponse response = MAPPER.apply(pedidos);

        assertEquals(response.getConteudo().size(), pedidos.getContent().size());
        assertEquals(response.getNumeroDeElementos(), Integer.valueOf(pedidos.getNumberOfElements()));
        assertEquals(response.getPaginaVazia(), pedidos.isEmpty());
        assertEquals(response.getPrimeiraPagina(), pedidos.isFirst());
        assertEquals(response.getTamanhoDaPagina(), Integer.valueOf(pedidos.getSize()));
        assertEquals(response.getTotalDeElementos(), Long.valueOf(pedidos.getTotalElements()));
        assertEquals(response.getTotalDePaginas(), Integer.valueOf(pedidos.getTotalPages()));
        assertEquals(response.getNumeroDaPagina(), Integer.valueOf(pedidos.getNumber()));
        assertEquals(response.getUltimaPagina(), pedidos.isLast());
    }

}
