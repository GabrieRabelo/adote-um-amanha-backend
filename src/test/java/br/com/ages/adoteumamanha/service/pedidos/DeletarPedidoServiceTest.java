package br.com.ages.adoteumamanha.service.pedidos;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.repository.PedidoRepository;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.validator.StatusPedidoValidator;
import br.com.ages.adoteumamanha.validator.UsuarioCriadorPedidoValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static br.com.ages.adoteumamanha.domain.enumeration.Status.PENDENTE;
import static br.com.ages.adoteumamanha.fixture.Fixture.make;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeletarPedidoServiceTest {

    @InjectMocks
    private DeletarPedidoService service;

    @Mock
    private PedidoRepository repository;

    @Mock
    private StatusPedidoValidator statusPedidoValidator;

    @Mock
    private BuscarPedidoService buscarPedidoService;

    @Mock
    private UsuarioCriadorPedidoValidator usuarioCriadorPedidoValidator;

    private UserPrincipal userPrincipal;

    @Before
    public void setup() {
        userPrincipal = UserPrincipal.create(make(Usuario.builder()).build());
    }

    @Test
    public void deletar_ok() {

        final Long idPedido = 1L;
        final Long idUsuario = userPrincipal.getId();

        final Usuario usuario = make(Usuario.builder())
                .withId(idUsuario)
                .build();

        final Pedido pedido = make(Pedido.builder())
                .withId(idPedido)
                .withStatus(PENDENTE)
                .withUsuario(usuario)
                .build();

        when(buscarPedidoService.buscarPorID(idPedido)).thenReturn(pedido);

        service.deletar(idPedido, userPrincipal);

        verify(statusPedidoValidator).validar(pedido);
        verify(usuarioCriadorPedidoValidator).validar(idUsuario, pedido.getUsuario().getId());
        verify(buscarPedidoService).buscarPorID(idPedido);
        verify(repository).delete(any(Pedido.class));
    }

}