package br.com.ages.adoteumamanha.service.pedidos;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.Status;
import br.com.ages.adoteumamanha.repository.PedidoRepository;
import br.com.ages.adoteumamanha.validator.StatusPedidoValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static br.com.ages.adoteumamanha.domain.enumeration.Status.PENDENTE;
import static br.com.ages.adoteumamanha.fixture.Fixture.make;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RecusarPedidoServiceTest {

    @InjectMocks
    private RecusarPedidoService service;

    @Mock
    private PedidoRepository repository;

    @Mock
    private StatusPedidoValidator statusPedidoValidator;

    @Mock
    private BuscarPedidoService buscarPedidoService;

    @Captor
    private ArgumentCaptor<Pedido> captor;

    @Test
    public void testeRecusarPedido() {

        final Long idPedido = 1L;

        final Usuario usuario = make(new Usuario());

        final Pedido pedido = make(Pedido.builder())
                .withId(idPedido)
                .withStatus(PENDENTE)
                .withUsuario(usuario)
                .build();

        final String motivoRecusa = "Inválido";

        var pedidoEsperado = pedido.toBuilder()
                .withStatus(Status.RECUSADO)
                .withMotivoRecusa(motivoRecusa)
                .build();

        when(buscarPedidoService.buscarPorID(idPedido)).thenReturn(pedido);
        doNothing().when(statusPedidoValidator).validar(pedido);

        service.recusar(idPedido, motivoRecusa);
        verify(repository).save(captor.capture());

        var pedidoSalvo = captor.getValue();

        assertThat(pedidoSalvo).usingRecursiveComparison().isEqualTo(pedidoEsperado);
    }

}