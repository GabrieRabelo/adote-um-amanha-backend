package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.dto.response.NecessidadesResponse;
import br.com.ages.adoteumamanha.dto.response.PedidoCurtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class NecessidadesResponseMapper implements Function<Page<Pedido>, NecessidadesResponse> {

    final PedidoCurtoResponseMapper pedidoCurtoResponseMapper = new PedidoCurtoResponseMapper();

    @Override
    public NecessidadesResponse apply(final Page<Pedido> pedidos) {

        return NecessidadesResponse.builder()
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

    public List<PedidoCurtoResponse> mapContentToList(final Page<Pedido> pedidos) {
        return Optional.of(pedidos.getContent())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(pedidoCurtoResponseMapper)
                .collect(Collectors.toList());
    }
}


