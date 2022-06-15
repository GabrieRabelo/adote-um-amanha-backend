package br.com.ages.adoteumamanha.service.match;

import br.com.ages.adoteumamanha.domain.entity.Match;
import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.fixture.Fixture;
import br.com.ages.adoteumamanha.mapper.DescricaoMatchResponseMapper;
import br.com.ages.adoteumamanha.mapper.MatchMapper;
import br.com.ages.adoteumamanha.repository.MatchRepository;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.service.pedidos.BuscarPedidoService;
import br.com.ages.adoteumamanha.validator.MatchValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static br.com.ages.adoteumamanha.domain.enumeration.Perfil.ADMIN;
import static br.com.ages.adoteumamanha.domain.enumeration.Status.FINALIZADA;
import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.DOACAO;
import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.NECESSIDADE;
import static br.com.ages.adoteumamanha.exception.Mensagem.REQUEST_INVALIDO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MatchAdminServiceTest {

    @InjectMocks
    private MatchAdminService service;

    @Mock
    private BuscarPedidoService buscarPedidoService;

    @Mock
    private MatchValidator matchValidator;

    @Mock
    private MatchMapper matchMapper;

    @Mock
    private DescricaoMatchResponseMapper descricaoMatchResponseMapper;

    @Mock
    private MatchRepository matchRepository;

    private UserPrincipal userPrincipal;

    @Captor
    private ArgumentCaptor<Match> matchArgumentCaptor;

    @Before
    public void setup() {
        userPrincipal = UserPrincipal.create(Fixture.make(Usuario.builder())
                .withPerfil(ADMIN)
                .build());
    }

    @Test
    public void request_invalida_idDoacao() {

        try {
            service.cadastrar(null, 1L, null);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(REQUEST_INVALIDO.getDescricao()));
            verifyNoInteractions(matchRepository, matchMapper, matchValidator, buscarPedidoService, descricaoMatchResponseMapper);
        }
    }


    @Test
    public void request_invalida_idNecessidade() {

        try {
            service.cadastrar(1L, null, null);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(REQUEST_INVALIDO.getDescricao()));
            verifyNoInteractions(matchRepository, matchMapper, matchValidator, buscarPedidoService, descricaoMatchResponseMapper);
        }
    }

    @Test
    public void match_ok() {

        final Long idNecessidade = 1L;
        final Pedido necessidade = Fixture.make(Pedido.builder())
                .withId(idNecessidade)
                .withTipoPedido(NECESSIDADE)
                .build();

        final Long idDoacao = 2L;
        final Pedido doacao = Fixture.make(Pedido.builder())
                .withId(idDoacao)
                .withTipoPedido(DOACAO)
                .build();

        when(buscarPedidoService.buscarPorID(anyLong())).thenReturn(doacao).thenReturn(necessidade);
        when(matchMapper.apply(userPrincipal, doacao, necessidade)).thenCallRealMethod();

        service.cadastrar(idDoacao, idNecessidade, userPrincipal);

        verify(matchValidator).validar(matchArgumentCaptor.capture());
        verify(buscarPedidoService, atLeastOnce()).buscarPorID(eq(idDoacao));
        verify(buscarPedidoService, atLeastOnce()).buscarPorID(eq(idNecessidade));
        verify(matchMapper).apply(userPrincipal, doacao, necessidade);
        verify(matchRepository).save(any(Match.class));

        assertEquals(DOACAO, matchArgumentCaptor.getValue().getDoacao().getTipoPedido());
        assertEquals(NECESSIDADE, matchArgumentCaptor.getValue().getNecessidade().getTipoPedido());
        assertEquals(idNecessidade, matchArgumentCaptor.getValue().getNecessidade().getId());
        assertEquals(idDoacao, matchArgumentCaptor.getValue().getDoacao().getId());
        assertEquals(FINALIZADA, matchArgumentCaptor.getValue().getNecessidade().getStatus());
        assertEquals(FINALIZADA, matchArgumentCaptor.getValue().getNecessidade().getStatus());
        assertEquals(FINALIZADA, matchArgumentCaptor.getValue().getStatus());
    }
}