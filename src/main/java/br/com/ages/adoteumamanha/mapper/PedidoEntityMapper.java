package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.PedidoEntity;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.Pedido;
import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.BiFunction;

import static br.com.ages.adoteumamanha.domain.enumeration.Pedido.DOACAO;
import static br.com.ages.adoteumamanha.domain.enumeration.Pedido.NECESSIDADE;
import static br.com.ages.adoteumamanha.domain.enumeration.Perfil.CASA;
import static br.com.ages.adoteumamanha.domain.enumeration.Status.PENDENTE;

@Component
public class PedidoEntityMapper implements BiFunction<CadastrarPedidoRequest, UserPrincipal, PedidoEntity> {

    @Override
    public PedidoEntity apply(final CadastrarPedidoRequest cadastrarPedidoRequest, final UserPrincipal userPrincipal) {
        return PedidoEntity.builder()
                .withAssunto(cadastrarPedidoRequest.getAssunto())
                .withDescricao(cadastrarPedidoRequest.getDescricao())
                .withCategoria(cadastrarPedidoRequest.getCategoria())
                .withSubcategoria(cadastrarPedidoRequest.getSubcategoria())
                .withStatus(PENDENTE)
                .withDataHora(LocalDateTime.now())
                .withPedido(buildTipoDonativo(userPrincipal))
                .withUsuario(buildUsuario(userPrincipal))
                .build();
    }

    private Usuario buildUsuario(final UserPrincipal userPrincipal) {
        return Usuario.builder().withId(userPrincipal.getId()).build();
    }

    private Pedido buildTipoDonativo(final UserPrincipal userPrincipal) {
        Optional<? extends GrantedAuthority> permissoes = userPrincipal.getAuthorities().stream().findFirst();

        if (CASA.getDescricao().equals(permissoes.get().getAuthority())) {
            return NECESSIDADE;
        }

        return DOACAO;
    }
}
