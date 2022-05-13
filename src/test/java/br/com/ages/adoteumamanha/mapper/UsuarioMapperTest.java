package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.Perfil;
import br.com.ages.adoteumamanha.dto.request.CadastrarUsuarioRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static br.com.ages.adoteumamanha.fixture.Fixture.make;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UsuarioMapperTest {

    final UsuarioMapper MAPPER = new UsuarioMapper();

    @Test
    public void mapper() throws JsonProcessingException {
        final CadastrarUsuarioRequest request = make(CadastrarUsuarioRequest.builder()).build();

        final Perfil perfil = Arrays.stream(Perfil.values()).findAny().orElse(null);
        final Usuario response = MAPPER.apply(request, perfil);

        Assert.assertEquals(request.getNome(), response.getNome());
        Assert.assertEquals(request.getEmail(), response.getEmail());
        assertNotNull(response.getSenha());
        Assert.assertEquals(request.getDocumento(), response.getDocumento());
        Assert.assertEquals(request.getTelefone(), response.getTelefone());
        Assert.assertEquals(request.getSite(), response.getSite());
        Assert.assertEquals(request.getEstado(), response.getEndereco().getEstado());
        Assert.assertEquals(request.getCidade(), response.getEndereco().getCidade());
        Assert.assertEquals(request.getBairro(), response.getEndereco().getBairro());
        Assert.assertEquals(request.getCep(), response.getEndereco().getCEP());
        Assert.assertEquals(request.getRua(), response.getEndereco().getRua());
        Assert.assertEquals(request.getNumero(), response.getEndereco().getNumero());
        Assert.assertEquals(request.getComplemento(), response.getEndereco().getComplemento());
        assertTrue(response.getAtivo());
        Assert.assertEquals(perfil, response.getPerfil());
    }

}
