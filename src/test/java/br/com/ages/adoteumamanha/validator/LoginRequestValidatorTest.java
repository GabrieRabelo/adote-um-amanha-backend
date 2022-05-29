package br.com.ages.adoteumamanha.validator;

import br.com.ages.adoteumamanha.dto.request.LoginRequest;
import br.com.ages.adoteumamanha.fixture.Fixture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static br.com.ages.adoteumamanha.exception.Mensagem.*;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@RunWith(MockitoJUnitRunner.class)
public class LoginRequestValidatorTest {

    @InjectMocks
    private LoginRequestValidator validator;

    @Mock
    private EmailValidator emailValidator;

    private LoginRequest request;

    @Test
    public void requestValida() {
        request = Fixture.make(LoginRequest.builder()).build();

        validator.validar(request);

        verify(emailValidator).validar(request.getEmail());
    }

    @Test
    public void requestInvalida() {
        try {
            validator.validar(null);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(REQUEST_INVALIDO.getDescricao()));
            verifyNoInteractions(emailValidator);
        }
    }

    @Test
    public void emailInvalido() {
        request = Fixture.make(LoginRequest.builder())
                .withEmail(null)
                .build();

        try {
            validator.validar(request);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(EMAIL_INVALIDO.getDescricao()));
            verifyNoInteractions(emailValidator);
        }

    }

    @Test
    public void senhaInvalida() {
        request = Fixture.make(LoginRequest.builder())
                .withEmail("marcelo@mail.com")
                .withSenha(null)
                .build();

        try {
            validator.validar(request);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(SENHA_INVALIDA.getDescricao()));
            verifyNoInteractions(emailValidator);
        }

    }

}