package br.com.ages.adoteumamanha.service;

import br.com.ages.adoteumamanha.domain.entity.Match;
import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.exception.Mensagem;
import br.com.ages.adoteumamanha.mapper.MatchMapper;
import br.com.ages.adoteumamanha.mapper.PedidoMapper;
import br.com.ages.adoteumamanha.repository.MatchRepository;
import br.com.ages.adoteumamanha.repository.PedidoRepository;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.validator.CadastrarPedidoRequestValidator;
import br.com.ages.adoteumamanha.validator.MatchValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.NECESSIDADE;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchDoadorService {

    private final MatchRepository repository;
    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;
    private final CadastrarPedidoRequestValidator validator;
    private final MatchMapper matchMapper;
    private final MatchValidator matchValidator;

    public void cadastrar(final UserPrincipal doador, final Long idNecessidade, final CadastrarPedidoRequest request) {

        log.info("Validando request de cadastro de pedido");
        validator.validate(request);

        final Pedido doacao = pedidoMapper.apply(request, doador);

        final Pedido necesidade = pedidoRepository.findById(idNecessidade)
                .orElseThrow(() -> new ApiException(Mensagem.NECESSIDADE_NAO_ENCONTRADA.getDescricao(), HttpStatus.NOT_FOUND));

        if (isFalse(NECESSIDADE.equals(necesidade.getTipoPedido()))) {
            throw new ApiException(Mensagem.VINCULACAO_DOACAO_DOACAO_MATCH.getDescricao(), HttpStatus.BAD_REQUEST);
        }

        final Match match = matchMapper.apply(doador, doacao, necesidade);
        matchValidator.validate(match);

        log.info("Cadastrando novo match, do usuario com id: {}, para a necessidade da casa com id {}", doador.getId(), idNecessidade);
        repository.save(match);
    }

}

