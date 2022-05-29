package br.com.ages.adoteumamanha.validator;

import br.com.ages.adoteumamanha.dto.request.CadastrarUsuarioRequest;
import br.com.ages.adoteumamanha.fixture.Fixture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static br.com.ages.adoteumamanha.exception.Mensagem.*;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@RunWith(MockitoJUnitRunner.class)
public class CadastrarUsuarioRequestValidatorTest {

    @InjectMocks
    private CadastrarUsuarioRequestValidator validator;

    @Mock
    private EmailValidator emailValidator;

    @Mock
    private CpfCnpjValidator cpfCnpjValidator;

    private CadastrarUsuarioRequest request;

    @Test
    public void requestValida() {
        request = Fixture.make(CadastrarUsuarioRequest.builder()).build();
        validator.validar(request);
    }

    @Test
    public void requestInvalida() {

        try {
            validator.validar(null);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(REQUEST_INVALIDO.getDescricao()));
            verifyNoInteractions(emailValidator, cpfCnpjValidator);
        }
    }


    @Test
    public void nome_invalido() {
        request = Fixture.make(CadastrarUsuarioRequest.builder())
                .withNome(null)
                .build();

        try {
            validator.validar(request);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(NOME_INVALIDO.getDescricao()));
            verifyNoInteractions(emailValidator, cpfCnpjValidator);
        }
    }

    @Test
    public void senha_invalida() {
        request = Fixture.make(CadastrarUsuarioRequest.builder())
                .withSenha(null)
                .build();

        try {
            validator.validar(request);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(SENHA_INVALIDA.getDescricao()));
            verifyNoInteractions(cpfCnpjValidator);
        }
    }

    @Test
    public void telefone_invalido() {
        request = Fixture.make(CadastrarUsuarioRequest.builder())
                .withTelefone(null)
                .build();

        try {
            validator.validar(request);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(TELEFONE_INVALIDO.getDescricao()));
            verify(emailValidator).validar(request.getEmail());
            verify(cpfCnpjValidator).validar(request.getDocumento());
        }
    }

    @Test
    public void estado_invalido() {
        request = Fixture.make(CadastrarUsuarioRequest.builder())
                .withEstado(null)
                .build();

        try {
            validator.validar(request);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(ESTADO_INVALIDO.getDescricao()));
            verify(emailValidator).validar(request.getEmail());
            verify(cpfCnpjValidator).validar(request.getDocumento());
        }
    }

    @Test
    public void cidade_invalido() {
        request = Fixture.make(CadastrarUsuarioRequest.builder())
                .withCidade(null)
                .build();

        try {
            validator.validar(request);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(CIDADE_INVALIDA.getDescricao()));
            verify(emailValidator).validar(request.getEmail());
            verify(cpfCnpjValidator).validar(request.getDocumento());
        }
    }

    @Test
    public void bairro_invalido() {
        request = Fixture.make(CadastrarUsuarioRequest.builder())
                .withBairro(null)
                .build();

        try {
            validator.validar(request);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(BAIRRO_INVALIDO.getDescricao()));
            verify(emailValidator).validar(request.getEmail());
            verify(cpfCnpjValidator).validar(request.getDocumento());
        }
    }

    @Test
    public void cep_invalido() {
        request = Fixture.make(CadastrarUsuarioRequest.builder())
                .withCep(null)
                .build();

        try {
            validator.validar(request);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(CEP_INVALIDO.getDescricao()));
            verifyNoInteractions(emailValidator, cpfCnpjValidator);
        }
    }

    @Test
    public void cep_empty() {
        request = Fixture.make(CadastrarUsuarioRequest.builder())
                .withCep(" ")
                .build();

        try {
            validator.validar(request);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(CEP_INVALIDO.getDescricao()));
            verify(emailValidator).validar(request.getEmail());
            verify(cpfCnpjValidator).validar(request.getDocumento());
        }
    }

    @Test
    public void rua_invalido() {
        request = Fixture.make(CadastrarUsuarioRequest.builder())
                .withRua(null)
                .build();

        try {
            validator.validar(request);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(RUA_INVALIDA.getDescricao()));
            verify(emailValidator).validar(request.getEmail());
            verify(cpfCnpjValidator).validar(request.getDocumento());
        }
    }

    @Test
    public void numero_invalido() {
        request = Fixture.make(CadastrarUsuarioRequest.builder())
                .withNumero(null)
                .build();

        try {
            validator.validar(request);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(NUMERO_CASA_INVALIDO.getDescricao()));
            verify(emailValidator).validar(request.getEmail());
            verify(cpfCnpjValidator).validar(request.getDocumento());
        }
    }

}