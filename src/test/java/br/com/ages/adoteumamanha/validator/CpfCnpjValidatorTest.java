package br.com.ages.adoteumamanha.validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static br.com.ages.adoteumamanha.exception.Mensagem.CPF_CNPJ_INVALIDO;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class CpfCnpjValidatorTest {

    @InjectMocks
    private CpfCnpjValidator validator;

    @Test
    public void cpf_ok() {
        validator.validar("278.085.250-06");
    }

    @Test
    public void cnpf_ok() {
        validator.validar("05.914.664/0001-80");
    }

    @Test
    public void invalido() {

        try {
            validator.validar("invalido");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(CPF_CNPJ_INVALIDO.getDescricao()));
        }
    }

}