package br.com.ages.adoteumamanha.service.pedidos.descricao.impl;

import br.com.ages.adoteumamanha.domain.enumeration.Perfil;
import br.com.ages.adoteumamanha.domain.enumeration.TipoPedido;
import br.com.ages.adoteumamanha.dto.response.DescricaoPedidoResponse;
import br.com.ages.adoteumamanha.service.pedidos.descricao.BuscarDescricaoPedido;
import br.com.ages.adoteumamanha.service.pedidos.descricao.BuscarDescricaoPedidoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.ages.adoteumamanha.domain.enumeration.Perfil.DOADOR;
import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.NECESSIDADE;
import static java.util.Collections.singletonList;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuscarDescricaoNecessidadeComoDoadorService implements BuscarDescricaoPedido {

    private final BuscarDescricaoPedidoService service;

    @Override
    public Perfil getPerfil() {
        return DOADOR;
    }

    @Override
    public List<TipoPedido> getTipoPedidos() {
        return singletonList(NECESSIDADE);
    }

    @Override
    public DescricaoPedidoResponse executar(final Long idPedido,
                                            final Long idUsuarioLogado) {

        log.info("Buscando {} para {}", getTipoPedidos(), getPerfil());
        return service.buscar(idPedido, getTipoPedidos(), null);
    }
}
