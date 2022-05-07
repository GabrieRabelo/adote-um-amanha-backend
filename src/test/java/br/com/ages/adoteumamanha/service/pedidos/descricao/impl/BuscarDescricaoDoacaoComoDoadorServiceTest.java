package br.com.ages.adoteumamanha.service.pedidos.descricao.impl;

import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.service.pedidos.descricao.BuscarDescricaoPedidoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static br.com.ages.adoteumamanha.domain.enumeration.Perfil.DOADOR;
import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.DOACAO;
import static br.com.ages.adoteumamanha.fixture.Fixture.make;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BuscarDescricaoDoacaoComoDoadorServiceTest {

    @InjectMocks
    private BuscarDescricaoDoacaoComoDoadorService service;

    @Mock
    private BuscarDescricaoPedidoService buscarDescricaoPedidoService;

    @Test
    public void getPerfil() {
        assertEquals(DOADOR, service.getPerfil());
    }

    @Test
    public void getTipoPedidos() {
        assertEquals(asList(DOACAO), service.getTipoPedidos());

    }

    @Test
    public void ok() {

        final Long idPedido = 1L;
        final UserPrincipal userPrincipal = UserPrincipal.create(make(Usuario.builder()).build());
        service.executar(idPedido, userPrincipal);

        verify(buscarDescricaoPedidoService).buscar(idPedido, service.getTipoPedidos(), userPrincipal);

    }

}