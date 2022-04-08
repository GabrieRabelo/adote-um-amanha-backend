package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.fixture.Fixture;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

import static br.com.ages.adoteumamanha.dto.response.NecessidadesResponse.NecessidadeResponse;

public class NecessidadeResponseMapperTest {

    final NecessidadeResponseMapper MAPPER = new NecessidadeResponseMapper();

    private Pedido pedido;

    @Test
    public void mapper() {

        pedido = Fixture.make(Pedido.builder()).build();

        final NecessidadeResponse response = MAPPER.apply(pedido);

        Assert.assertEquals(response.getId(), pedido.getId());
        Assert.assertEquals(response.getAssunto(), pedido.getAssunto());
        Assert.assertEquals(response.getDescricao(), pedido.getDescricao());
        Assert.assertEquals(response.getCategoria(), pedido.getCategoria());
        Assert.assertEquals(response.getSubcategoria(), pedido.getSubcategoria());
        Assert.assertEquals(response.getStatus(), pedido.getStatus());
        Assert.assertEquals(response.getData(), LocalDate.from(pedido.getDataHora()));
        Assert.assertEquals(response.getIdCasa(), pedido.getUsuario().getId());
        Assert.assertEquals(response.getNomeCasa(), pedido.getUsuario().getNome());
        Assert.assertEquals(response.getUrlVideo(), pedido.getUrlVideo());

    }
}
