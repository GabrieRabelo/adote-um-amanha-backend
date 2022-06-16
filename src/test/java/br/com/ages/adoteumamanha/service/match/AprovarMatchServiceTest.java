package br.com.ages.adoteumamanha.service.match;

import br.com.ages.adoteumamanha.domain.entity.Match;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.Status;
import br.com.ages.adoteumamanha.exception.ApiException;
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

import static br.com.ages.adoteumamanha.domain.enumeration.Status.FINALIZADA;
import static br.com.ages.adoteumamanha.exception.Mensagem.MATCH_FINALIZADA_OU_RECUSADA;
import static br.com.ages.adoteumamanha.fixture.Fixture.make;
import static java.util.Optional.of;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AprovarMatchServiceTest {

    @InjectMocks
    private AprovarMatchService service;

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
        final Match match = make(Match.builder())
                .withStatus(Status.PENDENTE)
                .build();

        when(repository.findById(idMatch)).thenReturn(of(match));

        service.aprovar(idMatch, userPrincipal);

        verify(repository).findById(idMatch);
        verify(repository).save(argumentCaptor.capture());

        final Match matchSalvo = argumentCaptor.getValue();

        assertEquals(FINALIZADA, matchSalvo.getStatus());
        assertEquals(FINALIZADA, matchSalvo.getDoacao().getStatus());
        assertEquals(FINALIZADA, matchSalvo.getNecessidade().getStatus());
        assertEquals(matchSalvo.getFinalizadoPor(), userPrincipal.getEmail());
        assertNotNull(matchSalvo.getDataFechamento());
    }

    @Test
    public void match_finalizado() {

        final Long idMatch = 1L;
        final Match match = make(Match.builder())
                .withStatus(FINALIZADA)
                .build();

        when(repository.findById(idMatch)).thenReturn(of(match));

        try {
            service.aprovar(idMatch, userPrincipal);

        } catch (Exception e) {
            assertTrue(e.getMessage().contains(MATCH_FINALIZADA_OU_RECUSADA.getDescricao()));
            verify(repository, times(0)).save(any());
        }
    }

    @Test
    public void match_recusado() {

        final Long idMatch = 1L;
        final Match match = make(Match.builder())
                .withStatus(Status.RECUSADO)
                .build();

        when(repository.findById(idMatch)).thenReturn(of(match));

        try {
            service.aprovar(idMatch, userPrincipal);

        } catch (Exception e) {
            assertTrue(e.getMessage().contains(MATCH_FINALIZADA_OU_RECUSADA.getDescricao()));
            verify(repository, times(0)).save(any());
        }
    }

    @Test(expected = ApiException.class)
    public void match_nao_encontrado() {

        service.aprovar(null, userPrincipal);
        verify(repository.findById(null));
        verify(repository, times(0)).save(any());

    }
}