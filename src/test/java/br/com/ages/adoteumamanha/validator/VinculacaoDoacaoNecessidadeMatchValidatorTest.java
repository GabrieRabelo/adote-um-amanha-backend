package br.com.ages.adoteumamanha.validator;

import br.com.ages.adoteumamanha.exception.ApiException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.DOACAO;
import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.NECESSIDADE;

@RunWith(MockitoJUnitRunner.class)
public class VinculacaoDoacaoNecessidadeMatchValidatorTest {

    @InjectMocks
    private VinculacaoDoacaoNecessidadeMatchValidator validator;

    @Test
    public void ok() {
        validator.validate(NECESSIDADE);
    }

    @Test(expected = ApiException.class)
    public void usuario_nao_eh_criador_pedido() {
        validator.validate(DOACAO);
    }

}