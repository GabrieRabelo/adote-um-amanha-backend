package br.com.ages.adoteumamanha.validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.DOACAO;
import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.NECESSIDADE;
import static br.com.ages.adoteumamanha.exception.Mensagem.VINCULACAO_DE_DOACAO_COM_DOACAO_MATCH;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class VinculacaoDoacaoNecessidadeMatchValidatorTest {

    @InjectMocks
    private VinculacaoDoacaoNecessidadeMatchValidator validator;

    @Test
    public void ok() {
        validator.validar(NECESSIDADE);
    }

    @Test
    public void usuario_nao_eh_criador_pedido() {

        try {
            validator.validar(DOACAO);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(VINCULACAO_DE_DOACAO_COM_DOACAO_MATCH.getDescricao()));
        }
    }

}