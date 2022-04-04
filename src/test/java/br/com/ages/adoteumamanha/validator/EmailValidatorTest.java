package br.com.ages.adoteumamanha.validator;

import br.com.ages.adoteumamanha.exception.ApiException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EmailValidatorTest {

    @InjectMocks
    private EmailValidator validator;

    @Test
    public void emailValido() {
        validator.validate("email@email.com");
    }

    @Test
    public void emailBRValido() {
        validator.validate("email@email.com.br");
    }

    @Test(expected = ApiException.class)
    public void emailInvalido() {
        validator.validate("email@email");
    }

}