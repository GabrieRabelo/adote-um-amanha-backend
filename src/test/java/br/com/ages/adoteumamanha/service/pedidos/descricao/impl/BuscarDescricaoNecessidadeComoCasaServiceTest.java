package br.com.ages.adoteumamanha.service.pedidos.descricao.impl;

import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.service.pedidos.descricao.BuscarDescricaoPedidoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static br.com.ages.adoteumamanha.domain.enumeration.Perfil.CASA;
import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.NECESSIDADE;
import static br.com.ages.adoteumamanha.fixture.Fixture.make;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BuscarDescricaoNecessidadeComoCasaServiceTest {

    @InjectMocks
    private BuscarDescricaoNecessidadeComoCasaService service;

    @Mock
    private BuscarDescricaoPedidoService buscarDescricaoPedidoService;

    @Test
    public void getPerfil() {
        assertEquals(CASA, service.getPerfil());
    }

    @Test
    public void getTipoPedidos() {
        assertEquals(asList(NECESSIDADE), service.getTipoPedidos());

    }

    @Test
    public void ok() {

        final Long idPedido = 1L;
        final UserPrincipal userPrincipal = UserPrincipal.create(make(Usuario.builder()).build());
        service.executar(idPedido, userPrincipal.getId());

        verify(buscarDescricaoPedidoService).buscar(idPedido, service.getTipoPedidos(), userPrincipal.getId());

    }

}