package br.com.ages.adoteumamanha.validator;

import br.com.ages.adoteumamanha.exception.ApiException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioCriadorPedidoValidatorTest {

    @InjectMocks
    private UsuarioCriadorPedidoValidator validator;

    @Test
    public void ok() {
        validator.validate(1L, 1L);
    }

    @Test(expected = ApiException.class)
    public void usuario_nao_eh_criador_pedido() {
        validator.validate(1L, 999L);
    }

}