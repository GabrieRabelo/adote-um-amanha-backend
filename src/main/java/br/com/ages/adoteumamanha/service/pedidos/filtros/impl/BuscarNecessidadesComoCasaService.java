package br.com.ages.adoteumamanha.service.pedidos.filtros.impl;

import br.com.ages.adoteumamanha.domain.enumeration.*;
import br.com.ages.adoteumamanha.dto.response.PedidosResponse;
import br.com.ages.adoteumamanha.service.pedidos.filtros.BuscarPedidosComFiltrosService;
import br.com.ages.adoteumamanha.service.pedidos.filtros.BuscarPedidosImplementacaoFiltros;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.ages.adoteumamanha.domain.enumeration.Perfil.CASA;
import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.NECESSIDADE;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuscarNecessidadesComoCasaService implements BuscarPedidosImplementacaoFiltros {

    private final BuscarPedidosComFiltrosService service;

    @Override
    public Perfil getPerfil() {
        return CASA;
    }

    @Override
    public TipoPedido getTipoPedido() {
        return NECESSIDADE;
    }

    @Override
    public PedidosResponse buscar(final Integer pagina,
                                  final Integer tamanho,
                                  final String ordenacao,
                                  final Direcao direcao,
                                  final List<Categoria> categorias,
                                  final List<Subcategoria> subcategorias,
                                  final List<Status> status,
                                  final Integer mesesCorte,
                                  final String textoBusca,
                                  final Long idUsuarioLogado) {
        log.info("Buscando {} para {}", getTipoPedido(), getPerfil());
        return service.buscar(pagina, tamanho, ordenacao, direcao,
                categorias, subcategorias, status, mesesCorte, textoBusca, getTipoPedido(), idUsuarioLogado);
    }
}
