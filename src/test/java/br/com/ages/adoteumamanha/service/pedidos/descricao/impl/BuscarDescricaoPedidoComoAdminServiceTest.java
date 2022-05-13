package br.com.ages.adoteumamanha.service.pedidos.descricao.impl;

import br.com.ages.adoteumamanha.service.pedidos.descricao.BuscarDescricaoPedidoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static br.com.ages.adoteumamanha.domain.enumeration.Perfil.ADMIN;
import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.DOACAO;
import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.NECESSIDADE;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BuscarDescricaoPedidoComoAdminServiceTest {

    @InjectMocks
    private BuscarDescricaoPedidoComoAdminService service;

    @Mock
    private BuscarDescricaoPedidoService buscarDescricaoPedidoService;

    @Test
    public void getPerfil() {
        assertEquals(ADMIN, service.getPerfil());
    }

    @Test
    public void getTipoPedidos() {
        assertEquals(asList(DOACAO, NECESSIDADE), service.getTipoPedidos());

    }

    @Test
    public void ok() {

        final Long idPedido = 1L;
        service.executar(idPedido, null);

        verify(buscarDescricaoPedidoService).buscar(idPedido, service.getTipoPedidos(), null);

    }

}