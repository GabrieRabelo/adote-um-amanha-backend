package br.com.ages.adoteumamanha.service.pedidos.descricao;

import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.service.pedidos.descricao.impl.BuscarDescricaoDoacaoComoDoadorService;
import br.com.ages.adoteumamanha.service.pedidos.descricao.impl.BuscarDescricaoNecessidadeComoCasaService;
import br.com.ages.adoteumamanha.service.pedidos.descricao.impl.BuscarDescricaoNecessidadeComoDoadorService;
import br.com.ages.adoteumamanha.service.pedidos.descricao.impl.BuscarDescricaoPedidoComoAdminService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static br.com.ages.adoteumamanha.domain.enumeration.Perfil.*;
import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.DOACAO;
import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.NECESSIDADE;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BuscarDescricaoPedidoStrategyTest {

    @InjectMocks
    private BuscarDescricaoPedidoStrategy buscarDescricaoPedidoStrategy;

    @Mock
    private BuscarDescricaoDoacaoComoDoadorService buscarDescricaoDoacaoComoDoadorService;

    @Mock
    private BuscarDescricaoNecessidadeComoCasaService buscarDescricaoNecessidadeComoCasaService;

    @Mock
    private BuscarDescricaoNecessidadeComoDoadorService buscarDescricaoNecessidadeComoDoadorService;

    @Mock
    private BuscarDescricaoPedidoComoAdminService buscarDescricaoPedidoComoAdminService;

    @Before
    public void setUp() {

        when(buscarDescricaoDoacaoComoDoadorService.getPerfil()).thenReturn(DOADOR);
        when(buscarDescricaoDoacaoComoDoadorService.getTipoPedidos()).thenReturn(asList(DOACAO));

        when(buscarDescricaoNecessidadeComoCasaService.getPerfil()).thenReturn(CASA);
        when(buscarDescricaoNecessidadeComoCasaService.getTipoPedidos()).thenReturn(asList(NECESSIDADE));

        when(buscarDescricaoNecessidadeComoDoadorService.getPerfil()).thenReturn(DOADOR);
        when(buscarDescricaoNecessidadeComoDoadorService.getTipoPedidos()).thenReturn(asList(NECESSIDADE));

        when(buscarDescricaoPedidoComoAdminService.getPerfil()).thenReturn(ADMIN);
        when(buscarDescricaoPedidoComoAdminService.getTipoPedidos()).thenReturn(asList(DOACAO, NECESSIDADE));

        ReflectionTestUtils.setField(buscarDescricaoPedidoStrategy, "services",
                asList(buscarDescricaoDoacaoComoDoadorService, buscarDescricaoNecessidadeComoCasaService,
                        buscarDescricaoNecessidadeComoDoadorService, buscarDescricaoPedidoComoAdminService));
    }

    @Test
    public void doador_doacao() {

        final BuscarDescricaoPedido service = buscarDescricaoPedidoStrategy.run(DOADOR, DOACAO);

        assertEquals(service.getPerfil(), DOADOR);
        assertEquals(service.getTipoPedidos(), asList(DOACAO));
    }

    @Test
    public void casa_necessidade() {

        final BuscarDescricaoPedido service = buscarDescricaoPedidoStrategy.run(CASA, NECESSIDADE);

        assertEquals(service.getPerfil(), CASA);
        assertEquals(service.getTipoPedidos(), asList(NECESSIDADE));
    }

    @Test
    public void doador_necessidade() {

        final BuscarDescricaoPedido service = buscarDescricaoPedidoStrategy.run(DOADOR, NECESSIDADE);

        assertEquals(service.getPerfil(), DOADOR);
        assertEquals(service.getTipoPedidos(), asList(NECESSIDADE));
    }

    @Test
    public void admin_doacao() {

        final BuscarDescricaoPedido service = buscarDescricaoPedidoStrategy.run(ADMIN, DOACAO);

        assertEquals(service.getPerfil(), ADMIN);
        assertTrue(service.getTipoPedidos().contains(DOACAO));
    }

    @Test
    public void admin_necessidade() {

        final BuscarDescricaoPedido service = buscarDescricaoPedidoStrategy.run(ADMIN, NECESSIDADE);

        assertEquals(service.getPerfil(), ADMIN);
        assertTrue(service.getTipoPedidos().contains(NECESSIDADE));
    }

    @Test(expected = ApiException.class)
    public void erro_operacao_invalida_casa_doacao() {
        buscarDescricaoPedidoStrategy.run(CASA, DOACAO);
    }
}
