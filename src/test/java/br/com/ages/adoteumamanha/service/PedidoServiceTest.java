package br.com.ages.adoteumamanha.service;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.TipoPedido;
import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.fixture.Fixture;
import br.com.ages.adoteumamanha.mapper.NecessidadesResponseMapper;
import br.com.ages.adoteumamanha.mapper.PedidoMapper;
import br.com.ages.adoteumamanha.repository.PedidoRepository;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.validator.CadastrarPedidoRequestValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PedidoServiceTest {

    @InjectMocks
    private PedidoService service;

    @Mock
    private PedidoRepository repository;

    @Mock
    private PedidoMapper pedidoMapper;

    @Mock
    private NecessidadesResponseMapper necessidadesResponseMapper;

    @Mock
    private CadastrarPedidoRequestValidator validator;

    private CadastrarPedidoRequest request;

    private UserPrincipal userPrincipal;

    @Captor
    private ArgumentCaptor<Pedido> pedidoEntityArgumentCaptor;


    @Before
    public void setup() {
        userPrincipal = UserPrincipal.create(Fixture.make(Usuario.builder()).build());
    }

    @Test
    public void cadastrar_ok() {
        request = Fixture.make(CadastrarPedidoRequest.builder()).build();

        when(pedidoMapper.apply(request, userPrincipal)).thenCallRealMethod();

        service.cadastrar(request, userPrincipal);

        verify(validator).validate(request);
        verify(pedidoMapper).apply(request, userPrincipal);

        verify(repository).save(pedidoEntityArgumentCaptor.capture());

        final Pedido entity = pedidoEntityArgumentCaptor.getValue();

        Assert.assertEquals(request.getAssunto(), entity.getAssunto());
        Assert.assertEquals(request.getDescricao(), entity.getDescricao());
        Assert.assertEquals(request.getCategoria(), entity.getCategoria());
        Assert.assertEquals(request.getSubcategoria(), entity.getSubcategoria());
        Assert.assertEquals(LocalDate.now().getYear(), entity.getDataHora().getYear());
        Assert.assertEquals(LocalDate.now().getMonth(), entity.getDataHora().getMonth());
        Assert.assertEquals(LocalDate.now().getDayOfWeek(), entity.getDataHora().getDayOfWeek());
        Assert.assertEquals(userPrincipal.getId(), entity.getUsuario().getId());

    }

    @Test(expected = ApiException.class)
    public void cadastrar_validator_erro() {

        doThrow(ApiException.class).when(validator).validate(request);

        service.cadastrar(request, userPrincipal);

        verifyNoInteractions(pedidoMapper, repository);
    }

    @Test
    public void listar_necessidade_ok() {

        final Integer pagina = 0;
        final Integer tamanho = 5;
        final Pageable paging = PageRequest.of(pagina, tamanho);
        final Page<Pedido> pedidoEntityPage = mock(Page.class);

        when(repository.findAllByTipoPedido(TipoPedido.NECESSIDADE, paging)).thenReturn(pedidoEntityPage);

        service.listarNecessidades(pagina, tamanho);

        verify(necessidadesResponseMapper).apply(pedidoEntityPage);
        verify(repository).findAllByTipoPedido(eq(TipoPedido.NECESSIDADE), eq(paging));
    }
}