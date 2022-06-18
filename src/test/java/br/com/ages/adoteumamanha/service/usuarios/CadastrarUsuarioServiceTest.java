package br.com.ages.adoteumamanha.service.usuarios;

import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.dto.request.CadastrarUsuarioRequest;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.exception.Mensagem;
import br.com.ages.adoteumamanha.mapper.UsuarioMapper;
import br.com.ages.adoteumamanha.repository.UsuarioRepository;
import br.com.ages.adoteumamanha.validator.CadastrarUsuarioRequestValidator;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static br.com.ages.adoteumamanha.domain.enumeration.Perfil.DOADOR;
import static br.com.ages.adoteumamanha.fixture.Fixture.make;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class CadastrarUsuarioServiceTest {

    @InjectMocks
    private CadastrarUsuarioService service;

    @Mock
    private UsuarioMapper mapper;

    @Mock
    private UsuarioRepository repository;

    @Mock
    private CadastrarUsuarioRequestValidator validator;

    @Captor
    private ArgumentCaptor<Usuario> usuarioArgumentCaptor;

    @Test
    public void cadastrar_ok() {
        final CadastrarUsuarioRequest request = make(CadastrarUsuarioRequest.builder())
                .withEmail("marcelo@email.com")
                .build();

        when(repository.existsByEmailOrDocumento(request.getEmail(), request.getDocumento())).thenReturn(Boolean.FALSE);
        when(mapper.apply(request, DOADOR)).thenCallRealMethod();

        service.cadastrar(request, DOADOR);

        verify(validator).validar(request);
        verify(mapper).apply(request, DOADOR);
        verify(repository).save(usuarioArgumentCaptor.capture());

        final Usuario entity = usuarioArgumentCaptor.getValue();

        assertEquals(request.getNome(), entity.getNome());
        assertEquals(request.getEmail(), entity.getEmail());
        assertNotNull(entity.getSenha());
        assertEquals(request.getDocumento(), entity.getDocumento());
        assertEquals(request.getTelefone(), entity.getTelefone());
        assertEquals(request.getSite(), entity.getSite());
        assertEquals(request.getEstado(), entity.getEndereco().getEstado());
        assertEquals(request.getCidade(), entity.getEndereco().getCidade());
        assertEquals(request.getBairro(), entity.getEndereco().getBairro());
        assertEquals(request.getCep(), entity.getEndereco().getCEP());
        assertEquals(request.getRua(), entity.getEndereco().getRua());
        assertEquals(request.getNumero(), entity.getEndereco().getNumero());
        assertEquals(request.getComplemento(), entity.getEndereco().getComplemento());
        assertTrue(entity.getAtivo());
        assertEquals(DOADOR, entity.getPerfil());

    }

    @Test
    public void cadastrar_cpf_ou_email_ja_existe() {
        final CadastrarUsuarioRequest request = make(CadastrarUsuarioRequest.builder())
                .withEmail("marcelo@email.com")
                .build();

        when(repository.existsByEmailOrDocumento(request.getEmail(), request.getDocumento())).thenReturn(Boolean.TRUE);
        when(mapper.apply(request, DOADOR)).thenCallRealMethod();

        Assertions.assertThatCode(()-> service.cadastrar(request, DOADOR))
                .isInstanceOf(ApiException.class)
                .hasMessage(Mensagem.EMAIL_OU_CPF_JA_CADASTRADO.getDescricao());

        verify(validator).validar(request);
    }
}

