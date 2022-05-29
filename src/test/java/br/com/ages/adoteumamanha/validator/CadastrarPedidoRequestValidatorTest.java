package br.com.ages.adoteumamanha.validator;

import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.fixture.Fixture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static br.com.ages.adoteumamanha.exception.Mensagem.*;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class CadastrarPedidoRequestValidatorTest {

    @InjectMocks
    private CadastrarPedidoRequestValidator validator;

    private CadastrarPedidoRequest request;

    @Test
    public void requestValida() {
        request = Fixture.make(CadastrarPedidoRequest.builder()).build();
        validator.validar(request);
    }

    @Test
    public void requestInvalida() {

        try {
            validator.validar(null);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(REQUEST_INVALIDO.getDescricao()));
        }
    }

    @Test
    public void assuntoInvalido() {
        request = Fixture.make(CadastrarPedidoRequest.builder())
                .withAssunto(null)
                .build();

        try {
            validator.validar(request);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(ASSUNTO_INVALIDO.getDescricao()));
        }
    }

    @Test
    public void descricaoInvalida() {
        request = Fixture.make(CadastrarPedidoRequest.builder())
                .withDescricao(null)
                .build();

        try {
            validator.validar(request);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(DESCRICAO_INVALIDA.getDescricao()));
        }
    }

    @Test
    public void categoriaInvalida() {
        request = Fixture.make(CadastrarPedidoRequest.builder())
                .withCategoria(null)
                .build();

        try {
            validator.validar(request);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(CATEGORIA_INVALIDA.getDescricao()));
        }
    }

    @Test
    public void subcategoriaInvalida() {
        request = Fixture.make(CadastrarPedidoRequest.builder())
                .withSubcategoria(null)
                .build();

        try {
            validator.validar(request);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(SUBCATEGORIA_INVALIDA.getDescricao()));
        }
    }

}