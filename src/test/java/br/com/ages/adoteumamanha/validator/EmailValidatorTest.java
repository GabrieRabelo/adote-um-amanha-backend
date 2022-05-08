package br.com.ages.adoteumamanha.validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static br.com.ages.adoteumamanha.exception.Mensagem.EMAIL_INVALIDO;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void emailInvalido() {

        try {
            validator.validate("email@email");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(EMAIL_INVALIDO.getDescricao()));
        }
    }

}