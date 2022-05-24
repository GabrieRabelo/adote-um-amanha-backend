package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Match;
import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.dto.response.MatchesResponse;
import br.com.ages.adoteumamanha.dto.response.PedidosResponse;
import br.com.ages.adoteumamanha.dto.response.ResumoMatchResponse;
import br.com.ages.adoteumamanha.dto.response.ResumoPedidoResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class MatchesResponseMapper implements Function<Page<Match>, MatchesResponse> {

    final MatchCurtoResponseMapper matchCurtoResponseMapper = new MatchCurtoResponseMapper();

    @Override
    public MatchesResponse apply(final Page<Match> matches) {

        return MatchesResponse.builder()
                .withConteudo(mapContentToList(matches))
                .withNumeroDaPagina(matches.getNumber())
                .withNumeroDeElementos(matches.getNumberOfElements())
                .withPaginaVazia(matches.isEmpty())
                .withPrimeiraPagina(matches.isFirst())
                .withTamanhoDaPagina(matches.getSize())
                .withTotalDeElementos(matches.getTotalElements())
                .withTotalDePaginas(matches.getTotalPages())
                .withUltimaPagina(matches.isLast())
                .build();
    }

    public List<ResumoMatchResponse> mapContentToList(final Page<Match> matches) {
        return Optional.of(matches.getContent())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(matchCurtoResponseMapper)
                .collect(Collectors.toList());
    }

}


