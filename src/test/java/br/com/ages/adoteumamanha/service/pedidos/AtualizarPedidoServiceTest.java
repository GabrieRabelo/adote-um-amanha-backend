package br.com.ages.adoteumamanha.service.pedidos;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.dto.request.AtualizarPedidoRequest;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.repository.PedidoRepository;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.validator.StatusPedidoValidator;
import br.com.ages.adoteumamanha.validator.UsuarioCriadorPedidoValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static br.com.ages.adoteumamanha.domain.enumeration.Status.PENDENTE;
import static br.com.ages.adoteumamanha.fixture.Fixture.make;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AtualizarPedidoServiceTest {

    @InjectMocks
    private AtualizarPedidoService service;

    @Mock
    private PedidoRepository repository;

    @Mock
    private StatusPedidoValidator statusPedidoValidator;

    @Mock
    private BuscarPedidoService buscarPedidoService;

    @Mock
    private UsuarioCriadorPedidoValidator usuarioCriadorPedidoValidator;

    private UserPrincipal userPrincipal;

    @Captor
    private ArgumentCaptor<Pedido> pedidoArgumentCaptor;

    @Before
    public void setup() {
        userPrincipal = UserPrincipal.create(make(Usuario.builder()).build());
    }

    @Test
    public void atualizar() {

        final Long idPedido = 1L;
        final Long idUsuario = userPrincipal.getId();

        final AtualizarPedidoRequest request = make(AtualizarPedidoRequest.builder().build());

        final Usuario usuario = make(Usuario.builder())
                .withId(idUsuario)
                .build();

        final Pedido pedido = make(Pedido.builder())
                .withId(idPedido)
                .withStatus(PENDENTE)
                .withUsuario(usuario)
                .build();

        when(buscarPedidoService.buscarPorID(idPedido)).thenReturn(pedido);

        service.atualizar(idPedido, request, userPrincipal);

        verify(statusPedidoValidator).validar(pedido);
        verify(usuarioCriadorPedidoValidator).validar(idUsuario, pedido.getUsuario().getId());
        verify(repository).save(pedidoArgumentCaptor.capture());

        assertEquals(request.getAssunto(), pedidoArgumentCaptor.getValue().getAssunto());
        assertEquals(request.getDescricao(), pedidoArgumentCaptor.getValue().getDescricao());
        assertEquals(request.getSubcategoria(), pedidoArgumentCaptor.getValue().getSubcategoria());
        assertEquals(request.getCategoria(), pedidoArgumentCaptor.getValue().getCategoria());
        assertEquals(request.getUrlVideo(), pedidoArgumentCaptor.getValue().getUrlVideo());
    }

    @Test(expected = ApiException.class)
    public void atualizar_pedido_request_invalido() {

        final Long idNecessidade = 1L;

        try {
            service.atualizar(idNecessidade, null, userPrincipal);
        } finally {
            verifyNoInteractions(repository, buscarPedidoService, statusPedidoValidator, usuarioCriadorPedidoValidator);
        }
    }
}
