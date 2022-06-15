package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Match;
import br.com.ages.adoteumamanha.dto.response.MatchesResponse;
import br.com.ages.adoteumamanha.fixture.Fixture;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MatchesResponseMapperTest {

    final MatchesResponseMapper MAPPER = new MatchesResponseMapper();

    @Test
    public void ok() {

        final Match match = Fixture.make(Match.builder()).build();
        final List<Match> matchesResponses = Arrays.asList(match);
        final Page<Match> pagedResponse = new PageImpl(matchesResponses);

        final MatchesResponse response = MAPPER.apply(pagedResponse);

        assertEquals(response.getConteudo().size(), pagedResponse.getContent().size());
    }

}