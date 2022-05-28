package br.com.ages.adoteumamanha.service.match;

import br.com.ages.adoteumamanha.domain.entity.Match;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.mapper.ResumoMatchResponseMapper;
import br.com.ages.adoteumamanha.repository.MatchRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static java.util.Optional.of;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BuscarMatchServiceTest {

    @InjectMocks
    private BuscarMatchService service;

    @Mock
    private MatchRepository repository;

    @Mock
    private ResumoMatchResponseMapper resumoMatchResponseMapper;

    @Test
    public void buscar_por_id_ok() {
        final Long id = 1L;

        when(repository.findById(id)).thenReturn(of(Match.builder().build()));
        service.buscarPorID(id);

        verify(repository).findById(id);
    }

    @Test(expected = ApiException.class)
    public void buscar_por_id_erro() {

        try {
            service.buscarPorID(null);
        } finally {
            verify(repository).findById(null);
        }
    }
}