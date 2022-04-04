package br.com.ages.adoteumamanha.validator;

import br.com.ages.adoteumamanha.dto.request.LoginRequest;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.fixture.Fixture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

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

        validator.validate(request);

        verify(emailValidator).validate(request.getEmail());
    }

    @Test(expected = ApiException.class)
    public void emailInvalido() {
        request = Fixture.make(LoginRequest.builder())
                .withEmail(null)
                .build();

        validator.validate(request);

        verifyNoInteractions(emailValidator);
    }

    @Test(expected = ApiException.class)
    public void senhaInvalida() {
        request = Fixture.make(LoginRequest.builder())
                .withSenha(null)
                .build();

        validator.validate(request);

        verifyNoInteractions(emailValidator);
    }

    @Test(expected = ApiException.class)
    public void emailValidatorError() {
        request = Fixture.make(LoginRequest.builder()).build();

        doThrow(ApiException.class).when(emailValidator).validate(request.getEmail());

        validator.validate(request);

        verify(emailValidator).validate(request.getEmail());
    }

}