package br.com.ages.adoteumamanha.service.password;

import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.redis.ResetarSenhaRepository;
import br.com.ages.adoteumamanha.repository.UsuarioRepository;
import br.com.ages.adoteumamanha.service.mail.MailService;
import br.com.ages.adoteumamanha.util.TokenGeneratorUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static br.com.ages.adoteumamanha.fixture.Fixture.make;
import static java.util.Optional.of;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GerarTokenSenhaServiceTest {

    @InjectMocks
    private GerarTokenSenhaService service;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ResetarSenhaRepository resetarSenhaRepository;

    @Mock
    private TokenGeneratorUtils tokenGeneratorUtils;

    @Mock
    private MailService mailService;

    @Test(expected = ApiException.class)
    public void match_nao_encontrado() {

        service.gerarToken(null);
        verify(usuarioRepository.findById(null));
        verifyNoInteractions(mailService, tokenGeneratorUtils, resetarSenhaRepository);
    }

    @Test
    public void ok() {

        when(usuarioRepository.findByEmail(any())).thenReturn(of(make(Usuario.builder()).build()));
        when(tokenGeneratorUtils.generate()).thenReturn(random(9));

        service.gerarToken(random(10));

        verify(usuarioRepository).findByEmail(any());
        verify(tokenGeneratorUtils).generate();
        verify(resetarSenhaRepository).save(any());

    }

}