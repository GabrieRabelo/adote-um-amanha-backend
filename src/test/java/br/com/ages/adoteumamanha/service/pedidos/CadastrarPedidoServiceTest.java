package br.com.ages.adoteumamanha.service.pedidos;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.mapper.PedidoMapper;
import br.com.ages.adoteumamanha.repository.PedidoRepository;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.validator.CadastrarPedidoRequestValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static br.com.ages.adoteumamanha.fixture.Fixture.make;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CadastrarPedidoServiceTest {

    @InjectMocks
    private CadastrarPedidoService service;

    @Mock
    private PedidoMapper pedidoMapper;

    @Mock
    private PedidoRepository repository;

    @Mock
    private CadastrarPedidoRequestValidator validator;

    @Captor
    private ArgumentCaptor<Pedido> pedidoEntityArgumentCaptor;

    private UserPrincipal userPrincipal;

    @Before
    public void setup() {
        userPrincipal = UserPrincipal.create(make(Usuario.builder()).build());
    }

    @Test
    public void cadastrar_ok() {
        final CadastrarPedidoRequest request = make(CadastrarPedidoRequest.builder()).build();
        when(pedidoMapper.apply(request, userPrincipal)).thenCallRealMethod();

        service.cadastrar(request, userPrincipal);

        verify(validator).validar(request);
        verify(pedidoMapper).apply(request, userPrincipal);
        verify(repository).save(pedidoEntityArgumentCaptor.capture());

        final Pedido entity = pedidoEntityArgumentCaptor.getValue();

        assertEquals(request.getAssunto(), entity.getAssunto());
        assertEquals(request.getDescricao(), entity.getDescricao());
        assertEquals(request.getCategoria(), entity.getCategoria());
        assertEquals(request.getSubcategoria(), entity.getSubcategoria());
        assertEquals(LocalDate.now().getYear(), entity.getDataHora().getYear());
        assertEquals(LocalDate.now().getMonth(), entity.getDataHora().getMonth());
        assertEquals(LocalDate.now().getDayOfWeek(), entity.getDataHora().getDayOfWeek());
        assertEquals(userPrincipal.getId(), entity.getUsuario().getId());
    }

}