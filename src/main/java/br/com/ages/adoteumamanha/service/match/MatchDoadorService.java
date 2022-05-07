package br.com.ages.adoteumamanha.service.match;

import br.com.ages.adoteumamanha.domain.entity.Match;
import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.mapper.MatchMapper;
import br.com.ages.adoteumamanha.mapper.PedidoMapper;
import br.com.ages.adoteumamanha.repository.MatchRepository;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.service.pedidos.BuscarPedidoService;
import br.com.ages.adoteumamanha.validator.CadastrarPedidoRequestValidator;
import br.com.ages.adoteumamanha.validator.MatchValidator;
import br.com.ages.adoteumamanha.validator.VinculacaoDoacaoNecessidadeMatchValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchDoadorService {

    private final MatchRepository repository;
    private final PedidoMapper pedidoMapper;
    private final BuscarPedidoService buscarPedidoService;
    private final CadastrarPedidoRequestValidator validator;
    private final MatchMapper matchMapper;
    private final MatchValidator matchValidator;
    private final VinculacaoDoacaoNecessidadeMatchValidator vinculacaoDoacaoNecessidadeMatchValidator;

    public void cadastrar(final UserPrincipal doador, final Long idNecessidade, final CadastrarPedidoRequest request) {

        log.info("Validando request de cadastro de pedido");
        validator.validate(request);

        final Pedido doacao = pedidoMapper.apply(request, doador);

        log.info("Buscando no banco o id da necessidade {}", idNecessidade);
        final Pedido necesidade = buscarPedidoService.buscarPorID(idNecessidade);

        log.info("Validando de registro da necessidade buscada do banco é uma necessidade", necesidade.getTipoPedido());
        vinculacaoDoacaoNecessidadeMatchValidator.validate(necesidade.getTipoPedido());

        final Match match = matchMapper.apply(doador, doacao, necesidade);

        log.info("validando se o match foi mapeado corretamente");
        matchValidator.validate(match);

        log.info("Cadastrando novo match, do usuario com id: {}, para a necessidade da casa com id {}", doador.getId(), idNecessidade);
        repository.save(match);
    }

}

