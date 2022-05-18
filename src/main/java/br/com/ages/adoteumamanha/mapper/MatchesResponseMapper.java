package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Match;
import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.dto.response.MatchesResponse;
import br.com.ages.adoteumamanha.dto.response.PedidosResponse;
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

    final PedidoCurtoResponseMapper pedidoCurtoResponseMapper = new PedidoCurtoResponseMapper();

    @Override
    public MatchesResponse apply(final Page<Match> matches) {

        return PedidosResponse.builder()
                .withConteudo(mapContentToList(pedidos))
                .withNumeroDaPagina(pedidos.getNumber())
                .withNumeroDeElementos(pedidos.getNumberOfElements())
                .withPaginaVazia(pedidos.isEmpty())
                .withPrimeiraPagina(pedidos.isFirst())
                .withTamanhoDaPagina(pedidos.getSize())
                .withTotalDeElementos(pedidos.getTotalElements())
                .withTotalDePaginas(pedidos.getTotalPages())
                .withUltimaPagina(pedidos.isLast())
                .build();
    }

    public List<ResumoPedidoResponse> mapContentToList(final Page<Pedido> pedidos) {
        return Optional.of(pedidos.getContent())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(pedidoCurtoResponseMapper)
                .collect(Collectors.toList());
    }

}


