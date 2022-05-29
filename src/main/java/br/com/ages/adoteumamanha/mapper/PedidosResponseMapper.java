package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
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
public class PedidosResponseMapper implements Function<Page<Pedido>, PedidosResponse> {

    final ResumoPedidoResponseMapper resumoPedidoResponseMapper = new ResumoPedidoResponseMapper();

    @Override
    public PedidosResponse apply(final Page<Pedido> pedidos) {

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
                .map(resumoPedidoResponseMapper)
                .collect(Collectors.toList());
    }

}


