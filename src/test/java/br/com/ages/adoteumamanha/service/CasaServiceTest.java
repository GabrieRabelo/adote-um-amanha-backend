package br.com.ages.adoteumamanha.service;

import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.Perfil;
import br.com.ages.adoteumamanha.dto.response.CasaDescricaoResponse;
import br.com.ages.adoteumamanha.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CasaServiceTest {

    private final UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
    private final CasaService casaService = new CasaService(usuarioRepository);

    @Test
    void buscaCasaDescricao() {

        long id = 1;
        var usuario = Usuario.builder()
                .withSite("batata.com")
                .withEmail("usuario@gmail.com")
                .withNome("jarbas")
                .withTelefone("0")
                .withPerfil(Perfil.CASA)
                .build();
        when(usuarioRepository.findByIdAndPerfil(id, Perfil.CASA)).thenReturn(Optional.of(usuario));

        var casaDescricao = casaService.buscaCasaDescricao(id);

        var casaEsperada = CasaDescricaoResponse.builder()
                .withSite("batata.com")
                .withEmail("usuario@gmail.com")
                .withNome("jarbas")
                .withTelefone("0")
                .build();

        assertThat(casaDescricao).isNotEmpty();
        assertThat(casaDescricao.get()).isInstanceOf(CasaDescricaoResponse.class);
        assertThat(casaDescricao.get()).usingRecursiveComparison().isEqualTo(casaEsperada);
    }

    @Test
    void buscaCasaDescricaoComResultadoVazio() {

        long id = 1;
        when(usuarioRepository.findByIdAndPerfil(id, Perfil.CASA)).thenReturn(Optional.empty());

        var casaDescricao = casaService.buscaCasaDescricao(id);

        assertThat(casaDescricao).isEmpty();
    }

}