package br.com.ages.adoteumamanha.service.pedidos.filtros;

import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.service.pedidos.filtros.impl.*;
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
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BuscarPedidosComFiltrosStrategyTest {

    @InjectMocks
    private BuscarPedidosComFiltrosStrategy buscarPedidosComFiltrosStrategy;

    @Mock
    private BuscarNecessidadesComoCasaService buscarNecessidadesComoCasaService;

    @Mock
    private BuscarNecessidadesComoDoadorService buscarNecessidadesComoDoadorService;

    @Mock
    private BuscarNecessidadesComoAdminService buscarNecessidadesComoAdminService;

    @Mock
    private BuscarDoacoesComoAdminService buscarDoacoesComoAdminService;

    @Mock
    private BuscarDoacoesComoDoadorService buscarDoacoesComoDoadorService;


    @Before
    public void setUp() {

        when(buscarNecessidadesComoCasaService.getPerfil()).thenReturn(CASA);
        when(buscarNecessidadesComoCasaService.getTipoPedido()).thenReturn(NECESSIDADE);

        when(buscarNecessidadesComoDoadorService.getPerfil()).thenReturn(DOADOR);
        when(buscarNecessidadesComoDoadorService.getTipoPedido()).thenReturn(NECESSIDADE);

        when(buscarNecessidadesComoAdminService.getPerfil()).thenReturn(ADMIN);
        when(buscarNecessidadesComoAdminService.getTipoPedido()).thenReturn(NECESSIDADE);

        when(buscarDoacoesComoAdminService.getPerfil()).thenReturn(ADMIN);
        when(buscarDoacoesComoAdminService.getTipoPedido()).thenReturn(DOACAO);

        when(buscarDoacoesComoDoadorService.getPerfil()).thenReturn(DOADOR);
        when(buscarDoacoesComoDoadorService.getTipoPedido()).thenReturn(DOACAO);

        ReflectionTestUtils.setField(buscarPedidosComFiltrosStrategy, "services",
                asList(buscarNecessidadesComoCasaService, buscarNecessidadesComoDoadorService,
                        buscarNecessidadesComoAdminService, buscarDoacoesComoAdminService,
                        buscarDoacoesComoDoadorService));
    }

    @Test
    public void casa_necessidade() {

        final BuscarPedidosImplementacaoFiltros service = buscarPedidosComFiltrosStrategy.run(CASA, NECESSIDADE);

        assertEquals(service.getPerfil(), CASA);
        assertEquals(service.getTipoPedido(), NECESSIDADE);
    }

    @Test
    public void doador_necessidade() {

        final BuscarPedidosImplementacaoFiltros service = buscarPedidosComFiltrosStrategy.run(DOADOR, NECESSIDADE);

        assertEquals(service.getPerfil(), DOADOR);
        assertEquals(service.getTipoPedido(), NECESSIDADE);
    }

    @Test
    public void admin_necessidade() {

        final BuscarPedidosImplementacaoFiltros service = buscarPedidosComFiltrosStrategy.run(ADMIN, NECESSIDADE);

        assertEquals(service.getPerfil(), ADMIN);
        assertEquals(service.getTipoPedido(), NECESSIDADE);
    }

    @Test
    public void admin_doacao() {

        final BuscarPedidosImplementacaoFiltros service = buscarPedidosComFiltrosStrategy.run(ADMIN, DOACAO);

        assertEquals(service.getPerfil(), ADMIN);
        assertEquals(service.getTipoPedido(), DOACAO);
    }

    @Test
    public void doador_doacao() {

        final BuscarPedidosImplementacaoFiltros service = buscarPedidosComFiltrosStrategy.run(DOADOR, DOACAO);

        assertEquals(service.getPerfil(), DOADOR);
        assertEquals(service.getTipoPedido(), DOACAO);
    }

    @Test(expected = ApiException.class)
    public void casa_doacao() {

        final BuscarPedidosImplementacaoFiltros service = buscarPedidosComFiltrosStrategy.run(CASA, DOACAO);

        assertEquals(service.getPerfil(), DOADOR);
        assertEquals(service.getTipoPedido(), DOACAO);
    }


}