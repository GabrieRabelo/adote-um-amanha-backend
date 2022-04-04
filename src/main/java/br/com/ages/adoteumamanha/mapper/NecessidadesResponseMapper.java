package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.PedidoEntity;
import br.com.ages.adoteumamanha.dto.response.NecessidadeResponse;
import org.springframework.cglib.core.internal.Function;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Component
public class NecessidadesResponseMapper implements Function<List<PedidoEntity>, List<NecessidadeResponse>> {

    @Override
    public List<NecessidadeResponse> apply(final List<PedidoEntity> pedidoEntities) {

        return ofNullable(pedidoEntities)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(necessidade -> NecessidadeResponse.builder()
                        .withId(necessidade.getId())
                        .withAssunto(necessidade.getAssunto())
                        .withData(LocalDate.from(necessidade.getDataHora()))
                        .withStatus(necessidade.getStatus())
                        .withSubcategoria(necessidade.getSubcategoria())
                        .build())
                .collect(Collectors.toList());
    }

}

