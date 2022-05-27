package br.com.ages.adoteumamanha.service.pedidos;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.enumeration.Status;
import br.com.ages.adoteumamanha.repository.PedidoRepository;
import br.com.ages.adoteumamanha.validator.StatusPedidoValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecusarPedidoService {

    private final PedidoRepository repository;
    private final StatusPedidoValidator statusPedidoValidator;
    private final BuscarPedidoService buscarPedidoService;

    public void recusar(final Long id, final String motivoRecusa) {

        log.debug("Recusando pedido com id: {}", id);

        final Pedido pedido = buscarPedidoService.buscarPorID(id);

        statusPedidoValidator.validar(pedido);

        pedido.setMotivoRecusa(motivoRecusa);
        pedido.setStatus(Status.RECUSADO);

        repository.save(pedido);
    }
}
