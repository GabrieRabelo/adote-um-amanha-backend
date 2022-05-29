package br.com.ages.adoteumamanha.service.match;

import br.com.ages.adoteumamanha.domain.entity.Match;
import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.dto.response.ResumoMatchResponse;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.exception.Mensagem;
import br.com.ages.adoteumamanha.mapper.MatchMapper;
import br.com.ages.adoteumamanha.mapper.ResumoMatchResponseMapper;
import br.com.ages.adoteumamanha.repository.MatchRepository;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.service.pedidos.BuscarPedidoService;
import br.com.ages.adoteumamanha.validator.MatchValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static br.com.ages.adoteumamanha.domain.enumeration.Status.FINALIZADA;
import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchAdminService {

    private final MatchRepository repository;
    private final MatchMapper matchMapper;
    private final MatchValidator matchValidator;
    private final BuscarPedidoService buscarPedidoService;
    private final ResumoMatchResponseMapper resumoMatchResponseMapper;

    public ResumoMatchResponse cadastrar(final Long idDoacao, final Long idNecessidade, final UserPrincipal userPrincipal) {

        log.info("Validando id da doação {} e id da necessidade {}", idDoacao, idNecessidade);
        if (isNull(idDoacao) || isNull(idNecessidade)) {
            throw new ApiException(Mensagem.REQUEST_INVALIDO.getDescricao(), HttpStatus.BAD_REQUEST);
        }

        log.info("Buscando no banco o id da doação {}", idDoacao);
        final Pedido doacao = buscarPedidoService.buscarPorID(idDoacao);

        log.info("Buscando no banco o id da necessidade {}", idNecessidade);
        final Pedido necesidade = buscarPedidoService.buscarPorID(idNecessidade);

        final Match match = matchMapper.apply(userPrincipal, doacao, necesidade);

        log.info("validando se o match foi mapeado corretamente");
        matchValidator.validar(match);

        log.info("Atualizando os status da necessidade e doação para {}", FINALIZADA);
        necesidade.setStatus(FINALIZADA);
        doacao.setStatus(FINALIZADA);

        log.info("Cadastrando match da doação id: {} com necessidade id {} pelo administrador", idDoacao, idNecessidade);
        var savedMatch = repository.save(match);
        return resumoMatchResponseMapper.apply(savedMatch);
    }

}

