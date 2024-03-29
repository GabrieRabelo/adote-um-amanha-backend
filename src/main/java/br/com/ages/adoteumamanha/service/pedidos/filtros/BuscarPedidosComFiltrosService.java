package br.com.ages.adoteumamanha.service.pedidos.filtros;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.enumeration.*;
import br.com.ages.adoteumamanha.dto.response.PedidosResponse;
import br.com.ages.adoteumamanha.mapper.PedidosResponseMapper;
import br.com.ages.adoteumamanha.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalTime.MIN;
import static java.util.Optional.ofNullable;
import static org.springframework.data.domain.Sort.by;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuscarPedidosComFiltrosService {

    private final PedidoRepository repository;
    private final PedidosResponseMapper pedidosResponseMapper;

    public PedidosResponse buscar(final Integer pagina,
                                  final Integer tamanho, final String ordenacao,
                                  final Direcao direcao,
                                  final List<Categoria> categorias,
                                  final List<Subcategoria> subcategorias,
                                  final List<Status> status,
                                  final Integer mesesCorte,
                                  final String textoBusca,
                                  final TipoPedido tipoPedido,
                                  final Long idUsuarioLogado) {

        final LocalDateTime mesesDeCorte = ofNullable(mesesCorte).map(meses -> LocalDateTime.now().minusMonths(meses).with(MIN)).orElse(null);
        log.info("categorias {}, subcategorias {}, status {}, dataCorte {}, texto de busca {}", categorias, subcategorias, status, mesesDeCorte, textoBusca);

        log.info("pagina {}, tamanho {}, ordenacao {}, direcao {}", pagina, tamanho, ordenacao, direcao);
        final Pageable paging = PageRequest.of(pagina, tamanho, by(Sort.Direction.valueOf(direcao.name()), ordenacao));

        log.info("Buscando no banco pedidos paginados");
        final Page<Pedido> pedidoEntities = repository.findAllPedidosPorFiltros(categorias, subcategorias, status, mesesDeCorte,
                textoBusca, tipoPedido, idUsuarioLogado, paging);

        return pedidosResponseMapper.apply(pedidoEntities);
    }
}