package br.com.ages.adoteumamanha.service.match;

import br.com.ages.adoteumamanha.domain.entity.Match;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.Status;
import br.com.ages.adoteumamanha.dto.request.RecusarMatchRequest;
import br.com.ages.adoteumamanha.repository.MatchRepository;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static br.com.ages.adoteumamanha.domain.enumeration.Status.PENDENTE;
import static br.com.ages.adoteumamanha.domain.enumeration.Status.RECUSADO;
import static br.com.ages.adoteumamanha.fixture.Fixture.make;
import static java.util.Optional.of;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RecusarMatchServiceTest {

    @InjectMocks
    private RecusarMatchService service;

    @Mock
    private MatchRepository repository;

    @Captor
    private ArgumentCaptor<Match> argumentCaptor;

    private UserPrincipal userPrincipal;

    @Before
    public void setup() {
        userPrincipal = UserPrincipal.create(make(Usuario.builder()).build());
    }

    @Test
    public void ok() {

        final Long idMatch = 1L;
        final RecusarMatchRequest request = make(RecusarMatchRequest.builder()).build();
        final Match match = make(Match.builder())
                .withStatus(Status.PENDENTE)
                .build();

        when(repository.findById(idMatch)).thenReturn(of(match));

        service.recusar(idMatch, userPrincipal, request);

        verify(repository).findById(idMatch);
        verify(repository).save(argumentCaptor.capture());

        final Match matchSalvo = argumentCaptor.getValue();

        assertEquals(RECUSADO, matchSalvo.getStatus());
        assertEquals(PENDENTE, matchSalvo.getDoacao().getStatus());
        assertEquals(PENDENTE, matchSalvo.getNecessidade().getStatus());
        assertEquals(matchSalvo.getFinalizadoPor(), userPrincipal.getEmail());
        assertNotNull(matchSalvo.getDataFechamento());
    }
}