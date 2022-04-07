package br.com.ages.adoteumamanha.validator;

import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.fixture.Fixture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CadastrarTipoPedidoRequestValidatorTest {

    @InjectMocks
    private CadastrarPedidoRequestValidator validator;

    private CadastrarPedidoRequest request;

    @Test
    public void requestValida() {
        request = Fixture.make(CadastrarPedidoRequest.builder()).build();
        validator.validate(request);
    }

    @Test(expected = ApiException.class)
    public void assuntoInvalido() {
        request = Fixture.make(CadastrarPedidoRequest.builder())
                .withAssunto(null)
                .build();

        validator.validate(request);
    }

    @Test(expected = ApiException.class)
    public void descricaoInvalida() {
        request = Fixture.make(CadastrarPedidoRequest.builder())
                .withDescricao(null)
                .build();

        validator.validate(request);
    }

    @Test(expected = ApiException.class)
    public void categoriaInvalida() {
        request = Fixture.make(CadastrarPedidoRequest.builder())
                .withCategoria(null)
                .build();

        validator.validate(request);
    }

    @Test(expected = ApiException.class)
    public void subcategoriaInvalida() {
        request = Fixture.make(CadastrarPedidoRequest.builder())
                .withSubcategoria(null)
                .build();

        validator.validate(request);
    }

}