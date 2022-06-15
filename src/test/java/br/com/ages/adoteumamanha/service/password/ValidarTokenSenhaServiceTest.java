package br.com.ages.adoteumamanha.service.password;

import br.com.ages.adoteumamanha.domain.entity.ResetarSenha;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;

import static br.com.ages.adoteumamanha.exception.Mensagem.*;
import static br.com.ages.adoteumamanha.fixture.Fixture.make;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ValidarTokenSenhaServiceTest {

    @InjectMocks
    private ValidarTokenSenhaService service;

    @Before
    public void setup() {
        ReflectionTestUtils.setField(service, "resetarSenhaTokenTtl", 10L);
    }

    @Test
    public void token_invalido() {

        final ResetarSenha resetarSenha = make(ResetarSenha.builder())
                .withData(LocalDateTime.now().minusMinutes(60))
                .withUsado(false)
                .build();

        try {
            service.validarToken(resetarSenha, null);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(TOKEN_INVALIDO.getDescricao()));
        }
    }

    @Test
    public void token_usado() {

        final ResetarSenha resetarSenha = make(ResetarSenha.builder())
                .withData(LocalDateTime.now())
                .withUsado(true)
                .build();

        try {
            service.validarToken(resetarSenha, resetarSenha.getToken());
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(TOKEN_USADO.getDescricao()));
        }
    }

    @Test
    public void token_expirado() {

        final ResetarSenha resetarSenha = make(ResetarSenha.builder())
                .withData(LocalDateTime.now().minusMinutes(60))
                .build();

        try {
            service.validarToken(resetarSenha, resetarSenha.getToken());
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(TOKEN_EXPIRADO.getDescricao()));
        }
    }

    @Test
    public void ok() {

        final ResetarSenha resetarSenha = make(ResetarSenha.builder())
                .withData(LocalDateTime.now())
                .withUsado(false)
                .build();

        service.validarToken(resetarSenha, resetarSenha.getToken());

    }

}