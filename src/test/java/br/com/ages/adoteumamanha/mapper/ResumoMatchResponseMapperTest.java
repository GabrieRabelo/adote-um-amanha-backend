package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Match;
import br.com.ages.adoteumamanha.dto.response.ResumoMatchResponse;
import br.com.ages.adoteumamanha.fixture.Fixture;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ResumoMatchResponseMapperTest {

    final ResumoMatchResponseMapper MAPPER = new ResumoMatchResponseMapper();

    @Test
    public void ok() {

        final Match match = Fixture.make(Match.builder()).build();

        final ResumoMatchResponse response = MAPPER.apply(match);

        assertEquals(response.getAssunto(), match.getNecessidade().getAssunto());
        assertEquals(response.getCategoria(), match.getNecessidade().getCategoria());
        assertNotNull(response.getData());
        assertEquals(response.getId(), match.getId());
        assertEquals(response.getNomeCasa(), match.getNecessidade().getUsuario().getNome());
        assertEquals(response.getNomeDoador(), match.getDoacao().getUsuario().getNome());
        assertEquals(response.getStatus(), match.getStatus());
        assertEquals(response.getSubcategoria(), match.getNecessidade().getSubcategoria());

    }

}