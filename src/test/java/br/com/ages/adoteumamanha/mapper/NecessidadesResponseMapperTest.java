package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.dto.response.NecessidadesResponse;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.Page;

import static org.mockito.Mockito.mock;

public class NecessidadesResponseMapperTest {

    final NecessidadesResponseMapper MAPPER = new NecessidadesResponseMapper();

    private Page<Pedido> pedidos;

    @Test
    public void mapper() {

        pedidos = mock(Page.class);

        final NecessidadesResponse response = MAPPER.apply(pedidos);

        Assert.assertEquals(response.getConteudo().size(), pedidos.getContent().size());
        Assert.assertEquals(response.getNumeroDaPagina(), Integer.valueOf(pedidos.getNumber()));
        Assert.assertEquals(response.getNumeroDeElementos(), Integer.valueOf(pedidos.getNumberOfElements()));
        Assert.assertEquals(response.getPaginaVazia(), pedidos.isEmpty());
        Assert.assertEquals(response.getPrimeiraPagina(), pedidos.isFirst());
        Assert.assertEquals(response.getTamanhoDaPagina(), Integer.valueOf(pedidos.getSize()));
        Assert.assertEquals(response.getTotalDeElementos(), Long.valueOf(pedidos.getTotalElements()));
        Assert.assertEquals(response.getTotalDePaginas(), Integer.valueOf(pedidos.getTotalPages()));
        Assert.assertEquals(response.getUltimaPagina(), pedidos.isLast());
    }
}
