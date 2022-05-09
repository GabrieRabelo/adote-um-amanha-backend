package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.dto.response.CasaResponse;
import org.junit.Test;

import static br.com.ages.adoteumamanha.fixture.Fixture.make;
import static org.junit.Assert.assertEquals;

public class CasaResponseMapperTest {

    final CasaDescricaoResponseMapper MAPPER = new CasaDescricaoResponseMapper();

    @Test
    public void mapper() {
        final Usuario usuario = make(Usuario.builder()).build();

        final CasaResponse response = MAPPER.apply(usuario);

        assertEquals(usuario.getEmail(), response.getEmail());
        assertEquals(usuario.getEndereco(), response.getEndereco());
        assertEquals(usuario.getNome(), response.getNome());
        assertEquals(usuario.getSite(), response.getSite());
        assertEquals(usuario.getTelefone(), response.getTelefone());
    }

}
