package br.com.ages.adoteumamanha.service.pedidos.descricao;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.enumeration.TipoPedido;
import br.com.ages.adoteumamanha.dto.response.DescricaoPedidoResponse;
import br.com.ages.adoteumamanha.mapper.DescricaoPedidoResponseMapper;
import br.com.ages.adoteumamanha.service.pedidos.BuscarPedidoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuscarDescricaoPedidoService {

    private final BuscarPedidoService buscarPedidoService;
    private final DescricaoPedidoResponseMapper mapper;

    public DescricaoPedidoResponse buscar(final Long idPedido, final List<TipoPedido> tipoPedidos, final Long idUsuarioLogado) {

        log.info("Buscando pedido com id {} do tipo {} para o usuario {}", idPedido, tipoPedidos, idUsuarioLogado);
        final Pedido pedido = buscarPedidoService.buscarPorIdPedidoTipoPedidosUsuario(idPedido, tipoPedidos, idUsuarioLogado);
        return mapper.apply(pedido);
    }

}