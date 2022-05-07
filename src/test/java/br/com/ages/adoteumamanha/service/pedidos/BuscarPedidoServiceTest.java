package br.com.ages.adoteumamanha.service.pedidos;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.TipoPedido;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.repository.PedidoRepository;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static br.com.ages.adoteumamanha.fixture.Fixture.make;
import static java.util.Optional.of;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BuscarPedidoServiceTest {

    @InjectMocks
    private BuscarPedidoService service;

    @Mock
    private PedidoRepository repository;

    private UserPrincipal userPrincipal;

    @Before
    public void setup() {
        userPrincipal = UserPrincipal.create(make(Usuario.builder()).build());
    }


    @Test
    public void buscar_por_id_ok() {
        final Long id = 1L;

        when(repository.findById(id)).thenReturn(of(Pedido.builder().build()));
        service.buscarPorID(id);

        verify(repository).findById(id);
    }

    @Test(expected = ApiException.class)
    public void buscar_por_id_erro() {

        try {
            service.buscarPorID(null);
        } finally {
            verify(repository).findById(null);
        }
    }

    @Test
    public void buscar_por_id_tipo_pedidos_usuario_ok() {
        final Long idPedido = 1L;
        final List<TipoPedido> tipoPedidos = List.of(TipoPedido.values());
        when(repository.findByIdPedidoAndTipoPedidoAndIdUsuario(idPedido, tipoPedidos, userPrincipal.getId())).thenReturn(of(Pedido.builder().build()));
        service.buscarPorIdPedidoTipoPedidosUsuario(idPedido, tipoPedidos, userPrincipal);

        verify(repository).findByIdPedidoAndTipoPedidoAndIdUsuario(idPedido, tipoPedidos, userPrincipal.getId());
    }

    @Test(expected = ApiException.class)
    public void buscar_por_id_tipo_pedidos_usuario_erro() {

        try {
            service.buscarPorIdPedidoTipoPedidosUsuario(null, null, null);
        } finally {
            verify(repository).findByIdPedidoAndTipoPedidoAndIdUsuario(null, null, null);
        }
    }
}