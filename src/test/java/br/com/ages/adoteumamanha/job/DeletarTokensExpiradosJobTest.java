package br.com.ages.adoteumamanha.job;

import br.com.ages.adoteumamanha.domain.entity.ResetarSenha;
import br.com.ages.adoteumamanha.redis.ResetarSenhaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeletarTokensExpiradosJobTest {

    @InjectMocks
    private DeletarTokensExpiradosJob job;

    @Mock
    private ResetarSenhaRepository repository;

    @Captor
    private ArgumentCaptor<List<ResetarSenha>> argumentCaptor;

    @Before
    public void setup() {
        ReflectionTestUtils.setField(job, "resetarSenhaTokenTtl", 10L);
    }

    @Test
    public void ok() {

        final ResetarSenha resetarSenhaNaoExpirado = ResetarSenha.builder()
                .withId(1L)
                .withUsado(false)
                .withData(LocalDateTime.now())
                .build();

        final ResetarSenha resetarSenhaExpirado = ResetarSenha.builder()
                .withId(2L)
                .withUsado(false)
                .withData(LocalDateTime.now().minusMinutes(20))
                .build();


        final ResetarSenha resetarSenhaUsado = ResetarSenha.builder()
                .withId(2L)
                .withUsado(true)
                .withData(LocalDateTime.now().minusMinutes(20))
                .build();


        final List<ResetarSenha> lista = Arrays.asList(resetarSenhaNaoExpirado, resetarSenhaExpirado);

        when(repository.findAll()).thenReturn(lista);

        job.executar();

        verify(repository).deleteAll(argumentCaptor.capture());

        assertEquals(argumentCaptor.getValue().size(), 1);
        assertEquals(argumentCaptor.getValue().get(0), resetarSenhaExpirado);

    }

}