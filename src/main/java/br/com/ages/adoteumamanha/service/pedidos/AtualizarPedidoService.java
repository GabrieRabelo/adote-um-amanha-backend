package br.com.ages.adoteumamanha.service.pedidos;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.dto.request.AtualizarPedidoRequest;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.exception.Mensagem;
import br.com.ages.adoteumamanha.repository.PedidoRepository;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.validator.StatusPedidoValidator;
import br.com.ages.adoteumamanha.validator.UsuarioCriadorPedidoValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtualizarPedidoService {

    private final PedidoRepository repository;
    private final StatusPedidoValidator statusPedidoValidator;
    private final BuscarPedidoService buscarPedidoService;
    private final UsuarioCriadorPedidoValidator usuarioCriadorPedidoValidator;

    public void atualizar(final Long id, final AtualizarPedidoRequest request, final UserPrincipal userPrincipal) {

        log.info("Validando request");
        if (isNull(request)) {
            throw new ApiException(Mensagem.REQUEST_INVALIDO.getDescricao(), HttpStatus.BAD_REQUEST);
        }

        log.info("Buscando pedido com id: {}", id);
        final Pedido pedido = buscarPedidoService.buscarPorID(id);

        log.info("Validando status do pedido: {}", pedido.getStatus());
        statusPedidoValidator.validar(pedido);

        final Long idUsuarioCriadorPedido = ofNullable(pedido.getUsuario()).map(Usuario::getId).orElse(null);
        final Long idUsuarioLogado = userPrincipal.getId();

        log.info("Validando id usuário logado {} com o do criador do pedido {}", idUsuarioLogado, idUsuarioCriadorPedido);
        usuarioCriadorPedidoValidator.validar(idUsuarioLogado, idUsuarioCriadorPedido);

        Optional.of(request).ifPresent(req -> {
            ofNullable(req.getAssunto()).ifPresent(pedido::setAssunto);
            ofNullable(req.getDescricao()).ifPresent(pedido::setDescricao);
            ofNullable(req.getCategoria()).ifPresent(pedido::setCategoria);
            ofNullable(req.getSubcategoria()).ifPresent(pedido::setSubcategoria);
            ofNullable(req.getUrlVideo()).ifPresent(pedido::setUrlVideo);
        });

        log.info("Atualizando pedido com id: {}", pedido.getId());
        repository.save(pedido);
    }

}