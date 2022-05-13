package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.dto.response.UsuarioResponse;
import org.junit.Test;

import static br.com.ages.adoteumamanha.fixture.Fixture.make;
import static org.junit.Assert.assertEquals;

public class UsuarioResponseMapperTest {

    final UsuarioResponseMapper MAPPER = new UsuarioResponseMapper();

    @Test
    public void mapper() {
        final Usuario usuario = make(Usuario.builder()).build();

        final UsuarioResponse response = MAPPER.apply(usuario);

        assertEquals(usuario.getNome(), response.getNome());
        assertEquals(usuario.getPerfil(), response.getPerfil());
    }
}
