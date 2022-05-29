package br.com.ages.adoteumamanha.validator;

import br.com.ages.adoteumamanha.domain.entity.Match;
import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.exception.ApiException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static br.com.ages.adoteumamanha.domain.enumeration.Categoria.BEM;
import static br.com.ages.adoteumamanha.domain.enumeration.Categoria.SERVICO;
import static br.com.ages.adoteumamanha.domain.enumeration.Status.FINALIZADA;
import static br.com.ages.adoteumamanha.domain.enumeration.Status.PENDENTE;
import static br.com.ages.adoteumamanha.domain.enumeration.Subcategoria.PROFISSIONALIZACAO;
import static br.com.ages.adoteumamanha.domain.enumeration.Subcategoria.SAUDE;
import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.DOACAO;
import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.NECESSIDADE;
import static br.com.ages.adoteumamanha.exception.Mensagem.*;
import static br.com.ages.adoteumamanha.fixture.Fixture.make;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class MatchValidatorTest {

    @InjectMocks
    private MatchValidator validator;

    @Test(expected = ApiException.class)
    public void match_invalido() {
        validator.validar(null);
    }

    @Test
    public void ok() {

        final Pedido doacao = make(Pedido.builder())
                .withTipoPedido(DOACAO)
                .withCategoria(BEM)
                .withStatus(PENDENTE)
                .withSubcategoria(PROFISSIONALIZACAO)
                .build();

        final Pedido necessidade = make(Pedido.builder())
                .withTipoPedido(NECESSIDADE)
                .withCategoria(BEM)
                .withStatus(PENDENTE)
                .withSubcategoria(PROFISSIONALIZACAO)
                .build();

        final Match match = make(Match.builder())
                .withDoacao(doacao)
                .withNecessidade(necessidade)
                .build();

        validator.validar(match);
    }


    @Test
    public void match_doacao_nao_eh_uma_doacao() {

        final Pedido doacao = make(Pedido.builder())
                .withTipoPedido(NECESSIDADE)
                .build();

        final Match match = make(Match.builder())
                .withDoacao(doacao)
                .build();

        try {
            validator.validar(match);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(DOACAO_MATCH_INVALIDO.getDescricao()));
        }
    }

    @Test
    public void match_necessidade_nao_eh_uma_necessidade() {

        final Pedido doacao = make(Pedido.builder())
                .withTipoPedido(DOACAO)
                .build();

        final Pedido necessidade = make(Pedido.builder())
                .withTipoPedido(DOACAO)
                .build();

        final Match match = make(Match.builder())
                .withDoacao(doacao)
                .withNecessidade(necessidade)
                .build();

        try {
            validator.validar(match);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(NECESSIDADE_MATCH_INVALIDO.getDescricao()));
        }
    }

    @Test
    public void match_necessidade_doacao_nao_tem_mesma_categoria() {

        final Pedido doacao = make(Pedido.builder())
                .withTipoPedido(DOACAO)
                .withCategoria(BEM)
                .build();

        final Pedido necessidade = make(Pedido.builder())
                .withTipoPedido(NECESSIDADE)
                .withCategoria(SERVICO)
                .build();

        final Match match = make(Match.builder())
                .withDoacao(doacao)
                .withNecessidade(necessidade)
                .build();

        try {
            validator.validar(match);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(CATEGORIA_MATCH_DIFERENTE.getDescricao()));
        }
    }

    @Test
    public void match_necessidade_doacao_nao_tem_mesma_subcategoria() {

        final Pedido doacao = make(Pedido.builder())
                .withTipoPedido(DOACAO)
                .withCategoria(BEM)
                .withSubcategoria(PROFISSIONALIZACAO)
                .build();

        final Pedido necessidade = make(Pedido.builder())
                .withTipoPedido(NECESSIDADE)
                .withCategoria(BEM)
                .withSubcategoria(SAUDE)
                .build();

        final Match match = make(Match.builder())
                .withDoacao(doacao)
                .withNecessidade(necessidade)
                .build();

        try {
            validator.validar(match);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(SUBCATGEORIA_MATCH_DIFERENTE.getDescricao()));
        }
    }

    @Test
    public void match_necessidade_nao_pendente() {

        final Pedido doacao = make(Pedido.builder())
                .withTipoPedido(DOACAO)
                .withCategoria(BEM)
                .withStatus(PENDENTE)
                .withSubcategoria(PROFISSIONALIZACAO)
                .build();

        final Pedido necessidade = make(Pedido.builder())
                .withTipoPedido(NECESSIDADE)
                .withCategoria(BEM)
                .withStatus(FINALIZADA)
                .withSubcategoria(PROFISSIONALIZACAO)
                .build();

        final Match match = make(Match.builder())
                .withDoacao(doacao)
                .withNecessidade(necessidade)
                .build();

        try {
            validator.validar(match);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(STATUS_NAO_PENDENTE.getDescricao()));
        }
    }

    @Test
    public void match_necessidade_doacao_nao_pendente() {

        final Pedido doacao = make(Pedido.builder())
                .withTipoPedido(DOACAO)
                .withCategoria(BEM)
                .withStatus(FINALIZADA)
                .withSubcategoria(PROFISSIONALIZACAO)
                .build();

        final Pedido necessidade = make(Pedido.builder())
                .withTipoPedido(NECESSIDADE)
                .withCategoria(BEM)
                .withStatus(FINALIZADA)
                .withSubcategoria(PROFISSIONALIZACAO)
                .build();

        final Match match = make(Match.builder())
                .withDoacao(doacao)
                .withStatus(PENDENTE)
                .withNecessidade(necessidade)
                .build();

        try {
            validator.validar(match);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(STATUS_NAO_PENDENTE.getDescricao()));
        }
    }

}
