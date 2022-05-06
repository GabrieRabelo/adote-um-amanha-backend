package br.com.ages.adoteumamanha.service.pedidos.filtros;

import br.com.ages.adoteumamanha.domain.enumeration.*;
import br.com.ages.adoteumamanha.dto.response.PedidosResponse;

import java.util.List;

public interface BuscarPedidosImplementacaoFiltros {


    Perfil getPerfil();

    TipoPedido getTipoPedidos();

    PedidosResponse buscar(final Integer pagina,
                           final Integer tamanho, final String ordenacao,
                           final Direcao direcao,
                           final List<Categoria> categorias,
                           final List<Subcategoria> subcategorias,
                           final List<Status> status,
                           final Integer mesesCorte,
                           final String textoBusca,
                           final Long idUsuarioLogado);

}
