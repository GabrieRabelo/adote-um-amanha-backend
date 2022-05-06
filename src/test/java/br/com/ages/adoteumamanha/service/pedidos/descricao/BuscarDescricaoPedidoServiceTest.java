package br.com.ages.adoteumamanha.service.pedidos.descricao;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.Perfil;
import br.com.ages.adoteumamanha.domain.enumeration.TipoPedido;
import br.com.ages.adoteumamanha.dto.response.DescricaoPedidoResponse;
import br.com.ages.adoteumamanha.mapper.DescricaoPedidoResponseMapper;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.service.pedidos.BuscarPedidoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static br.com.ages.adoteumamanha.domain.enumeration.Perfil.*;
import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.NECESSIDADE;
import static br.com.ages.adoteumamanha.fixture.Fixture.make;
import static java.util.Collections.singletonList;
import static org.mockito.Mockito.*;
import static org.testcontainers.shaded.org.apache.commons.lang.math.RandomUtils.nextLong;

@RunWith(MockitoJUnitRunner.class)
public class BuscarDescricaoPedidoServiceTest {

    @InjectMocks
    private BuscarDescricaoPedidoService service;

    @Mock
    private BuscarPedidoService buscarPedidoService;

    @Mock
    private DescricaoPedidoResponseMapper mapper;

    @Test
    public void ok() {

        final Long idPedido = nextLong();
        final List<TipoPedido> tipoPedidos = singletonList(NECESSIDADE);
        final UserPrincipal userPrincipal = UserPrincipal.create(make(Usuario.builder())
                .withPerfil(CASA)
                .build());

        final Pedido pedido = make(Pedido.builder()).build();

        when(buscarPedidoService.buscarPorIdPedidoTipoPedidosUsuario(idPedido, tipoPedidos, userPrincipal)).thenReturn(pedido);

        service.buscar(idPedido, tipoPedidos, userPrincipal);

        verify(buscarPedidoService).buscarPorIdPedidoTipoPedidosUsuario(idPedido, tipoPedidos, userPrincipal);
        verify(mapper).apply(any(Pedido.class));

    }

}