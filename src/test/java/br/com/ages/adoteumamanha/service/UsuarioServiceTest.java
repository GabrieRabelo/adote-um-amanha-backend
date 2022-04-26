package br.com.ages.adoteumamanha.service;

import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.Perfil;
import br.com.ages.adoteumamanha.dto.response.UsuarioResponse;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.fixture.Fixture;
import br.com.ages.adoteumamanha.mapper.CasaDescricaoResponseMapper;
import br.com.ages.adoteumamanha.mapper.UsuarioResponseMapper;
import br.com.ages.adoteumamanha.repository.UsuarioRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService service;

    @Mock
    private UsuarioRepository repository;

    @Mock
    private CasaDescricaoResponseMapper casaDescricaoResponseMapper;

    @Mock
    private UsuarioResponseMapper usuarioResponseMapper;

    @Test
    public void ok() {

        long id = 1;
        var usuario = Fixture.make(Usuario.builder()).build();

        when(repository.findByIdAndPerfil(id, Perfil.CASA)).thenReturn(Optional.of(usuario));
        when(casaDescricaoResponseMapper.apply(usuario)).thenCallRealMethod();

        service.buscaCasaDescricao(id);

        verify(repository).findByIdAndPerfil(id, Perfil.CASA);
        verify(casaDescricaoResponseMapper).apply(usuario);
    }

    @Test(expected = ApiException.class)
    public void casa_nao_encontrada_erro() {

        final Long id = 1L;
        when(repository.findByIdAndPerfil(id, Perfil.CASA)).thenReturn(Optional.empty());

        service.buscaCasaDescricao(id);

        verifyNoInteractions(casaDescricaoResponseMapper);
    }

    @Test
    public void testBuscaUsuarioPorId() {

        long id = 1;
        var usuario = Fixture.make(Usuario.builder()).build();

        when(repository.findById(id)).thenReturn(Optional.of(usuario));
        when(usuarioResponseMapper.apply(usuario)).thenCallRealMethod();

        var result = service.buscaUsuario(id);

        var expectedResult = UsuarioResponse.builder()
                .withNome(usuario.getNome())
                .withPerfil(usuario.getPerfil())
                .build();

        assertThat(result).usingRecursiveComparison().isEqualTo(expectedResult);

        verify(repository).findById(id);
        verify(usuarioResponseMapper).apply(usuario);
    }
}