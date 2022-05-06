package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.TipoPedido;
import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.function.BiFunction;

import static br.com.ages.adoteumamanha.domain.enumeration.Perfil.CASA;
import static br.com.ages.adoteumamanha.domain.enumeration.Status.PENDENTE;
import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.DOACAO;
import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.NECESSIDADE;

@Component
public class PedidoMapper implements BiFunction<CadastrarPedidoRequest, UserPrincipal, Pedido> {

    @Override
    public Pedido apply(final CadastrarPedidoRequest cadastrarPedidoRequest, final UserPrincipal userPrincipal) {
        return Pedido.builder()
                .withAssunto(cadastrarPedidoRequest.getAssunto())
                .withDescricao(cadastrarPedidoRequest.getDescricao())
                .withCategoria(cadastrarPedidoRequest.getCategoria())
                .withSubcategoria(cadastrarPedidoRequest.getSubcategoria())
                .withUrlVideo(cadastrarPedidoRequest.getUrlVideo())
                .withStatus(PENDENTE)
                .withDataHora(LocalDateTime.now())
                .withTipoPedido(buildTipoPedido(userPrincipal))
                .withUsuario(buildUsuario(userPrincipal))
                .build();
    }

    private Usuario buildUsuario(final UserPrincipal userPrincipal) {
        return Usuario.builder().withId(userPrincipal.getId()).build();
    }

    private TipoPedido buildTipoPedido(final UserPrincipal userPrincipal) {

        if (CASA.equals(userPrincipal.getPerfil())) {
            return NECESSIDADE;
        }

        return DOACAO;
    }

}
