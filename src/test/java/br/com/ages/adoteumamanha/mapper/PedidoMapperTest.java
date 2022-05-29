package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.TipoPedido;
import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import org.junit.Before;
import org.junit.Test;

import static br.com.ages.adoteumamanha.domain.enumeration.Perfil.CASA;
import static br.com.ages.adoteumamanha.domain.enumeration.Perfil.DOADOR;
import static br.com.ages.adoteumamanha.domain.enumeration.Status.PENDENTE;
import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.DOACAO;
import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.NECESSIDADE;
import static br.com.ages.adoteumamanha.fixture.Fixture.make;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PedidoMapperTest {

    final PedidoMapper MAPPER = new PedidoMapper();

    private UserPrincipal userPrincipal;

    @Before
    public void setup() {
        userPrincipal = UserPrincipal.create(make(Usuario.builder()).build());
    }

    @Test
    public void mapper_doador() {
        final CadastrarPedidoRequest request = make(CadastrarPedidoRequest.builder()).build();

        userPrincipal.setPerfil(DOADOR);
        final Pedido response = MAPPER.apply(request, userPrincipal);

        assertEquals(request.getAssunto(), response.getAssunto());
        assertEquals(request.getDescricao(), response.getDescricao());
        assertEquals(request.getCategoria(), response.getCategoria());
        assertEquals(request.getSubcategoria(), response.getSubcategoria());
        assertNotNull(response.getDataHora());
        assertEquals(PENDENTE, response.getStatus());
        assertEquals(buildTipoPedido(userPrincipal), response.getTipoPedido());
        assertEquals(request.getUrlVideo(), response.getUrlVideo());
        assertEquals(userPrincipal.getId(), response.getUsuario().getId());
    }

    @Test
    public void mapper_casa() {
        final CadastrarPedidoRequest request = make(CadastrarPedidoRequest.builder()).build();

        userPrincipal.setPerfil(CASA);
        final Pedido response = MAPPER.apply(request, userPrincipal);

        assertEquals(request.getAssunto(), response.getAssunto());
        assertEquals(request.getDescricao(), response.getDescricao());
        assertEquals(request.getCategoria(), response.getCategoria());
        assertEquals(request.getSubcategoria(), response.getSubcategoria());
        assertNotNull(response.getDataHora());
        assertEquals(PENDENTE, response.getStatus());
        assertEquals(buildTipoPedido(userPrincipal), response.getTipoPedido());
        assertEquals(request.getUrlVideo(), response.getUrlVideo());
        assertEquals(userPrincipal.getId(), response.getUsuario().getId());
    }

    private TipoPedido buildTipoPedido(final UserPrincipal userPrincipal) {
        if (CASA.equals(userPrincipal.getPerfil())) {
            return NECESSIDADE;
        }
        return DOACAO;
    }
}