package br.com.ages.adoteumamanha.service.match;

import br.com.ages.adoteumamanha.domain.entity.Match;
import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.fixture.Fixture;
import br.com.ages.adoteumamanha.mapper.MatchMapper;
import br.com.ages.adoteumamanha.mapper.PedidoMapper;
import br.com.ages.adoteumamanha.repository.MatchRepository;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.service.pedidos.BuscarPedidoService;
import br.com.ages.adoteumamanha.validator.CadastrarPedidoRequestValidator;
import br.com.ages.adoteumamanha.validator.MatchValidator;
import br.com.ages.adoteumamanha.validator.VinculacaoDoacaoNecessidadeMatchValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static br.com.ages.adoteumamanha.domain.enumeration.Status.MATCH_PENDENTE;
import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.DOACAO;
import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.NECESSIDADE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MatchDoadorServiceTest {

    @InjectMocks
    private MatchDoadorService service;

    @Mock
    private MatchRepository repository;

    @Mock
    private BuscarPedidoService buscarPedidoService;

    @Mock
    private PedidoMapper pedidoMapper;

    @Mock
    private CadastrarPedidoRequestValidator validator;

    @Mock
    private VinculacaoDoacaoNecessidadeMatchValidator vinculacaoDoacaoNecessidadeMatchValidator;

    @Mock
    private MatchMapper matchMapper;

    @Mock
    private MatchValidator matchValidator;

    private CadastrarPedidoRequest request;

    private UserPrincipal userPrincipal;

    @Captor
    private ArgumentCaptor<Match> matchArgumentCaptor;

    @Before
    public void setup() {
        userPrincipal = UserPrincipal.create(Fixture.make(Usuario.builder()).build());
    }

    @Test
    public void match_ok() {

        final Long idNecessidade = 1L;
        final Pedido necessidade = Fixture.make(Pedido.builder())
                .withTipoPedido(NECESSIDADE)
                .build();

        request = Fixture.make(CadastrarPedidoRequest.builder()).build();

        final Pedido doacao = Fixture.make(Pedido.builder())
                .withTipoPedido(DOACAO)
                .withAssunto(request.getAssunto())
                .withDescricao(request.getDescricao())
                .withCategoria(request.getCategoria())
                .withSubcategoria(request.getSubcategoria())
                .build();

        when(pedidoMapper.apply(request, userPrincipal)).thenReturn(doacao);
        when(matchMapper.apply(userPrincipal, doacao, necessidade)).thenCallRealMethod();
        when(buscarPedidoService.buscarPorID(idNecessidade)).thenReturn(necessidade);

        service.cadastrar(userPrincipal, idNecessidade, request);

        verify(matchValidator).validar(matchArgumentCaptor.capture());
        verify(validator).validar(request);
        verify(repository).save(matchArgumentCaptor.getValue());
        verify(vinculacaoDoacaoNecessidadeMatchValidator).validar(necessidade.getTipoPedido());

        assertEquals(DOACAO, matchArgumentCaptor.getValue().getDoacao().getTipoPedido());
        assertEquals(NECESSIDADE, matchArgumentCaptor.getValue().getNecessidade().getTipoPedido());
        assertEquals(MATCH_PENDENTE, matchArgumentCaptor.getValue().getNecessidade().getStatus());
        assertEquals(MATCH_PENDENTE, matchArgumentCaptor.getValue().getNecessidade().getStatus());
        assertEquals(MATCH_PENDENTE, matchArgumentCaptor.getValue().getStatus());

    }
}