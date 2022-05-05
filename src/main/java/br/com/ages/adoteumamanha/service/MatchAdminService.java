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
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchAdminService {

    private final MatchRepository repository;
    private final PedidoRepository pedidoRepository;
    private final MatchMapper matchMapper;
    private final MatchValidator matchValidator;

    public void cadastrar(final Long idDoacao, final Long idNecessidade, final UserPrincipal userPrincipal) {

        if(isNull(idDoacao) || isNull(idNecessidade)) {
            throw new ApiException(Mensagem.REQUEST_INVALIDO.getDescricao(), HttpStatus.BAD_REQUEST);
        }

        final Pedido doacao = pedidoRepository.findById(idDoacao)
                .orElseThrow(() -> new ApiException(Mensagem.DOACAO_NAO_ENCONTRADA.getDescricao(), HttpStatus.NOT_FOUND));

        final Pedido necesidade = pedidoRepository.findById(idNecessidade)
                .orElseThrow(() -> new ApiException(Mensagem.NECESSIDADE_NAO_ENCONTRADA.getDescricao(), HttpStatus.NOT_FOUND));

        final Match match = matchMapper.apply(userPrincipal, doacao, necesidade);
        matchValidator.validate(match);

        log.info("Cadastrando match da necessidade id: {} com necessidade  id {} pelo administrador", idDoacao, idNecessidade);
        repository.save(match);
    }

}

