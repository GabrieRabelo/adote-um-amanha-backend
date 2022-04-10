package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.dto.response.CasaDescricaoResponse;
import br.com.ages.adoteumamanha.fixture.Fixture;
import org.junit.Assert;
import org.junit.Test;

public class CasaDescricaoResponseMapperTest {

    final CasaDescricaoResponseMapper MAPPER = new CasaDescricaoResponseMapper();

    @Test
    public void mapper() {
        final Usuario usuario = Fixture.make(Usuario.builder()).build();

        final CasaDescricaoResponse response = MAPPER.apply(usuario);

        Assert.assertEquals(usuario.getEmail(), response.getEmail());
        Assert.assertEquals(usuario.getEndereco(), response.getEndereco());
        Assert.assertEquals(usuario.getNome(), response.getNome());
        Assert.assertEquals(usuario.getSite(), response.getSite());
        Assert.assertEquals(usuario.getTelefone(), response.getTelefone());
    }

}
