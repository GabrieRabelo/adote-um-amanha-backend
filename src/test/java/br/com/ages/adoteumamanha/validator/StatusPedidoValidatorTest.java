package br.com.ages.adoteumamanha.validator;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.exception.ApiException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static br.com.ages.adoteumamanha.domain.enumeration.Status.FINALIZADA;
import static br.com.ages.adoteumamanha.domain.enumeration.Status.PENDENTE;

@RunWith(MockitoJUnitRunner.class)
public class StatusPedidoValidatorTest {

    @InjectMocks
    private StatusPedidoValidator validator;

    @Test
    public void ok() {
        validator.validate(Pedido.builder().withStatus(PENDENTE).build());
    }

    @Test(expected = ApiException.class)
    public void status_pedido_nao_pendente() {
        validator.validate(Pedido.builder().withStatus(FINALIZADA).build());
    }

}