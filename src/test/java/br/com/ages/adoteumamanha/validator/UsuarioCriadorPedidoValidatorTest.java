package br.com.ages.adoteumamanha.validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static br.com.ages.adoteumamanha.exception.Mensagem.USUARIO_NAO_PODE_DELETAR_PEDIDO;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioCriadorPedidoValidatorTest {

    @InjectMocks
    private UsuarioCriadorPedidoValidator validator;

    @Test
    public void ok() {
        validator.validar(1L, 1L);
    }

    @Test
    public void usuario_nao_eh_criador_pedido() {

        try {
            validator.validar(1L, 999L);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(USUARIO_NAO_PODE_DELETAR_PEDIDO.getDescricao()));
        }
    }

}