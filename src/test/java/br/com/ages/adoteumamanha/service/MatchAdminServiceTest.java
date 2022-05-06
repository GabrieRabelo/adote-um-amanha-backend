package br.com.ages.adoteumamanha.service;

import br.com.ages.adoteumamanha.domain.entity.Match;
import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.fixture.Fixture;
import br.com.ages.adoteumamanha.mapper.MatchMapper;
import br.com.ages.adoteumamanha.repository.MatchRepository;
import br.com.ages.adoteumamanha.repository.PedidoRepository;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.validator.MatchValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.DOACAO;
import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.NECESSIDADE;
import static java.util.Optional.of;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.testcontainers.shaded.org.apache.commons.lang.math.RandomUtils.nextLong;

@RunWith(MockitoJUnitRunner.class)
public class MatchAdminServiceTest {

    @InjectMocks
    private MatchAdminService service;

    @Mock
    private MatchRepository repository;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private MatchValidator matchValidator;

    @Mock
    private MatchMapper matchMapper;

    private UserPrincipal userPrincipal;

    @Captor
    private ArgumentCaptor<Match> matchArgumentCaptor;

    @Before
    public void setup() {
        userPrincipal = UserPrincipal.create(Fixture.make(Usuario.builder()).build());
    }

    @Test
    public void match_ok() {

        final Long idNecessidade = nextLong();
        final Pedido necessidade = Fixture.make(Pedido.builder())
                .withId(idNecessidade)
                .withTipoPedido(NECESSIDADE)
                .build();

        final Long idDoacao = nextLong();
        final Pedido doacao = Fixture.make(Pedido.builder())
                .withId(idDoacao)
                .withTipoPedido(DOACAO)
                .build();

        when(pedidoRepository.findById(anyLong())).thenReturn(of(doacao)).thenReturn(of(necessidade));
        when(matchMapper.apply(userPrincipal, doacao, necessidade)).thenCallRealMethod();

        service.cadastrar(idDoacao, idNecessidade, userPrincipal);

        verify(matchValidator).validate(matchArgumentCaptor.capture());
        verify(pedidoRepository, atLeastOnce()).findById(eq(idDoacao));
        verify(pedidoRepository, atLeastOnce()).findById(eq(idNecessidade));
        verify(matchMapper).apply(userPrincipal, doacao, necessidade);
        verify(repository).save(matchArgumentCaptor.getValue());

        assertEquals(DOACAO, matchArgumentCaptor.getValue().getDoacao().getTipoPedido());
        assertEquals(NECESSIDADE, matchArgumentCaptor.getValue().getNecessidade().getTipoPedido());
        assertEquals(idNecessidade, matchArgumentCaptor.getValue().getNecessidade().getId());
        assertEquals(idDoacao, matchArgumentCaptor.getValue().getDoacao().getId());

    }
}