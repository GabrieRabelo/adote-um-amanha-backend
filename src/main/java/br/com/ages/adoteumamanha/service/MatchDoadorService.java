package br.com.ages.adoteumamanha.service;

import br.com.ages.adoteumamanha.domain.entity.Match;
import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.Status;
import br.com.ages.adoteumamanha.domain.enumeration.TipoPedido;
import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.mapper.PedidoMapper;
import br.com.ages.adoteumamanha.repository.MatchRepository;
import br.com.ages.adoteumamanha.repository.PedidoRepository;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static org.springframework.data.domain.Sort.by;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchDoadorService {

    private final MatchRepository repository;
    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;

    public void cadastrar(final UserPrincipal doador, final Long idNecessidade, CadastrarPedidoRequest request) throws Exception {

        final Pedido doacao = pedidoMapper.apply(request, doador);
        pedidoRepository.save(doacao);
        final Pedido necesidade = pedidoRepository.getById(idNecessidade);

        // Validar doacao e necessidade se sao dos tipos corretos

        if(necesidade.getTipoPedido() != TipoPedido.NECESSIDADE || doacao.getTipoPedido() != TipoPedido.DOACAO){
            throw new Exception("Necessidade e doação não são do tipo pedido esperado.");
        }

        final var match = Match.builder()
                .withDoacao(doacao)
                .withNecessidade(necesidade)
                .withData(LocalDateTime.now())
                .withStatus(Status.valueOf("PENDENTE"))
                .build();

        log.info("Cadastrando novo match, do usuario com id: {}, para a necessidade da casa com id {}", doador.getId(), idNecessidade);
        repository.save(match);
    }

}
