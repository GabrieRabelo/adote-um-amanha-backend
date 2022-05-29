package br.com.ages.adoteumamanha.service.pedidos;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.enumeration.Status;
import br.com.ages.adoteumamanha.repository.PedidoRepository;
import br.com.ages.adoteumamanha.security.UserPrincipal;
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

    public void recusar(final Long id, final UserPrincipal userPrincipal, final String motivoRecusa) {

        log.info("Buscando pedido com id: {}", id);
        final Pedido pedido = buscarPedidoService.buscarPorID(id);

        log.info("Validando pedido");
        statusPedidoValidator.validar(pedido);

        log.info("Alterando atributos do pedido recusado");
        pedido.setMotivoRecusa(motivoRecusa);
        pedido.setStatus(Status.RECUSADO);
        pedido.setFinalizadoPor(userPrincipal.getEmail());

        log.info("Salvando pedido com id: {}", id);
        repository.save(pedido);
    }
}
