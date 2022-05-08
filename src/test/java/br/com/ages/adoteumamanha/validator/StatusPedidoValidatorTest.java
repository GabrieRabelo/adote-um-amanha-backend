package br.com.ages.adoteumamanha.validator;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static br.com.ages.adoteumamanha.domain.enumeration.Status.FINALIZADA;
import static br.com.ages.adoteumamanha.domain.enumeration.Status.PENDENTE;
import static br.com.ages.adoteumamanha.exception.Mensagem.STATUS_NAO_PENDENTE;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class StatusPedidoValidatorTest {

    @InjectMocks
    private StatusPedidoValidator validator;

    @Test
    public void ok() {
        validator.validate(Pedido.builder().withStatus(PENDENTE).build());
    }

    @Test
    public void status_pedido_nao_pendente() {

        try {
            validator.validate(Pedido.builder().withStatus(FINALIZADA).build());
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(STATUS_NAO_PENDENTE.getDescricao()));
        }
    }

}