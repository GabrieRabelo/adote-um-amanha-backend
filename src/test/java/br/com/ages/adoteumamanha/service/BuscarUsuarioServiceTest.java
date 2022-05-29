package br.com.ages.adoteumamanha.service;

import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.Perfil;
import br.com.ages.adoteumamanha.dto.response.UsuarioResponse;
import br.com.ages.adoteumamanha.fixture.Fixture;
import br.com.ages.adoteumamanha.mapper.CasaDescricaoResponseMapper;
import br.com.ages.adoteumamanha.mapper.UsuarioResponseMapper;
import br.com.ages.adoteumamanha.repository.UsuarioRepository;
import br.com.ages.adoteumamanha.service.usuarios.BuscarUsuarioService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static br.com.ages.adoteumamanha.exception.Mensagem.CASA_NAO_ENCONTRADA;
import static br.com.ages.adoteumamanha.exception.Mensagem.USUARIO_NAO_ENCONTRADO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BuscarUsuarioServiceTest {

    @InjectMocks
    private BuscarUsuarioService service;

    @Mock
    private UsuarioRepository repository;

    @Mock
    private CasaDescricaoResponseMapper casaDescricaoResponseMapper;

    @Mock
    private UsuarioResponseMapper usuarioResponseMapper;

    @Test
    public void buscar_casa_ok() {

        long id = 1;
        var usuario = Fixture.make(Usuario.builder()).build();

        when(repository.findByIdAndPerfil(id, Perfil.CASA)).thenReturn(Optional.of(usuario));
        when(casaDescricaoResponseMapper.apply(usuario)).thenCallRealMethod();

        service.buscarCasaPorId(id);

        verify(repository).findByIdAndPerfil(id, Perfil.CASA);
        verify(casaDescricaoResponseMapper).apply(usuario);
    }

    @Test
    public void buscar_usuario_ok() {

        long id = 1;
        var usuario = Fixture.make(Usuario.builder()).build();

        when(repository.findById(id)).thenReturn(Optional.of(usuario));
        when(usuarioResponseMapper.apply(usuario)).thenCallRealMethod();

        var result = service.buscarUsuarioPorId(id);

        var expectedResult = UsuarioResponse.builder()
                .withNome(usuario.getNome())
                .withPerfil(usuario.getPerfil())
                .build();

        assertThat(result).usingRecursiveComparison().isEqualTo(expectedResult);

        verify(repository).findById(id);
        verify(usuarioResponseMapper).apply(usuario);
    }

    @Test
    public void casa_nao_encontrada_erro() {

        try {
            service.buscarCasaPorId(null);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(CASA_NAO_ENCONTRADA.getDescricao()));
            verifyNoInteractions(casaDescricaoResponseMapper);
        }
    }

    @Test
    public void usuario_nao_encontrado_erro() {

        try {
            service.buscarUsuarioPorId(null);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(USUARIO_NAO_ENCONTRADO.getDescricao()));
            verifyNoInteractions(usuarioResponseMapper);
        }
    }
}