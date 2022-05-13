package br.com.ages.adoteumamanha.service.pedidos.descricao;

import br.com.ages.adoteumamanha.domain.enumeration.Perfil;
import br.com.ages.adoteumamanha.domain.enumeration.TipoPedido;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.exception.Mensagem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuscarDescricaoPedidoStrategy {

    private final List<BuscarDescricaoPedido> services;

    public BuscarDescricaoPedido run(final Perfil perfil, final TipoPedido tipoPedido) {

        log.info("Proucando a service com a implementação para {} e de tipo de pedido {}", perfil, tipoPedido);
        return services.stream()
                .filter(service -> perfil.equals(service.getPerfil()))
                .filter(service -> service.getTipoPedidos().contains(tipoPedido))
                .findFirst()
                .orElseThrow(() -> new ApiException(Mensagem.OPERACAO_NAO_PERMITIDA.getDescricao(), HttpStatus.BAD_REQUEST));

    }
}
