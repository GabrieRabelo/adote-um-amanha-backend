package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Match;
import br.com.ages.adoteumamanha.dto.response.ResumoMatchResponse;
import org.junit.Test;

import static br.com.ages.adoteumamanha.fixture.Fixture.make;
import static org.junit.Assert.assertEquals;

public class ResumoMatchResponseMapperTest {

    final ResumoMatchResponseMapper MAPPER = new ResumoMatchResponseMapper();

    @Test
    public void mapper() {
        final Match match = make(Match.builder()).build();

        final ResumoMatchResponse response = MAPPER.apply(match);

        assertEquals(match.getId(), response.getId());
        assertEquals(match.getNecessidade().getAssunto(), response.getAssunto());
        assertEquals(match.getDataCriacao(), response.getData());
        assertEquals(match.getNecessidade().getCategoria(), response.getCategoria());
        assertEquals(match.getNecessidade().getSubcategoria(), response.getSubcategoria());
        assertEquals(match.getStatus(), response.getStatus());
        assertEquals(match.getNecessidade().getDescricao(), response.getDescricaoDoacao());
        assertEquals(match.getNecessidade().getUsuario().getId(), response.getIdCasa());
        assertEquals(match.getNecessidade().getUsuario().getNome(), response.getNomeCasa());
        assertEquals(match.getDoacao().getUsuario().getId(), response.getIdDoador());
        assertEquals(match.getDoacao().getUsuario().getNome(), response.getNomeDoador());
    }

}
