package br.com.ages.adoteumamanha.service;

import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.Perfil;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.fixture.Fixture;
import br.com.ages.adoteumamanha.mapper.CasaDescricaoResponseMapper;
import br.com.ages.adoteumamanha.repository.UsuarioRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CasaServiceTest {

    @InjectMocks
    private CasaService service;

    @Mock
    private UsuarioRepository repository;

    @Mock
    private CasaDescricaoResponseMapper mapper;

    @Test
    public void ok() {

        long id = 1;
        var usuario = Fixture.make(Usuario.builder()).build();

        when(repository.findByIdAndPerfil(id, Perfil.CASA)).thenReturn(Optional.of(usuario));
        when(mapper.apply(usuario)).thenCallRealMethod();

        service.buscaCasaDescricao(id);

        verify(repository).findByIdAndPerfil(id, Perfil.CASA);
        verify(mapper).apply(usuario);
    }

    @Test(expected = ApiException.class)
    public void casa_nao_encontrada_erro() {

        final Long id = 1L;
        when(repository.findByIdAndPerfil(id, Perfil.CASA)).thenReturn(Optional.empty());

        service.buscaCasaDescricao(id);

        verifyNoInteractions(mapper);
    }
}