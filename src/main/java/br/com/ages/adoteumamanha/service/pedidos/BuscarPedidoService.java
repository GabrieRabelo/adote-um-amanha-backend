package br.com.ages.adoteumamanha.service.pedidos;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.enumeration.TipoPedido;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.exception.Mensagem;
import br.com.ages.adoteumamanha.repository.PedidoRepository;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuscarPedidoService {

    private final PedidoRepository repository;

    public Pedido buscarPorID(final Long id) {

        log.info("Buscando pedido por id {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new ApiException(Mensagem.ITEM_NAO_ENCONTRADO.getDescricao(), HttpStatus.NOT_FOUND));
    }

    public Pedido buscarPorIdPedidoTipoPedidosUsuario(final Long idPedido, final List<TipoPedido> tipoPedidos, final UserPrincipal userPrincipal) {

        final Long idUsuario = ofNullable(userPrincipal).map(UserPrincipal::getId).orElse(null);

        log.info("Buscando pedido por id {} para o usuário {} para os pedidos de tipo {}", idPedido, idUsuario, tipoPedidos);
        return repository.findByIdPedidoAndTipoPedidoAndIdUsuario(idPedido, tipoPedidos, idUsuario)
                .orElseThrow(() -> new ApiException(Mensagem.ITEM_NAO_ENCONTRADO.getDescricao(), HttpStatus.NOT_FOUND));
    }
}