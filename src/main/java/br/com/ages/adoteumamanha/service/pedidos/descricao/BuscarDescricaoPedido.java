package br.com.ages.adoteumamanha.service.pedidos.descricao;

import br.com.ages.adoteumamanha.domain.enumeration.*;
import br.com.ages.adoteumamanha.dto.response.DescricaoPedidoResponse;
import br.com.ages.adoteumamanha.security.UserPrincipal;

import java.util.List;

public interface BuscarDescricaoPedido {


    Perfil getPerfil();

    List<TipoPedido> getTipoPedidos();

    DescricaoPedidoResponse executar(final Long idPedido, final UserPrincipal userPrincipal);

}
