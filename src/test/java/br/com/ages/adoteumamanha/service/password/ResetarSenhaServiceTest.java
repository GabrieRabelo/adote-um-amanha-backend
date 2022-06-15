package br.com.ages.adoteumamanha.service.password;

import br.com.ages.adoteumamanha.domain.entity.ResetarSenha;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.dto.request.NovaSenhaRequest;
import br.com.ages.adoteumamanha.redis.ResetarSenhaRepository;
import br.com.ages.adoteumamanha.repository.UsuarioRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static br.com.ages.adoteumamanha.exception.Mensagem.*;
import static br.com.ages.adoteumamanha.fixture.Fixture.make;
import static java.util.Optional.of;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ResetarSenhaServiceTest {

    @InjectMocks
    private ResetarSenhaService service;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ResetarSenhaRepository resetarSenhaRepository;

    @Mock
    private ValidarTokenSenhaService validarTokenSenhaService;

    @Test
    public void request_null() {

        try {
            service.resetar(random(1), random(1), null);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(REQUEST_INVALIDO.getDescricao()));
            verifyNoInteractions(usuarioRepository, validarTokenSenhaService);
        }
    }

    @Test
    public void nova_senha_null() {

        try {
            service.resetar(random(1), random(1), NovaSenhaRequest.builder().build());
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(REQUEST_INVALIDO.getDescricao()));
            verifyNoInteractions(usuarioRepository, validarTokenSenhaService);
        }
    }


    @Test
    public void usuario_nao_encontrado() {

        try {
            service.resetar(null, random(1), make(NovaSenhaRequest.builder()).build());
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(USUARIO_NAO_ENCONTRADO.getDescricao()));
        }
    }

    @Test
    public void resetar_senha_nao_encontrado() {

        when(usuarioRepository.findByEmail(any())).thenReturn(of(make(Usuario.builder()).build()));

        try {
            service.resetar(random(1), null, make(NovaSenhaRequest.builder()).build());
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(TOKEN_INVALIDO.getDescricao()));
        }
    }

    @Test
    public void ok() {

        when(usuarioRepository.findByEmail(any())).thenReturn(of(make(Usuario.builder()).build()));
        when(resetarSenhaRepository.findByTokenAndEmail(any(),any())).thenReturn(of(make(ResetarSenha.builder()).build()));

        service.resetar(random(1), random(1), make(NovaSenhaRequest.builder()).build());

        verify(validarTokenSenhaService).validarToken(any(), any());
    }
}