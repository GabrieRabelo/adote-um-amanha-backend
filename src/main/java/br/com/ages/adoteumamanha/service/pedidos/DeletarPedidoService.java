package br.com.ages.adoteumamanha.service.pedidos;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.repository.PedidoRepository;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.service.pedidos.BuscarPedidoService;
import br.com.ages.adoteumamanha.validator.StatusPedidoValidator;
import br.com.ages.adoteumamanha.validator.UsuarioCriadorPedidoValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeletarPedidoService {

    private final PedidoRepository repository;
    private final StatusPedidoValidator statusPedidoValidator;
    private final BuscarPedidoService buscarPedidoService;
    private final UsuarioCriadorPedidoValidator usuarioCriadorPedidoValidator;

    public void deletar(final Long id, final UserPrincipal userPrincipal) {

        log.info("Buscando pedido com id: {}", id);
        final Pedido pedido = buscarPedidoService.buscarPorID(id);

        log.info("Validando status do pedido: {}", pedido.getStatus());
        statusPedidoValidator.validate(pedido);

        final Long idUsuarioCriadorPedido = ofNullable(pedido.getUsuario()).map(Usuario::getId).orElse(null);
        final Long idUsuarioLogado = userPrincipal.getId();

        log.info("Validando id usuário logado {} com o do criador do pedido {}", idUsuarioLogado, idUsuarioCriadorPedido);
        usuarioCriadorPedidoValidator.validate(idUsuarioLogado, idUsuarioCriadorPedido);

        log.info("Deletando pedido com id: {}", pedido.getId());
        repository.delete(pedido);
    }
}